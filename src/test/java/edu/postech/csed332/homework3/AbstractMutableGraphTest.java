package edu.postech.csed332.homework3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Set;

/**
 * An abstract test class for MutableGraph with blackbox test methods
 *
 * @param <V> type of vertices
 * @param <G> type of Graph
 */
@Disabled
public abstract class AbstractMutableGraphTest<V extends Comparable<V>, G extends MutableGraph<V>> {

    G graph;
    V v1, v2, v3, v4, v5, v6, v7, v8;

    abstract boolean checkInv();    // call checkInv of graph

    @Test
    void testAddVertex() {
        Assertions.assertTrue(graph.addVertex(v1));
        Assertions.assertTrue(graph.containsVertex(v1));
        Assertions.assertTrue(checkInv());
    }

    @Test
    void testAddDuplicateVertices() {
        Assertions.assertTrue(graph.addVertex(v6));
        Assertions.assertTrue(graph.addVertex(v7));
        Assertions.assertFalse(graph.addVertex(v6));
        Assertions.assertTrue(graph.containsVertex(v6));
        Assertions.assertTrue(graph.containsVertex(v7));
        Assertions.assertTrue(checkInv());
    }

    @Test
    void testFindReachableVertices() {
        graph.addEdge(v1, v1);
        graph.addEdge(v1, v2);
        graph.addEdge(v3, v1);
        graph.addEdge(v3, v2);
        graph.addVertex(v4);

        Assertions.assertEquals(graph.findReachableVertices(v1), Set.of(v1, v2));
        Assertions.assertEquals(graph.findReachableVertices(v2), Set.of(v2));
        Assertions.assertEquals(graph.findReachableVertices(v3), Set.of(v1, v2, v3));
        Assertions.assertEquals(graph.findReachableVertices(v4), Set.of(v4));
        Assertions.assertEquals(graph.findReachableVertices(v5), Collections.emptySet());
        Assertions.assertTrue(checkInv());
    }

    @Test
    void testRemoveEdge() {
        graph.addEdge(v1, v2);
        Assertions.assertTrue(graph.removeEdge(v1, v2));
        Assertions.assertFalse(graph.removeEdge(v1, v2));
        Assertions.assertTrue(checkInv());
    }

    @Test
    void testRemoveVertex() {
        graph.addEdge(v1, v2);
        graph.addEdge(v2, v3);
        graph.addEdge(v4, v2);

        Assertions.assertTrue(graph.removeVertex(v2));
        Assertions.assertFalse(graph.removeVertex(v2));

        Assertions.assertEquals(graph.getTargets(v1), Set.of());
        Assertions.assertEquals(graph.getTargets(v4), Set.of());
        Assertions.assertEquals(graph.getSources(v3), Set.of());
        Assertions.assertTrue(checkInv());
    }

    @Test
    void testAddEdge() {
        Assertions.assertTrue(graph.addEdge(v1, v2));
        Assertions.assertFalse(graph.addEdge(v1, v2));

        Assertions.assertEquals(graph.getTargets(v1), Set.of(v2));
        Assertions.assertEquals(graph.getSources(v2), Set.of(v1));
        Assertions.assertTrue(checkInv());
    }

    @Test
    void testBidirectional() {
        Assertions.assertTrue(graph.addEdge(v1, v2));
        Assertions.assertTrue(graph.addEdge(v1, v4));
        Assertions.assertTrue(graph.addEdge(v2, v3));
        Assertions.assertTrue(graph.addEdge(v3, v2));
        Assertions.assertTrue(graph.addEdge(v3, v4));

        Assertions.assertEquals(graph.getTargets(v2), Set.of(v3));
        Assertions.assertEquals(graph.getTargets(v3), Set.of(v2, v4));

        Assertions.assertEquals(graph.getSources(v2), Set.of(v1, v3));
        Assertions.assertEquals(graph.getSources(v3), Set.of(v2));

        Assertions.assertTrue(graph.removeVertex(v3));
        Assertions.assertEquals(graph.getVertices(), Set.of(v1, v2, v4));

        Assertions.assertEquals(graph.getTargets(v2), Set.of());
        Assertions.assertEquals(graph.getTargets(v3), Set.of());

        Assertions.assertEquals(graph.getSources(v2), Set.of(v1));
        Assertions.assertEquals(graph.getSources(v3), Set.of());
        Assertions.assertEquals(graph.getSources(v4), Set.of(v1));
    }
}
