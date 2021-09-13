package edu.postech.csed332.homework1;

import java.util.Objects;

/**
 * A position of a game board, given by a pair (x, y).
 * NOTE: do not modify this file!
 */
public class Position {
    private final int x, y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns a new position based on this position with respect to given differences.
     *
     * @param dx x-axis difference
     * @param dy y-axis difference
     * @return the new position
     */
    public Position getRelativePosition(int dx, int dy) {
        return new Position(getX() + dx, getY() + dy);
    }

    /**
     * Returns the distance between this position (x,y) and a given position (x',y').
     * The distance is defined by |x - x'| + |y - y'| (which is called L1 distance).
     *
     * @param p a position
     * @return the distance between this position and p
     */
    public int getDistance(Position p) {
        return Math.abs(p.getX() - getX()) + Math.abs(p.getY() - getY());
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return x == ((Position) o).x && y == ((Position) o).y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}