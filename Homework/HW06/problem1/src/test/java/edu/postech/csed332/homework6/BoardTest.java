package edu.postech.csed332.homework6;

import edu.postech.csed332.homework6.events.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoardTest {

    @Test
    void testCellConstruction() {
        Cell cell = new Cell(Cell.Type.EVEN);
        for (int n = 1; n <= 9; n++)
            if (n % 2 == 0)
                Assertions.assertTrue(cell.containsPossibility(n));
            else
                Assertions.assertFalse(cell.containsPossibility(n));
    }

    @Test
    void testBoardConstruction() {
        Board board = new Board(GameInstanceFactory.createGameInstance());

        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++) {
                Assertions.assertTrue(board.getRowGroup(i).contains(board.getCell(i, j)));
                Assertions.assertTrue(board.getColGroup(j).contains(board.getCell(i, j)));
                Assertions.assertTrue(board.getSquareGroup(i / 3, j / 3).contains(board.getCell(i, j)));
            }
    }

    @Test
    void testCellDisabled() {
        Cell cell = new Cell(Cell.Type.EVEN);
        cell.removePossibility(2);
        cell.removePossibility(4);
        cell.removePossibility(6);
        assertFalse(cell.emptyPossibility());

        final Boolean[] disabled = {false};
        cell.addObserver(new Observer() {
            @Override
            public void update(Subject caller, Event arg) {
                if (arg instanceof DisabledEvent)
                    disabled[0] = true;
            }
        });
        cell.removePossibility(8);
        assertTrue(disabled[0]);
        assertTrue(cell.emptyPossibility());
    }

    //TODO: add more test methods for non-GUI code, if you want.
}
