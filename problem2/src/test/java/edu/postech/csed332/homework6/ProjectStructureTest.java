package edu.postech.csed332.homework6;

import com.intellij.openapi.project.Project;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;


public class ProjectStructureTest extends BasePlatformTestCase {

    public void testProjectTreeModelRoot() {
        Project mockProject = getProject();
        TreeModel model = ProjectTreeModelFactory.createProjectTreeModel(mockProject);
        assertNotNull(model);

        Object root = model.getRoot();
        assertNotNull(root);
        assertTrue(root instanceof DefaultMutableTreeNode);
        assertEquals(mockProject, ((DefaultMutableTreeNode) root).getUserObject());
    }

    public void testProjectStructureTreeRoot() {
        ProjectStructureWindow tree1 = new ProjectStructureWindow();
        Object root1 = tree1.getProjectStructureTree().getRoot();
        Object root2 = ProjectTreeModelFactory.createProjectTreeModel(getProject()).getRoot();

        assertTrue(root1 instanceof DefaultMutableTreeNode);
        assertTrue(root2 instanceof DefaultMutableTreeNode);
        assertEquals(((DefaultMutableTreeNode)root1).getUserObject(), ((DefaultMutableTreeNode)root2).getUserObject());
    }

}
