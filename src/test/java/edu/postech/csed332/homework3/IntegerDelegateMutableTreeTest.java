package edu.postech.csed332.homework3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IntegerDelegateMutableTreeTest extends AbstractMutableTreeTest<Integer, DelegateTree<Integer>> {

    @BeforeEach
    void setUp() {
        tree = new DelegateTree<>(new AdjacencyListGraph<Integer>());
        v1 = 1;
        v2 = 2;
        v3 = 3;
        v4 = 4;
        v5 = 5;
        v6 = 6;
        v7 = 7;
        v8 = 8;
    }

    @Override
    boolean checkInv() {
        return tree.checkInv();
    }

    @Test
    void testConstructor() {
        MutableGraph<Integer> graph = new AdjacencyListGraph<>();
        graph.addVertex(v1);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            tree = new DelegateTree<>(graph);
        });
    }

    @Test
    void testCheckInvEdgeToRoot() {
        MutableGraph<Integer> graph = new AdjacencyListGraph<>();
        tree = new DelegateTree<>(graph);
        tree.addVertex(v1);
        tree.addEdge(v1, v2);

        graph.addEdge(v2, v1);

        Assertions.assertFalse(checkInv());
    }

    @Test
    void testCheckInvNotReachable() {
        MutableGraph<Integer> graph = new AdjacencyListGraph<>();
        tree = new DelegateTree<>(graph);
        tree.addVertex(v1);
        tree.addEdge(v1, v2);

        graph.addVertex(v3);

        Assertions.assertFalse(checkInv());
    }

    @Test
    void testCheckInvMultipleParent() {
        MutableGraph<Integer> graph = new AdjacencyListGraph<>();
        tree = new DelegateTree<>(graph);
        tree.addVertex(v1);
        tree.addEdge(v1, v2);
        tree.addEdge(v1, v3);

        graph.addEdge(v3, v2);

        Assertions.assertFalse(checkInv());
    }
}
