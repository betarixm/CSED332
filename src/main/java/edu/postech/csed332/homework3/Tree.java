package edu.postech.csed332.homework3;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.Set;

/**
 * A tree, specified as a subclass of Graph. A tree has a single root vertex, and there
 * exists exactly one path from the root to any vertex. This invariant is maintained by
 * restricting the operations for adding and removing vertices and edges.
 *
 * @param <N> type of vertices, which must be immutable and comparable
 */
public interface Tree<N extends Comparable<N>> extends Graph<N> {

    /**
     * Returns the root of this tree.
     *
     * @return the root, or {@code Optional.empty()} if the tree is empty
     */
    @NotNull Optional<N> getRoot();

    /**
     * Returns the distance of vertex from the root of this tree. For example, the root
     * has depth 0, and a child of the root has depth 1.
     *
     * @param vertex a vertex
     * @return the depth of vertex
     * @throws IllegalStateException    if the tree has no root
     * @throws IllegalArgumentException if vertex is not in the graph
     */
    int getDepth(@NotNull N vertex);

    /**
     * Returns the maximum depth in this tree.
     *
     * @return the maximum depth in this tree
     * @throws IllegalStateException if the tree has no root
     */
    int getHeight();

    /**
     * Returns the children of a given vertex in this tree, which are exactly the set
     * of the target vertices of the given vertex.
     *
     * @param vertex a vertex
     * @return the set of children of vertex in the tree
     */
    default @NotNull Set<N> getChildren(@NotNull N vertex) {
        return getTargets(vertex);
    }

    /**
     * Returns the parent of a given vertex in this tree, which is the unique source
     * vertex of the given vertex.
     *
     * @param vertex a vertex
     * @return the parent of vertex, or {@code Optional.empty()} if vertex is the root
     */
    default @NotNull Optional<N> getParent(N vertex) {
        return getSources(vertex).stream().findAny();
    }
}
