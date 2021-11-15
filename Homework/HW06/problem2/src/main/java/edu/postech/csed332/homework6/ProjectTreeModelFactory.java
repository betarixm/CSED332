package edu.postech.csed332.homework6;

import com.intellij.ide.projectView.impl.nodes.PackageUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

class ProjectTreeModelFactory {

    /**
     * Create a tree model that describes the structure of a java project. This method use JavaElementVisitor to
     * traverse the Java hierarchy of each root package in the source directory, and to create a tree. Each node is an
     * instance of {@link DefaultMutableTreeNode} that can have a user object. The user object of root is the project
     * itself, and other nodes have corresponding instances of PsiPackage, PsiClass, PsiMethod, and PsiField.
     *
     * @param project a project
     * @return a tree model to describe the structure of project
     */
    public static TreeModel createProjectTreeModel(Project project) {
        // the root node of the tree
        final var root = new DefaultMutableTreeNode(project);

        // The visitor to traverse the Java hierarchy and to construct the tree
        final var visitor = new JavaElementVisitor() {
            DefaultMutableTreeNode curParent = root;

            @Override
            public void visitPackage(PsiPackage pack) {
                DefaultMutableTreeNode parentNode = new DefaultMutableTreeNode(pack.getName());
                parentNode.setUserObject(pack);
                curParent.add(parentNode);

                for (PsiPackage e : pack.getSubPackages()) {
                    curParent = parentNode;
                    e.accept(this);
                }

                for (PsiClass e : pack.getClasses()) {
                    curParent = parentNode;
                    e.accept(this);
                }

                System.out.println();
            }

            @Override
            public void visitClass(PsiClass aClass) {
                DefaultMutableTreeNode parentNode = new DefaultMutableTreeNode();
                parentNode.setUserObject(aClass);
                curParent.add(parentNode);

                for (PsiMethod e : aClass.getMethods()) {
                    curParent = parentNode;
                    e.accept(this);
                }

                for (PsiField f : aClass.getFields()) {
                    curParent = parentNode;
                    f.accept(this);
                }
            }

            @Override
            public void visitMethod(PsiMethod method) {
                DefaultMutableTreeNode parentNode = new DefaultMutableTreeNode();
                parentNode.setUserObject(method);
                curParent.add(parentNode);

                for (PsiElement e : method.getChildren()) {
                    curParent = parentNode;
                    e.accept(this);
                }
            }

            @Override
            public void visitField(PsiField field) {
                DefaultMutableTreeNode parentNode = new DefaultMutableTreeNode();
                parentNode.setUserObject(field);
                curParent.add(parentNode);

                for (PsiElement e : field.getChildren()) {
                    curParent = parentNode;
                    e.accept(this);
                }
            }
        };

        // apply the visitor for each root package in the source directory
        getRootPackages(project).forEach(aPackage -> aPackage.accept(visitor));
        return new DefaultTreeModel(root);
    }

    /**
     * Returns the root package(s) in the source directory of a project. The default package will not be considered, as
     * it includes all Java classes. Note that classes in the default package (i.e., having no package statement) will
     * be ignored for this assignment. To be completed, this case must be separately handled.
     *
     * @param project a project
     * @return a set of root packages
     */
    private static Set<PsiPackage> getRootPackages(Project project) {
        final Set<PsiPackage> rootPackages = new HashSet<>();
        var visitor = new PsiElementVisitor() {
            @Override
            public void visitDirectory(@NotNull PsiDirectory dir) {
                final var psiPackage = JavaDirectoryService.getInstance().getPackage(dir);
                if (psiPackage != null && !PackageUtil.isPackageDefault(psiPackage))
                    rootPackages.add(psiPackage);
                else
                    Arrays.stream(dir.getSubdirectories()).forEach(sd -> sd.accept(this));
            }
        };

        var rootManager = ProjectRootManager.getInstance(project);
        var psiManager = PsiManager.getInstance(project);
        Arrays.stream(rootManager.getContentSourceRoots())
                .map(psiManager::findDirectory)
                .filter(Objects::nonNull)
                .forEach(dir -> dir.accept(visitor));

        return rootPackages;
    }
}
