package edu.postech.csed332.homework1;

/**
 * An objects on a game board, either a ground unit or an air unit.
 * NOTE: do not modify this file!
 */
public interface Unit {

    /**
     * Checks whether this object is a ground object.
     *
     * @return true if ground; otherwise, false
     */
    boolean isGround();

    /**
     * Returns the game board that this object belongs to.
     *
     * @return the game board
     */
    GameBoard getBoard();

    /**
     * Returns the position of this unit in the board.
     *
     * @return the position of this unit
     */
    default Position getPosition() {
        return getBoard().getPosition(this);
    }
}
