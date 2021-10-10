package edu.postech.csed332.homework3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Set;

/**
 * An abstract test class for MutableTree with blackbox test methods
 *
 * @param <V> type of vertices
 * @param <T> type of Tree
 */
@Disabled
public abstract class AbstractMutableTreeTest<V extends Comparable<V>, T extends MutableTree<V>> {

    T tree;
    V v1, v2, v3, v4, v5, v6, v7, v8;

    abstract boolean checkInv();    // call checkInv of tree

    @Test
    void testGetDepthRoot() {
        tree.addVertex(v1);
        Assertions.assertEquals(tree.getDepth(v1), 0);
    }

    @Test
    void testGetDepthTwo() {
        tree.addVertex(v1);
        tree.addEdge(v1, v2);
        Assertions.assertEquals(tree.getDepth(v2), 1);
    }

    @Test
    void testGetDepthNoRoot() {
        Assertions.assertThrows(IllegalStateException.class, () -> tree.getDepth(v1));
    }

    @Test
    void testGetDepthNoVertex() {
        Assertions.assertTrue(tree.addVertex(v1));
        Assertions.assertThrows(IllegalArgumentException.class, () -> tree.getDepth(v2));
    }

    @Test
    void testGetHeight() {
        Assertions.assertTrue(tree.addVertex(v1));
        Assertions.assertTrue(tree.addEdge(v1, v2));
        Assertions.assertTrue(tree.addEdge(v2, v3));
        Assertions.assertTrue(tree.addEdge(v3, v4));
        Assertions.assertTrue(tree.addEdge(v1, v5));
        Assertions.assertTrue(tree.addEdge(v5, v7));
        Assertions.assertTrue(tree.addEdge(v5, v6));
        Assertions.assertTrue(tree.addEdge(v7, v8));

        Assertions.assertEquals(tree.getHeight(), 3);

        Assertions.assertTrue(checkInv());
    }

