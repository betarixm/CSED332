package edu.postech.csed332.homework3;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

/**
 * An implementation of Graph with an adjacency list representation.
 * NOTE: you should NOT add more member variables to this class.
 *
 * @param <N> type of vertices, which must be immutable and comparable
 */
public class AdjacencyListGraph<N extends Comparable<N>> implements MutableGraph<N> {

    /**
     * A map from vertices to the sets of their adjacent vertices. For example, a graph
     * with four vertices {v1, v2, v3, v4} and four edges {(v1,v1), (v1,v2), (v3,v1), (v3,v2)}
     * is represented by the map {v1 |-> {v1,v2}, v2 |-> {}, v3 -> {v1,v2}, v4 -> {}}.
     */
    private final @NotNull SortedMap<N, SortedSet<N>> adjMap;

    /*
     * Abstraction Function:
     *   AF(r) = a matrix, M, such that,
     *     M_{i,j} = (Edge (i, j) exists) ? 1 : 0
     *     where v1 = 1, v2 = 2, ...
     */

    /*
     * Class Invariant:
     *   - adjMap is not null.
     *   - adjMap does not have duplicate keys.
     *   - adjMap's each value (SortedSet<N>) consists only of vertices in the key-set.
     */

    /**
     * Creates an empty graph
     */
    public AdjacencyListGraph() {
        adjMap = new TreeMap<>();
    }

    @Override
    public boolean containsVertex(@NotNull N vertex) {
        return adjMap.containsKey(vertex);
    }

    @Override
    public boolean addVertex(@NotNull N vertex) {
        if (containsVertex(vertex)) {
            return false;
        } else {
            adjMap.put(vertex, new TreeSet<>());
            return true;
        }
    }

    @Override
    public boolean removeVertex(@NotNull N vertex) {
        if (containsVertex(vertex)) {
            Set<N> sources = getSources(vertex);

            for (N s : sources) {
                adjMap.get(s).remove(vertex);
            }

            adjMap.remove(vertex);

            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean containsEdge(@NotNull N source, @NotNull N target) {
        return adjMap.containsKey(source)
                && adjMap.containsKey(target)
                && adjMap.get(source).contains(target);
    }

    @Override
    public boolean addEdge(@NotNull N source, @NotNull N target) {
        if (containsEdge(source, target)) {
            return false;
        } else {
            if (!adjMap.containsKey(source)) {
                addVertex(source);
            }

            if (!adjMap.containsKey(target)) {
                addVertex(target);
            }

            adjMap.get(source).add(target);

            return true;
        }
    }

    @Override
    public boolean removeEdge(@NotNull N source, @NotNull N target) {
        if (containsEdge(source, target)) {
            adjMap.get(source).remove(target);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public @NotNull Set<N> getSources(N target) {
        return adjMap.keySet().stream().filter(k -> {
            return adjMap.get(k).contains(target);
        }).collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public @NotNull Set<N> getTargets(N source) {
        if (!adjMap.containsKey(source)) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet(adjMap.get(source));
    }

    @Override
    public @NotNull Set<N> getVertices() {
        return Collections.unmodifiableSet(adjMap.keySet());
    }

    @Override
    public @NotNull Set<Edge<N>> getEdges() {
        return adjMap.entrySet().stream()
                .flatMap(entry -> entry.getValue().stream().map(n -> new Edge<>(entry.getKey(), n)))
                .collect(Collectors.toUnmodifiableSet());
    }

    /**
     * Checks if all class invariants hold for this object.
     *
     * @return true if the representation of this graph is valid
     */
    boolean checkInv() {
        Set<Edge<N>> edges = getEdges();

        for (Edge<N> edge: edges) {
            if((!adjMap.containsKey(edge.getSource())) || (!adjMap.containsKey(edge.getTarget()))) {
                return false;
            }
        }

        return true;
    }

    /**
     * Provides a human-readable string representation for the abstract value of the graph
     *
     * @return a string representation
     */
    @Override
    public String toString() {
        return String.format("[vertex: {%s}, edge: {%s}]",
                getVertices().stream().map(N::toString).collect(Collectors.joining(", ")),
                getEdges().stream().map(Edge::toString).collect(Collectors.joining(", ")));
    }
}
