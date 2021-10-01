package edu.postech.csed332.homework3;

import org.jetbrains.annotations.NotNull;

/**
 * A direct graph with operations for adding and removing vertices and edges. Note
 * that vertices of MutableGraph are still immutable, but the graph itself is mutable
 * by adding or removing vertices and edges.
 *
 * @param <N> type of vertices, which must be immutable and comparable
 */
public interface MutableGraph<N extends Comparable<N>> extends Graph<N> {

    /**
     * Adds a given vertex to this graph, and returns {@code true} if the graph is
     * changed. If the graph already contains the vertex, the graph is not changed and
     * this method returns {@code false}.
     *
     * @param vertex a vertex to add
     * @return {@code true} if the graph is modified, {@code false} otherwise
     */
    boolean addVertex(@NotNull N vertex);

    /**
     * Removes a vertex from this graph, together with all edges to or from the vertex,
     * and returns {@code true} if the graph is changed. If the vertex is not in the
     * graph, the graph is not changed and this method returns {@code false}.
     *
     * @param vertex a vertex to remove
     * @return {@code true} if the graph is modified, {@code false} otherwise
     */
    boolean removeVertex(@NotNull N vertex);

    /**
     * Adds an edge from source to target to this graph, and returns {@code true} if
     * the graph is changed. If source and/or target vertices are not in the graph,
     * missing vertices are added to the graph. If the graph already contains the edge,
     * the graph is not changed and this method returns {@code false}.
     *
     * @param source a source vertex
     * @param target a target vertex
     * @return {@code true} if the graph is modified, {@code false} otherwise
     */
    boolean addEdge(@NotNull N source, @NotNull N target);

    /**
     * Removes the edge from source to target from this graph, and returns {@code true}
     * if the graph is changed. If the graph does not contains the edge, the graph is
     * not changed and this method returns {@code false}.
     *
     * @param source a source vertex
     * @param target a target vertex
     * @return {@code true} if the graph is modified, {@code false} otherwise
     */
    boolean removeEdge(@NotNull N source, @NotNull N target);
}
