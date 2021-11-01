package edu.postech.csed332.homework6;

import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

/**
 * An even-odd Sudoku board
 */
public class Board {
    Cell[][] cells;
    Group[] rowSet, colSet;
    Group[] squareSet;


    /**
     * Creates an even-odd Sudoku board
     *
     * @param game an initial configuration
     */
    Board(@NotNull GameInstance game) {
        int number;
        int i, j;
        cells = new Cell[9][9];
        rowSet = new Group[9];
        colSet = new Group[9];
        squareSet = new Group[9];

        for (i = 0; i < rowSet.length; i++) {
            rowSet[i] = new Group();
            colSet[i] = new Group();
            squareSet[i] = new Group();
        }

        for (i = 0; i < cells.length; i++) {
            for (j = 0; j < cells[i].length; j++) {
                Cell.Type type = game.isEven(i, j) ? Cell.Type.EVEN : Cell.Type.ODD;
                Cell cell = new Cell(type);

                cells[i][j] = cell;
                rowSet[i].addCell(cell);
                colSet[j].addCell(cell);
                squareSet[(i / 3) * 3 + j / 3].addCell(cell);
            }
        }

        for (i = 0; i < cells.length; i++) {
            for (j = 0; j < cells[i].length; j++) {
                Cell cell = getCell(i, j);
                number = game.getNumbers(i, j).orElse(0);

                if (number > 0) {
                    cell.setNumber(number);
                }
            }
        }
    }

    /**
     * Returns a cell in the (i+1)-th row of (j+1)-th column, where 0 <= i, j < 9.
     *
     * @param i a row index
     * @param j a column index
     * @return a cell in the (i+1)-th row of (j+1)-th column
     */
    @NotNull
    Cell getCell(int i, int j) {
        return cells[i][j];
    }

    /**
     * Returns a group for the (i+1)-th row, where 0 <= i < 9.
     *
     * @param i a row index
     * @return a group for the (i+1)-th row
     */
    @NotNull
    Group getRowGroup(int i) {
        return rowSet[i];
    }

    /**
     * Returns a group for the (j+1)-th column, where 0 <= j < 9.
     *
     * @param j a column index
     * @return a group for the (j+1)-th column
     */
    @NotNull
    Group getColGroup(int j) {
        return colSet[j];
    }

    /**
     * Returns a square group for the (n+1)-th row of (m+1)-th column, where 1 <= n, m <= 3
     *
     * @param n a square row index
     * @param m a square column index
     * @return a square group for the (n+1)-th row of (m+1)-th column
     */
    @NotNull
    Group getSquareGroup(int n, int m) {
        return squareSet[n * 3 + m];
    }
}
