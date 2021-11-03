package edu.postech.csed332.homework6;

import edu.postech.csed332.homework6.events.Event;
import edu.postech.csed332.homework6.events.SetFixedNumberEvent;
import edu.postech.csed332.homework6.events.SetNumberEvent;
import edu.postech.csed332.homework6.events.UnsetNumberEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

/**
 * A group that observes a set of cells, and maintains the invariant: if one of the members has a particular value,
 * none of its other members can have the value as a possibility.
 */
public class Group implements Observer {
    //TODO: add private member variables for Board
    Set<Cell> cellSet;

    /**
     * Creates an empty group.
     */
    Group() {
        cellSet = new HashSet<>();
    }

    /**
     * Adds a cell to this group. Use cell.addGroup to register this group.
     *
     * @param cell a cell to be added
     */
    void addCell(Cell cell) {
        cell.addGroup(this);
        cellSet.add(cell);
    }

    /**
     * Returns true if a given cell is a part of this group
     *
     * @param cell a cell
     * @return true if this group contains cell
     */
    @NotNull
    Boolean contains(@NotNull Cell cell) {
        return cellSet.contains(cell);
    }

    /**
     * Returns true if a given number is available in the group
     *
     * @param number a number
     * @return true if no cell in the group has a given number.
     */
    @NotNull
    public Boolean isAvailable(int number) {
        boolean result = true;

        for (Cell cell : cellSet) {
            if (cell.getNumber().get() == number)
                result = false;
        }

        return result;
    }

    /**
     * Whenever a cell is changed, this function is called. Two kinds of events, SetNumberEvent and UnsetNumberEvent,
     * should be handled here.
     *
     * @param caller the subject
     * @param arg    an argument
     */
    @Override
    public void update(Subject caller, Event arg) {
        int number;

        if (arg instanceof SetFixedNumberEvent){
            number = ((SetFixedNumberEvent)arg).getNumber();
            for (Cell cell : cellSet)
                cell.setFixedPossibility(number);
        } else if (arg instanceof SetNumberEvent) {
            number = ((SetNumberEvent) arg).getNumber();
            for (Cell cell : cellSet)
                cell.removePossibility(number);
        } else if (arg instanceof UnsetNumberEvent) {
            number = ((UnsetNumberEvent) arg).getNumber();
            for (Cell cell : cellSet)
                cell.addPossibility(number);
        }
    }
}
