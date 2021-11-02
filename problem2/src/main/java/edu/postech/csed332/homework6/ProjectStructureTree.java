package edu.postech.csed332.homework6;

import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.ui.ColoredTreeCellRenderer;
import com.intellij.ui.treeStructure.Tree;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.plaf.metal.MetalIconFactory;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * A tree GUI for our Project Structure plugin. It displays the corresponding name and icon for the nodes in our tree
 * model using a custom cell renderer. Note that each node is an instance of DefaultMutableTreeNode, and its user data
 * is an instance of Project, PsiPackage, PsiClass, PsiMethod, or PsiField. The tree GUI detects double-click
 * mouse events for Method and Field nodes, and shows the corresponding methods or fields in the editor (by using
 * {@link PsiMethod#navigate} or {@link PsiField#navigate}). Finally, whenever the underlying project changes, the 
 * corresponding node of the tree GUI is automatically chosen.
 */
class ProjectStructureTree extends Tree {

    private static final Icon projectIcon = MetalIconFactory.getTreeHardDriveIcon();
    private static final Icon packageIcon = MetalIconFactory.getTreeFolderIcon();
    private static final Icon classIcon = MetalIconFactory.getTreeComputerIcon();
    private static final Icon methodIcon = MetalIconFactory.getFileChooserDetailViewIcon();
    private static final Icon fieldIcon = MetalIconFactory.getVerticalSliderThumbIcon();
    private static final Icon defaultIcon = MetalIconFactory.getTreeLeafIcon();

    /**
     * Creates a project structure tree for a given project.
     *
     * @param project a project
     */
    ProjectStructureTree(@NotNull Project project) {
        setModel(ProjectTreeModelFactory.createProjectTreeModel(project));

        // Set a cell renderer to display the name and icon of each node
        setCellRenderer(new ColoredTreeCellRenderer() {
            @Override
            public void customizeCellRenderer(@NotNull JTree tree, Object value, boolean selected,
                                              boolean expanded, boolean leaf, int row, boolean hasFocus) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
                Object userObject = node.getUserObject();

                if (userObject instanceof Project) {
                    setIcon(projectIcon);
                    append(((Project) userObject).getName());
                } else if (userObject instanceof PsiPackage) {
                    setIcon(packageIcon);
                    append(Objects.requireNonNull(((PsiPackage) userObject).getName()));
                } else if (userObject instanceof PsiClass) {
                    setIcon(classIcon);
                    append(Objects.requireNonNull(((PsiClass) userObject).getName()));
                } else if (userObject instanceof PsiMethod) {
                    setIcon(methodIcon);
                    append(Objects.requireNonNull(((PsiMethod) userObject).getName()));
                } else if (userObject instanceof PsiField) {
                    setIcon(fieldIcon);
                    append(Objects.requireNonNull(((PsiField) userObject).getName()));
                } else if (userObject instanceof PsiElement) {
                    setIcon(defaultIcon);
                    append(Objects.requireNonNull(((PsiElement) userObject).getText()));
                }
            }
        });

        // Set a mouse listener to handle double-click events
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    // TODO: implement the double-click behavior here
                    // hint: use the navigate method of the classes PsiMethod and PsiField
                    TreePath path = getSelectionPath();
                    if (path == null) { return; }
                    DefaultMutableTreeNode term = (DefaultMutableTreeNode) path.getLastPathComponent();

                    if (term.getUserObject() instanceof PsiMethod || term.getUserObject() instanceof PsiField) {
                        ((PsiJvmMember) term.getUserObject()).navigate(true);
                    }
                }
            }
        });

        // Set a Psi tree change listener to handle changes in the project. We provide code for obtaining an instance
        // of PsiField, PsiMethod, PsiClass, or PsiPackage. Implement the updateTree method below.
        PsiManager.getInstance(project).addPsiTreeChangeListener(new PsiTreeChangeAdapter() {
            @Override
            public void childAdded(@NotNull PsiTreeChangeEvent event) {
                getTargetElement(event).ifPresent(target -> updateTree(project, target));
            }

            @Override
            public void childRemoved(@NotNull PsiTreeChangeEvent event) {
                getTargetElement(event).ifPresent(target -> updateTree(project, target));
            }

            @Override
            public void childReplaced(@NotNull PsiTreeChangeEvent event) {
                getTargetElement(event).ifPresent(target -> updateTree(project, target));
            }
        }, project);
    }

    /**
     * Updates a tree according to the change in the target element, and shows the corresponding node in the Project
     * Structure tree. The simplest way is to reset a model of the tree (using setModel) and then to traverse the tree
     * to find the corresponding node to the target element. Use the methods {@link JTree::setSelectionPath} and
     * {@link JTree::scrollPathToVisibles} to display the corresponding node in GUI.
     *
     * @param project a project
     * @param target  a target element
     */
    private void updateTree(@NotNull Project project, @NotNull PsiElement target) {
        setModel(ProjectTreeModelFactory.createProjectTreeModel(project));
    }

    /**
     * Returns an instance of PsiField, PsiMethod, PsiClass, or PsiPackage that is related to a change event
     *
     * @param event a change event
     * @return the corresponding Psi element
     */
    @NotNull
    private Optional<PsiElement> getTargetElement(@NotNull PsiTreeChangeEvent event) {
        for (PsiElement obj : List.of(event.getChild(), event.getParent())) {
            for (var c : List.of(PsiField.class, PsiMethod.class, PsiClass.class, PsiPackage.class)) {
                var elm = PsiTreeUtil.getParentOfType(obj, c, false);
                if (elm != null)
                    return Optional.of(elm);
            }
            if (obj instanceof PsiDirectory) {
                final var pack = JavaDirectoryService.getInstance().getPackage((PsiDirectory) obj);
                if (pack != null)
                    return Optional.of(pack);
            }
        }
        return Optional.empty();
    }
}
