package edu.postech.csed332.homework3;

import org.jetbrains.annotations.NotNull;

/**
 * A tree with operations for adding and removing vertices and edges. Note that this
 * interface is exactly the same as MutableGraph, but has different contracts.
 *
 * @param <N> type of vertices, which must be immutable and comparable
 */
public interface MutableTree<N extends Comparable<N>> extends Tree<N>, MutableGraph<N> {

    /**
     * Adds a vertex to this tree and sets it as the root of the tree, if the tree is
     * empty. If the tree contains any vertices, the graph is not changed (because
     * otherwise that vertex will be an isolated vertex not reachable from the root).
     *
     * @param vertex a root vertex to add
     * @return {@code true} if the graph is modified, {@code false} otherwise
     */
    @Override
    boolean addVertex(@NotNull N vertex);

    /**
     * Removes a given vertex, and all vertices that are descendants of the given
     * vertex, from this graph. For example, if the root is removed, all vertices are
     * removed and the tree becomes empty.
     *
     * @param vertex a vertex to remove
     * @return {@code true} if the graph is modified, {@code false} otherwise
     */
    @Override
    boolean removeVertex(@NotNull N vertex);

    /**
     * Add an edge from source to target to the tree, if source is already in the tree
     * and target is not in the tree. Otherwise, the graph is not changed.
     *
     * @param source a parent vertex
     * @param target a child vertex
     * @return {@code true} if the graph is modified, {@code false} otherwise
     */
    @Override
    boolean addEdge(@NotNull N source, @NotNull N target);

    /**
     * Removes the edge from source to target, causing the target and all its
     * descendants to be removed.
     *
     * @param source a parent vertex
     * @param target a child vertex
     * @return {@code true} if the graph is modified, {@code false} otherwise
     */
    @Override
    boolean removeEdge(@NotNull N source, @NotNull N target);
}
