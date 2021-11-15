package edu.postech.csed332.homework6;

import java.util.Optional;

/**
 * An even/odd Sudoku puzzle
 */
interface GameInstance {
    /**
     * Initial numbers (null if a cell is empty)
     *
     * @param i row index
     * @param j column index
     * @return the number in the (i+1) row of (j+1) column
     */
    Optional<Integer> getNumbers(int i, int j);

    /**
     * Flags for even or odd numbers
     *
     * @param i row index
     * @param j column index
     * @return return true if the (i+1) row of (j+1) column is even
     */
    boolean isEven(int i, int j);
}