    @Test
    void testGetHeightNoRoot() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            tree.getHeight();
        });

        Assertions.assertTrue(checkInv());
    }

    @Test
    void testContainsVertex() {
        Assertions.assertTrue(tree.addVertex(v1));
        Assertions.assertTrue(tree.addEdge(v1, v2));

        Assertions.assertTrue(tree.containsVertex(v1));
        Assertions.assertTrue(tree.containsVertex(v2));
        Assertions.assertFalse(tree.containsVertex(v3));

        Assertions.assertTrue(checkInv());
    }

    @Test
    void testAddVertexAlreadyAdded() {
        Assertions.assertTrue(tree.addVertex(v1));
        Assertions.assertFalse(tree.addVertex(v2));
    }

    @Test
    void testRemoveVertex() {
        Assertions.assertTrue(tree.addVertex(v1));
        Assertions.assertTrue(tree.addEdge(v1, v2));
        Assertions.assertTrue(tree.addEdge(v2, v3));
        Assertions.assertTrue(tree.addEdge(v3, v4));
        Assertions.assertTrue(tree.addEdge(v1, v5));
        Assertions.assertTrue(tree.addEdge(v5, v7));
        Assertions.assertTrue(tree.addEdge(v5, v6));
        Assertions.assertTrue(tree.addEdge(v7, v8));

        Assertions.assertTrue(tree.removeVertex(v5));
        Assertions.assertFalse(tree.removeVertex(v5));

        Assertions.assertTrue(tree.removeVertex(v4));
        Assertions.assertFalse(tree.removeVertex(v4));

        Assertions.assertEquals(tree.getVertices(), Set.of(v1, v2, v3));

        for (V v : Set.of(v4, v5, v6, v7, v8)) {
            Assertions.assertFalse(tree.containsVertex(v));
        }

        Assertions.assertEquals(tree.getHeight(), 2);

        Assertions.assertTrue(checkInv());
    }

    @Test
    void testRemoveVertexRoot() {
        Assertions.assertTrue(tree.addVertex(v1));
        Assertions.assertTrue(tree.removeVertex(v1));
        Assertions.assertTrue(tree.getRoot().isEmpty());
    }

    @Test
    void testRemoveEdge() {
        Assertions.assertTrue(tree.addVertex(v1));
        Assertions.assertTrue(tree.addEdge(v1, v2));
        Assertions.assertTrue(tree.addEdge(v2, v3));
        Assertions.assertTrue(tree.addEdge(v3, v4));
        Assertions.assertTrue(tree.addEdge(v1, v5));
        Assertions.assertTrue(tree.addEdge(v5, v7));
        Assertions.assertTrue(tree.addEdge(v5, v6));
        Assertions.assertTrue(tree.addEdge(v7, v8));

        Assertions.assertTrue(tree.removeEdge(v1, v5));
        Assertions.assertFalse(tree.removeEdge(v1, v5));

        Assertions.assertTrue(tree.removeEdge(v3, v4));
        Assertions.assertFalse(tree.removeEdge(v3, v4));

        Assertions.assertFalse(tree.removeEdge(v4, v8));

        Assertions.assertEquals(tree.getVertices(), Set.of(v1, v2, v3));

        for (V v : Set.of(v4, v5, v6, v7, v8)) {
            Assertions.assertFalse(tree.containsVertex(v));
        }

        Assertions.assertEquals(tree.getHeight(), 2);

        Assertions.assertTrue(checkInv());
    }

    @Test
    void testAddEdge() {
        Assertions.assertTrue(tree.addVertex(v1));
        Assertions.assertTrue(tree.addEdge(v1, v2));
        Assertions.assertFalse(tree.addEdge(v3, v4));
        Assertions.assertFalse(tree.addEdge(v1, v2));
        Assertions.assertFalse(tree.addEdge(v3, v2));

        Assertions.assertTrue(checkInv());
    }

    @Test
    void testGetSources() {
        Assertions.assertTrue(tree.addVertex(v1));
        Assertions.assertTrue(tree.addEdge(v1, v2));
        Assertions.assertTrue(tree.addEdge(v2, v3));
        Assertions.assertTrue(tree.addEdge(v3, v4));
        Assertions.assertTrue(tree.addEdge(v1, v5));
        Assertions.assertTrue(tree.addEdge(v5, v7));
        Assertions.assertTrue(tree.addEdge(v5, v6));
        Assertions.assertTrue(tree.addEdge(v7, v8));

        Assertions.assertEquals(tree.getSources(v1), Set.of());
        Assertions.assertEquals(tree.getSources(v2), Set.of(v1));
        Assertions.assertEquals(tree.getSources(v8), Set.of(v7));
    }

    @Test
    void testGetTargets() {
        Assertions.assertTrue(tree.addVertex(v1));
        Assertions.assertTrue(tree.addEdge(v1, v2));
        Assertions.assertTrue(tree.addEdge(v2, v3));
        Assertions.assertTrue(tree.addEdge(v3, v4));
        Assertions.assertTrue(tree.addEdge(v1, v5));
        Assertions.assertTrue(tree.addEdge(v5, v7));
        Assertions.assertTrue(tree.addEdge(v5, v6));
        Assertions.assertTrue(tree.addEdge(v7, v8));

        Assertions.assertEquals(tree.getTargets(v1), Set.of(v2, v5));
        Assertions.assertEquals(tree.getTargets(v5), Set.of(v6, v7));
        Assertions.assertEquals(tree.getTargets(v8), Set.of());
    }

    @Test
    void testGetEdges() {
        Assertions.assertTrue(tree.addVertex(v1));
        Assertions.assertTrue(tree.addEdge(v1, v2));
        Assertions.assertTrue(tree.addEdge(v2, v3));
        Assertions.assertTrue(tree.addEdge(v3, v4));
        Assertions.assertTrue(tree.addEdge(v1, v5));
        Assertions.assertTrue(tree.addEdge(v5, v7));
        Assertions.assertTrue(tree.addEdge(v5, v6));
        Assertions.assertTrue(tree.addEdge(v7, v8));

        Assertions.assertEquals(tree.getEdges(), Set.of(
                new Edge<V>(v1, v2),
                new Edge<V>(v1, v5),
                new Edge<V>(v2, v3),
                new Edge<V>(v3, v4),
                new Edge<V>(v5, v6),
                new Edge<V>(v5, v7),
                new Edge<V>(v7, v8)
        ));
    }

    @Test
    void testContainsEdge() {
        Assertions.assertTrue(tree.addVertex(v1));
        Assertions.assertTrue(tree.addEdge(v1, v2));
        Assertions.assertTrue(tree.addEdge(v2, v3));
        Assertions.assertTrue(tree.addEdge(v3, v4));
        Assertions.assertTrue(tree.addEdge(v1, v5));
        Assertions.assertTrue(tree.addEdge(v5, v7));
        Assertions.assertTrue(tree.addEdge(v5, v6));
        Assertions.assertTrue(tree.addEdge(v7, v8));

        Assertions.assertTrue(tree.containsEdge(v1, v2));
        Assertions.assertFalse(tree.containsEdge(v2, v1));
        Assertions.assertFalse(tree.containsEdge(v2, v8));
    }
}
