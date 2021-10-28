package edu.postech.csed332.homework6.events;

/**
 * An event indicating that the number of a cell is removed.
 */
public class UnsetNumberEvent extends NumberEvent {
    public UnsetNumberEvent(int number) {
        super(number);
    }
}
