package edu.postech.csed332.homework3;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

/**
 * An implementation of Tree, where each vertex has a reference to its parent node but
 * no references to its children.
 *
 * @param <N> type of vertices, which must be immutable and comparable
 */
public class ParentPointerTree<N extends Comparable<N>> implements MutableTree<N> {

    private static class Node<V> {
        final @Nullable V parent;
        final int depth;

        Node(@Nullable V parent, int depth) {
            this.parent = parent;
            this.depth = depth;
        }
    }

    /**
     * A root vertex of this tree; {@code null} for an empty tree.
     */
    private @Nullable N root;

    /**
     * A map assigning to each vertex a pair of a parent reference and a depth. The parent
     * of the root is {@code null}. For example, a tree with four vertices {v1, v2, v3, v4}
     * and three edges {(v1,v2), (v1,v3), (v2,v4)}, where v1 is the root, is represented by
     * the map {v1 |-> (null,0), v2 |-> (v1,1), v3 |-> (v1,1), v4 |-> (v2,2)}.
     */
    private final @NotNull SortedMap<N, Node<N>> nodeMap;

    /**
     * Creates an empty parent pointer tree.
     */
    public ParentPointerTree() {
        this.root = null;
        this.nodeMap = new TreeMap<>();
    }

    @Override
    public @NotNull Optional<N> getRoot() {
        return root == null ? Optional.empty() : Optional.of(root);
    }

    @Override
    public int getDepth(@NotNull N vertex) {
        if (getRoot().isEmpty()) {
            throw new IllegalStateException();
        } else if (!nodeMap.containsKey(vertex)) {
            throw new IllegalArgumentException();
        }

        return nodeMap.get(vertex).depth;
    }

    @Override
    public int getHeight() {
        if (getRoot().isEmpty()) {
            throw new IllegalStateException();
        }

        return nodeMap.values().stream().mapToInt(n -> n.depth).max().orElse(0);
    }

    @Override
    public boolean containsVertex(@NotNull N vertex) {
        return nodeMap.containsKey(vertex);
    }

    @Override
    public boolean addVertex(@NotNull N vertex) {
        if (getRoot().isEmpty()) {
            root = vertex;
            nodeMap.put(vertex, new Node<>(null, 0));

            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean removeVertex(@NotNull N vertex) {
        if (containsVertex(vertex)) {
            Set<N> target = new HashSet<>(findReachableVertices(vertex));
            Iterator<N> iter = target.iterator();

            while (iter.hasNext()) {
                N v = iter.next();
                if (v == root) {
                    root = null;
                }
                nodeMap.remove(v);
                iter.remove();
            }

            return true;

        } else {
            return false;
        }
    }

    @Override
    public boolean containsEdge(@NotNull N source, @NotNull N target) {
        return nodeMap.get(target) != null && Objects.equals(nodeMap.get(target).parent, source);
    }

    @Override
    public boolean addEdge(@NotNull N source, @NotNull N target) {
        if (nodeMap.containsKey(source) && !nodeMap.containsKey(target)) {
            nodeMap.put(target, new Node<>(source, nodeMap.get(source).depth + 1));
            return true;
        } else {
            return false;
        }

    }

    @Override
    public boolean removeEdge(@NotNull N source, @NotNull N target) {
        if (containsEdge(source, target)) {
            removeVertex(target);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public @NotNull Set<N> getSources(N target) {
        N parent = nodeMap.get(target).parent;

        if (parent == null) {
            return Collections.emptySet();
        } else {
            return Set.of(parent);
        }
    }

    @Override
    public @NotNull Set<N> getTargets(N source) {
        return nodeMap.entrySet().stream()
                .filter(e -> e.getValue().parent == source)
                .map(Map.Entry::getKey)
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public @NotNull Set<N> getVertices() {
        return Collections.unmodifiableSet(nodeMap.keySet());
    }

    @Override
    public @NotNull Set<Edge<N>> getEdges() {
        return nodeMap.entrySet().stream()
                .filter(e -> e.getValue().parent != null)
                .map(e -> new Edge<>(e.getValue().parent, e.getKey()))
                .collect(Collectors.toUnmodifiableSet());
    }

    /**
     * Checks if all class invariants hold for this object
     *
     * @return true if the representation of this tree is valid
     */
    boolean checkInv() {
        Set<N> vertices = getVertices();
        Set<Edge<N>> edges = getEdges();

        for (Edge<N> e : edges) {
            N s = e.getSource();
            N t = e.getTarget();

            if ((!vertices.contains(s)) || (!vertices.contains(t))) {
                return false;
            }

            if (t == root) {
                return false;
            }

            if (root != null && !findReachableVertices(root).equals(getVertices())) {
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
