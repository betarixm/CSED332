package edu.postech.csed332.homework1;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A game board contains a set of units and a goal position. A game consists
 * of a number of rounds. For each round, all units perform their actions:
 * a monster can escape or move, and a tower can attack nearby monsters.
 * The following invariant conditions must be maintained throught the game:
 * <p>
 * (a) The position of each unit is inside the boundaries.
 * (b) Different ground units cannot be on the same tile.
 * (c) Different air units cannot be on the same tile.
 * <p>
 * TODO: implements all the unimplemented methods (marked with TODO)
 * NOTE: do not modify the signature of public methods (which will be
 * used for GUI and grading). But you can feel free to add new fields
 * or new private methods if needed.
 */
public class GameBoard {
    private final Position goal;
    private final int width, height;

    // TODO: add more fields to implement this class
    private final Map<Position, Set<Unit>> board;

    private int escaped;
    private int killed;

    /**
     * Creates a game board with a given width and height. The goal position
     * is set to the middle of the end column.
     *
     * @param width  of this board
     * @param height of this board
     */
    public GameBoard(int width, int height) {
        this.board = new HashMap<>();
        this.escaped = 0;
        this.killed = 0;

        this.width = width;
        this.height = height;
        this.goal = new Position(width - 1, height / 2);

        this.board.put(goal, new HashSet<>());
    }

    /**
     * Places a unit at a given position into this board.
     *
     * @param obj a unit to be placed
     * @param p   the position of obj
     * @throws IllegalArgumentException if p is outside the bounds of the board.
     */
    public void placeUnit(Unit obj, Position p) {
        if (!isValidPositionRange(p)) {
            throw new IllegalArgumentException();
        }

        if (!board.containsKey(p)) {
            board.put(p, new HashSet<>());
        }

        board.get(p).add(obj);
    }

    /**
     * Clears this game board. That is, all units are removed, and all numbers
     * for game statistics are reset to 0.
     */
    public void clear() {
        board.clear();
        escaped = 0;
        killed = 0;
    }

    /**
     * Returns the set of units at a given position in the board. Note that
     * multiple units can exist at the same position (e.g., ground and air)
     *
     * @param p a position
     * @return the set of units at position p
     */
    public Set<Unit> getUnitsAt(Position p) {
        return board.getOrDefault(p, Collections.emptySet());
    }

    /**
     * Returns the set of all monsters in this board.
     *
     * @return the set of all monsters
     */
    public Set<Monster> getMonsters() {
        return board.entrySet().stream()
                .flatMap(u -> u.getValue().stream())
                .filter(u -> u instanceof Monster)
                .map(u -> (Monster) u)
                .collect(Collectors.toSet());
    }

    /**
     * Returns the set of all towers in this board.
     *
     * @return the set of all towers
     */
    public Set<Tower> getTowers() {
        return board.entrySet().stream()
                .flatMap(u -> u.getValue().stream())
                .filter(u -> u instanceof Tower)
                .map(u -> (Tower) u)
                .collect(Collectors.toSet());
    }

