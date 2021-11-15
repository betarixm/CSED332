package edu.postech.csed332.homework6.events;

/**
 * An event indicating that a cell is set to have a given number.
 */
public class SetNumberEvent extends NumberEvent {
    public SetNumberEvent(int number) {
        super(number);
    }
}
