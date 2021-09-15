package edu.postech.csed332.homework1;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public abstract class MovingMonster<T extends AttackTower> implements Monster {
    public static Set<Position> escapablePosition;

    private final Set<Position> prevPositions;
    private final List<Position> prevPositionHistory;

    private Position prevPosition;

    public abstract boolean isGround();

    public abstract GameBoard getBoard();

    static {
        escapablePosition = new HashSet<>();
    }

    public MovingMonster() {
        this.prevPositions = new HashSet<>();
        this.prevPositionHistory = new ArrayList<>();
        this.prevPosition = null;
    }

    public Set<Position> getEscapablePosition() {
        return escapablePosition;
    }

    public Set<Position> getPrevPositions() {
        return prevPositions;
    }

    private Set<Position> adjacentPositions(Position position) {
        return new HashSet<>(Arrays.asList(
                new Position(position.getX() - 1, position.getY()), new Position(position.getX() + 1, position.getY()),
                new Position(position.getX(), position.getY() - 1), new Position(position.getX(), position.getY() + 1)
        ));
    }

    public double calculateScore(Position position) {
        double W_KILL = Double.NEGATIVE_INFINITY;
        double W_SURVIVE = 100000000;
        double W_DIST = 20000;
        double W_NEAR = 20000;
        double W_VERTICAL = 5000;
        double W_ESCAPABLE = 25000;
        double W_NEAR_ESCAPABLE = 15000;
        double W_PREV = -10000000;
        double W_DIRECT_PREV = -Double.MAX_VALUE;
        double W_GOAL = Double.POSITIVE_INFINITY;
        double W_BLOCK = Double.NEGATIVE_INFINITY;
        double W_PREV_SEARCH = -100000;

        int PREV_SEARCH_DEPTH = 4;

        GameBoard board = this.getBoard();
        Position goal = board.getGoalPosition();
        Set<Position> pAdjacent = adjacentPositions(position);

        double score = 0;
        double dist = Math.pow(position.getX() - goal.getX(), 2) + Math.pow(goal.getY(), 2);

        // (Policy) +Inf at goal
        if (position == goal) {
            return W_GOAL;
        }

        // (Policy) -Inf at overlap
        if (board.getUnitsAt(position).stream().anyMatch(u -> isGround() == u.isGround())) {
            return W_BLOCK;
        }

        // (Policy) Check adjacent towers
        // (Policy) Bonus points for near-escape-positions
        for (Position p : pAdjacent) {
            Set<Unit> units = board.getUnitsAt(p);
            score += units.stream().anyMatch(u -> isGround() ? u instanceof GroundTower : u instanceof AirTower) ? W_KILL : W_SURVIVE;
            score += escapablePosition.contains(p) ? W_NEAR_ESCAPABLE : 0;
        }

        // (Policy) Deduct points for already visited positions; There's weight over time.
        for (int i = 1; i <= PREV_SEARCH_DEPTH; i++) {
            if (prevPositionHistory.size() >= i && prevPositionHistory.get(prevPositionHistory.size() - i) == position) {
                score += W_PREV_SEARCH * ((double) i / PREV_SEARCH_DEPTH);
            }
        }

        // (Policy) The distance to the goal
        score += 1.0 / (dist * 100 + W_DIST);

        // (Policy) Bonus points near the goal
        score += (dist < (board.getHeight() / 2.0)) ? W_NEAR : 0;

        // (Policy) Inducing monsters to move in the vertical directionally
        score += 1.0 / (Math.abs(position.getY() - goal.getY()) + W_VERTICAL);

        // (Policy) Deduct points for already visited positions
        score += (getPrevPositions().contains(position)) ? W_PREV : 0;

        // (Policy) High points for positions where other monsters have escaped
        score += (getEscapablePosition().contains(position)) ? W_ESCAPABLE : 0;

        // (Policy) Deduct points for the position monster visited right before
        score += (prevPosition.getX() == position.getX() && prevPosition.getY() == position.getY()) ? W_DIRECT_PREV : 0;

        return score;
    }

    @Override
    public Position move() {
        GameBoard board = getBoard();
        Set<Position> allocatedPosition = board.allocatedPosition(isGround());
        Set<Position> escapablePosition = getEscapablePosition();

        Position position = board.getPosition(this);
        Position curMaxPosition = position;

        Set<Position> pAdjacent = GameBoard.adjacentPositions(position);
        Predicate<Position> isAllocable = (p) -> board.isValidPositionRange(p)
                && !allocatedPosition.contains(p)
                && board.getUnitsAt(p).stream().noneMatch(u -> isGround() == u.isGround());

        double curMaxScore = Double.NEGATIVE_INFINITY;
        double curScore;

        if (prevPosition == null) {
            prevPosition = position;
        }

        for (Position p : pAdjacent.stream().filter(isAllocable).collect(Collectors.toSet())) {
            curScore = calculateScore(p);
            if (curScore > curMaxScore) {
                curMaxScore = curScore;
                curMaxPosition = p;
            }
        }

        prevPositions.add(curMaxPosition);
        prevPositionHistory.add(curMaxPosition);

        prevPosition = position;

        if (curMaxPosition == board.getGoalPosition()) {
            escapablePosition.addAll(prevPositions);
        } else {
            prevPositions.removeAll(escapablePosition);
        }

        return curMaxPosition;
    }
}
