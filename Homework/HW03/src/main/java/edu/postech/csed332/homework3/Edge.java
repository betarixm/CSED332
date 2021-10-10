package edu.postech.csed332.homework3;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * An edge of a graph, given by a pair of two vertices of type V.
 *
 * @param <N> type of vertices, which must be immutable and comparable
 */
class Edge<N extends Comparable<N>> implements Comparable<Edge<N>> {
    private final N source;
    private final N target;

    Edge(N source, N target) {
        this.source = source;
        this.target = target;
    }

    N getSource() {
        return source;
    }

    N getTarget() {
        return target;
    }

    @Override
    public int compareTo(@NotNull Edge<N> o) {
        int c1 = this.source.compareTo(o.source);
        return c1 != 0 ? c1 : this.target.compareTo(o.target);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge<?> edge = (Edge<?>) o;
        return Objects.equals(source, edge.source) &&
                Objects.equals(target, edge.target);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, target);
    }

    @Override
    public String toString() {
        return "(" + source + "," + target + ")";
    }
}
