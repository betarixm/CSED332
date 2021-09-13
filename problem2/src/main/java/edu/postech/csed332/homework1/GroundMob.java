package edu.postech.csed332.homework1;

/**
 * A ground monster that moves towards to the goal position of the board, while
 * satisfying the game board invariants. The class GroundMob implements the interface
 * Monster. TODO: implement all unimplemented methods.
 * Feel free to modify this file, e.g., adding new fields or methods. If needed,
 * you can define a new (abstract) super class that this class inherits.
 *
 * @see GameBoard#isValid
 */

public class GroundMob extends MovingMonster<GroundTower> {
    private final GameBoard board;

    public GroundMob(GameBoard board) {
        super();
        this.board = board;
    }

    @Override
    public boolean isGround() {
        return true;
    }

    @Override
    public GameBoard getBoard() {
        return board;
    }
}
