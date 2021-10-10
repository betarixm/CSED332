package edu.postech.csed332.homework3;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A directed graph consisting of a set of vertices and edges. Vertices are given by
 * any immutable type N. Edges have source and target vertices (that can be identical).
 *
 * @param <N> type of vertices, which must be immutable and comparable
 */
public interface Graph<N extends Comparable<N>> {

    /**
     * Returns true if this graph contains a given vertex.
     *
     * @param vertex a vertex
     * @return {@code true} if the graph contains vertex
     */
    boolean containsVertex(@NotNull N vertex);

    /**
     * Returns true if this graph contains a directed edge from source to target.
     *
     * @param source a source vertex
     * @param target a target vertex
     * @return true if source and target is connected by an edge
     */
    boolean containsEdge(@NotNull N source, @NotNull N target);

    /**
     * Returns the set of source vertices that are connected to a given target vertex,
     * by a directed edge from each source to the given target in the graph. If target
     * is not in the graph, returns the empty set.
     *
     * @param target a vertex
     * @return the set of vertices that have an edge to target, immutable
     */
    @NotNull Set<N> getSources(N target);

    /**
     * Returns the set of target vertices that are connected to a given source vertex,
     * by a directed edge from the given source to each target in the graph. If source
     * is not in the graph, returns the empty set.
     *
     * @param source a vertex
     * @return the set of vertices that have an edge from source, immutable
     */
    @NotNull Set<N> getTargets(N source);

    /**
     * Returns all the vertices in this graph.
     *
     * @return the set of vertices in the graph, immutable
     */
    @NotNull Set<N> getVertices();

    /**
     * Returns all the edges in this graph.
     *
     * @return the set of edges in the graph, immutable
     */
    @NotNull Set<Edge<N>> getEdges();

    /**
     * Returns all the vertices that are reachable from a given vertex in this graph,
     * based on a breadth-first search strategy. For example, consider a graph with
     * vertices {v1, v2, v3, v4} and edges {(v1,v1), (v1,v2), (v3,v1), (v3,v2)}. Then,
     * findReachableVertices(v2) = {v2}, findReachableVertices(v3) = {v1, v2, v3},
     * findReachableVertices(v4) = {v4}, and findReachableVertices(v5) = {}.
     *
     * @param vertex a vertex
     * @return the set of reachable vertices from {@code vertex}, immutable
     */
    default @NotNull Set<N> findReachableVertices(@NotNull N vertex) {
        Set<N> seen = new HashSet<>();
        Set<N> frontier = new HashSet<>();

        if (containsVertex(vertex))
            frontier.add(vertex);

        while (!seen.containsAll(frontier)) {
            seen.addAll(frontier);
            frontier = frontier.stream()
                    .flatMap(n -> getTargets(n).stream())
                    .filter(n -> !seen.contains(n))
                    .collect(Collectors.toSet());
        }
        return Collections.unmodifiableSet(seen);
    }
}
