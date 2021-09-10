package edu.postech.csed332.homework1;

/**
 * A monster that can move inside a game board.
 * NOTE: do not modify this file!
 */
public interface Monster extends Unit {

    /**
     * Returns the position of this monster in the next step. 
     *
     * @return the position of the monster
     */
    Position move();
}
