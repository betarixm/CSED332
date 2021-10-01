package edu.postech.csed332.homework3;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

/**
 * An implementation of Tree that delegates to a given instance of Graph. This class
 * is a wrapper of a MutableGraph instance that enforces the Tree invariant.
 * NOTE: you should NOT add more member variables to this class.
 *
 * @param <N> type of vertices, which must be immutable and comparable
 */
public class DelegateTree<N extends Comparable<N>> implements MutableTree<N> {

    /**
     * A root vertex of this tree; {@code null} for an empty tree.
     */
    private @Nullable N root;

    /**
     * The underlying graph of this tree
     */
    private final @NotNull MutableGraph<N> delegate;

    /**
     * A map assigning a depth to each vertex in this tree
     */
    private final @NotNull Map<N, Integer> depthMap;

    /*
     * Abstraction Function:
     *   AF(r) = a matrix, M, such that,
     *     M_{i,j} = (Edge (i, j) exists) ? 1 : 0
     *     where v1 = 1, v2 = 2, ...
     *   (*) Note that M is upper triangular matrix of which diagonal entries are zero.
     */

    /*
     * Class Invariant:
     *   - AF(r) is upper triangular matrix of which diagonal entries are zero.
     *   - delegate is not null.
     *   - depthMap is not null.
     *   - There's no edge targeting root.
     *   - All vertices must be reached from root.
     *   - All vertices must have only one parent.
     *   - All edges must consist of existing vertices.
     */

    /**
     * Creates an empty tree that delegates to a given graph.
     *
     * @param emptyGraph an empty graph
     * @throws IllegalArgumentException if {@code emptyGraph} is not empty
     */
    public DelegateTree(@NotNull MutableGraph<N> emptyGraph) {
        if (!emptyGraph.getVertices().isEmpty())
            throw new IllegalArgumentException();

        delegate = emptyGraph;
        depthMap = new HashMap<>();
    }

    @Override
    public @NotNull Optional<N> getRoot() {
        return root == null ? Optional.empty() : Optional.of(root);
    }

    @Override
    public int getDepth(@NotNull N vertex) {
        if (getRoot().isEmpty()) {
            throw new IllegalStateException();
        } else if (!delegate.containsVertex(vertex)) {
            throw new IllegalArgumentException();
        }

        return depthMap.get(vertex);
    }

    @Override
    public int getHeight() {
        if (getRoot().isEmpty()) {
            throw new IllegalStateException();
        }
        return Collections.max(depthMap.entrySet(), Map.Entry.comparingByValue()).getValue();
    }

    @Override
    public boolean containsVertex(@NotNull N vertex) {
        return delegate.containsVertex(vertex);
    }

    @Override
    public boolean addVertex(@NotNull N vertex) {
        if (getRoot().isEmpty()) {
            delegate.addVertex(vertex);
            depthMap.put(vertex, 0);
            root = vertex;

            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean removeVertex(@NotNull N vertex) {
        if (delegate.containsVertex(vertex)) {
            removeVertexRecursive(vertex);
            return true;
        } else {
            return false;
        }
    }

    private void removeVertexRecursive(@NotNull N vertex) {
        for (N v : delegate.getTargets(vertex)) {
            removeVertexRecursive(v);
        }

        delegate.removeVertex(vertex);
        depthMap.remove(vertex);

        if (root == vertex) {
            root = null;
        }
    }

    @Override
    public boolean containsEdge(@NotNull N source, @NotNull N target) {
        return delegate.containsEdge(source, target);
    }

    @Override
    public boolean addEdge(@NotNull N source, @NotNull N target) {
        if (delegate.containsVertex(source) && !delegate.containsVertex(target)) {
            delegate.addEdge(source, target);
            depthMap.put(target, depthMap.get(source) + 1);

            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean removeEdge(@NotNull N source, @NotNull N target) {
        if (containsEdge(source, target)) {
            removeVertexRecursive(target);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public @NotNull Set<N> getSources(N target) {
        return Collections.unmodifiableSet(delegate.getSources(target));
    }

    @Override
    public @NotNull Set<N> getTargets(N source) {
        return Collections.unmodifiableSet(delegate.getTargets(source));
    }

    @Override
    public @NotNull Set<N> getVertices() {
        return Collections.unmodifiableSet(delegate.getVertices());
    }

    @Override
    public @NotNull Set<Edge<N>> getEdges() {
        return Collections.unmodifiableSet(delegate.getEdges());
    }

    /**
     * Checks if all class invariants hold for this object
     *
     * @return true if the representation of this tree is valid
     */
    boolean checkInv() {
        Set<N> vertices = getVertices();
        Set<Edge<N>> edges = getEdges();

        for(Edge<N> e : edges) {
            N s = e.getSource();
            N t = e.getTarget();

            if ((!vertices.contains(s)) || (!vertices.contains(t))) {
                return false;
            }

            if (t == root) {
                return false;
            }

            if (root != null && !delegate.findReachableVertices(root).equals(getVertices())) {
                return false;
            }
        }

        for(N n : vertices) {
            if (n != root && getSources(n).size() != 1) {
                return false;
            }
        }

        return true;
    }

    /**
     * Provides a human-readable string representation for the abstract value of the tree
     *
     * @return a string representation
     */
    @Override
    public String toString() {
        return String.format("[root: %s, vertex: {%s}, edge: {%s}]",
                root,
                getVertices().stream().map(N::toString).collect(Collectors.joining(", ")),
                getEdges().stream().map(Edge::toString).collect(Collectors.joining(", ")));
    }
}