    /**
     * Returns the position of a given unit
     *
     * @param obj a unit
     * @return the position of obj
     */
    public Position getPosition(Unit obj) {
        for (Position p : board.keySet()) {
            if (board.get(p).contains(obj)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Proceeds one round of a game. The behavior of this method is as follows:
     * (1) Every monster at the goal position escapes from the game.
     * (2) Each tower attacks nearby remaining monsters (using the attack method).
     * (3) All remaining monsters (neither escaped nor attacked) moves (using the goal method).
     */
    public void step() {
        // (1) Every monster at the goal position escapes from the game.
        escaped += getUnitsAt(goal).size();
        if(board.get(goal) != null) {
            board.get(goal).clear();
        }

        // (*) Entry Set
        Set<Map.Entry<Position, Set<Unit>>> towerEntry = board.entrySet().stream()
                .filter(e -> e.getValue().stream().anyMatch(u -> u instanceof Tower)).collect(Collectors.toSet());

        Set<Map.Entry<Position, Set<Unit>>> monsterEntry = board.entrySet().stream()
                .filter(e -> e.getValue().stream().anyMatch(u -> u instanceof Monster)).collect(Collectors.toSet());

        // (2) Each tower attacks nearby remaining monsters (using the attack method)
        for (Map.Entry<Position, Set<Unit>> e : towerEntry) {
            Position pTower = e.getKey();
            Set<Position> adjacent = new HashSet<>(Arrays.asList(
                    new Position(pTower.getX() - 1, pTower.getY()), new Position(pTower.getX() + 1, pTower.getY()),
                    new Position(pTower.getX(), pTower.getY() - 1), new Position(pTower.getX(), pTower.getY() + 1)
            ));

            Set<Monster> target = e.getValue().stream()
                    .filter(u -> u instanceof Tower)
                    .map(t -> ((Tower) t).attack())
                    .flatMap(Collection::stream)
                    .collect(Collectors.toSet());

            for (Position p : adjacent) {
                for (Monster m : target) {
                    killed += (getUnitsAt(p).remove(m) ? 1 : 0);
                }
            }
        }

        // (3) All remaining monsters (neither escaped nor attacked) moves (using the goal method).
        for (Map.Entry<Position, Set<Unit>> e : monsterEntry) {
            for (Monster m : e.getValue().stream().filter(u -> u instanceof Monster).map(m -> (Monster) m).collect(Collectors.toSet())) {
                Position pNew = m.move();

                if (pNew != e.getKey()) {
                    e.getValue().remove(m);
                    placeUnit(m, pNew);
                }

                if(!isValid()) {
                    System.out.println("asdf");
                }
            }
        }
    }

    public boolean isValidPositionRange(Position position) {
        return ((0 <= position.getX() && position.getX() < width) && (0 <= position.getY() && position.getY() < height));
    }

    /**
     * Checks whether the following invariants hold in the current game board:
     * (a) All units are in the boundaries.
     * (b) Different ground units cannot be on the same tile.
     * (c) Different air units cannot be on the same tile.
     *
     * @return true if there is no problem. false otherwise.
     */
    public boolean isValid() {
        for (Map.Entry<Position, Set<Unit>> e : board.entrySet()) {
            Position p = e.getKey();
            Set<Unit> units = e.getValue();

            // (a) All units are in the boundaries.
            if (units.size() > 0 && !isValidPositionRange(p)) {
                System.out.println("(A)");
                System.out.println(p.getX());
                System.out.println(p.getY());
                return false;
            }

            // (b) Different ground units cannot be on the same tile.
            if (units.stream().filter(Unit::isGround).count() > 1) {
                System.out.println("(B)");
                System.out.println(p.getX());
                System.out.println(p.getY());
                return false;
            }

            // (c) Different air units cannot be on the same tile.
            if (units.stream().filter(u -> !u.isGround()).count() > 1) {
                System.out.println("(C)");
                System.out.println(p.getX());
                System.out.println(p.getY());
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the number of the monsters in this board.
     *
     * @return the number of the monsters
     */
    public int getNumMobs() {
        return (int) board.entrySet().stream()
                .flatMap(u -> u.getValue().stream())
                .filter(u -> u instanceof Monster).count();
    }

    /**
     * Returns the number of the towers in this board.
     *
     * @return the number of the towers
     */
    public int getNumTowers() {
        return (int) board.entrySet().stream()
                .flatMap(u -> u.getValue().stream())
                .filter(u -> u instanceof Tower).count();
    }

    /**
     * Returns the number of the monsters removed so far in this game.
     * This number will be reset to 0 by the clear method.
     *
     * @return the number of the monsters removed
     */
    public int getNumMobsKilled() {
        return killed;
    }

    /**
     * Returns the number of the monsters escaped so far in this game
     * This number will be reset to 0 by the clear method.
     *
     * @return the number of the monsters escaped
     */
    public int getNumMobsEscaped() {
        return escaped;
    }

    /**
     * Returns the width of this board.
     *
     * @return width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the height of this board.
     *
     * @return height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns the goal position where the monster can escape.
     *
     * @return the goal position of this game
     */
    public Position getGoalPosition() {
        return goal;
    }

    public Set<Position> allocatedPosition(boolean isGround) {
        return board.entrySet().stream()
                .filter(e -> e.getValue().stream().anyMatch(u -> u.isGround() == isGround))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }
}
