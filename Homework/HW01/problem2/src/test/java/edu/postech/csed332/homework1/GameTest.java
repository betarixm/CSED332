package edu.postech.csed332.homework1;

import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    @Test
    void testGameBoard() {
        GameBoard board = new GameBoard(5, 3);
        assertEquals(board.getWidth(), 5);
        assertEquals(board.getHeight(), 3);
    }

    @Test
    void testPlaceUnit() {
        GameBoard board = new GameBoard(5, 3);
        Unit obj = new GroundMob(board);
        Position pos = new Position(0, 0);

        board.placeUnit(obj, pos);
        assertTrue(board.getUnitsAt(pos).contains(obj));
        assertEquals(board.getNumMobs(), 1);
    }

    @Test
    void testPlaceUnitInvalid() {
        GameBoard board = new GameBoard(5, 3);
        Unit obj = new GroundMob(board);
        Position pos = new Position(5, 0);

        assertThrows(IllegalArgumentException.class, () -> {
            board.placeUnit(obj, pos);
        });
    }

    @Test
    void testPlaceTwoUnits() {
        GameBoard board = new GameBoard(5, 3);
        Unit o1 = new AirMob(board);
        Unit o2 = new GroundTower(board);
        Position p = new Position(0, 0);

        board.placeUnit(o1, p);
        board.placeUnit(o2, p);
        assertTrue(board.getUnitsAt(p).contains(o1));
        assertTrue(board.getUnitsAt(p).contains(o2));
        assertEquals(board.getNumMobs(), 1);
        assertEquals(board.getNumTowers(), 1);
    }

    @Test
    void testGroundAttack() {
        GameBoard board = new GameBoard(5, 3);
        Tower o1 = new GroundTower(board);
        Position p1 = new Position(1, 1);
        Monster o2 = new GroundMob(board);
        Position p2 = new Position(1, 2);

        board.placeUnit(o1, p1);
        board.placeUnit(o2, p2);
        assertTrue(o1.attack().contains(o2));
    }
}
