package edu.postech.csed332.homework3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

public class DoubleParentPointerMutableTreeTest extends AbstractMutableTreeTest<Double, ParentPointerTree<Double>> {

    @BeforeEach
    void setUp() {
        tree = new ParentPointerTree<>();
        v1 = 1.9;
        v2 = 2.8;
        v3 = 3.7;
        v4 = 4.6;
        v5 = 5.5;
        v6 = 6.4;
        v7 = 7.3;
        v8 = 8.2;
    }

    @Override
    boolean checkInv() {
        return tree.checkInv();
    }

    @Test
    void testCheckInvNotReachable() throws NoSuchFieldException, IllegalAccessException {
        tree = new ParentPointerTree<>();

        Field nodeMapField = tree.getClass().getDeclaredField("root");
        nodeMapField.setAccessible(true);

        tree.addVertex(v1);
        tree.addEdge(v1, v2);

        nodeMapField.set(tree, null);

        tree.addVertex(v3);

        Assertions.assertFalse(checkInv());
    }

    @Test
    void testCheckInvEdgeTargetRoot() throws NoSuchFieldException, IllegalAccessException {
        tree = new ParentPointerTree<>();

        Field nodeMapField = tree.getClass().getDeclaredField("root");
        nodeMapField.setAccessible(true);

        tree.addVertex(v1);
        tree.addEdge(v1, v2);

        nodeMapField.set(tree, v2);

        Assertions.assertFalse(checkInv());
    }
}
