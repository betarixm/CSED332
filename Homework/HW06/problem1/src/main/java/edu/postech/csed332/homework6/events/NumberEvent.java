package edu.postech.csed332.homework6.events;

/**
 * An event with a number
 */
public class NumberEvent implements Event {
    private final int number;

    NumberEvent(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
