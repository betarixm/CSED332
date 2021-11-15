package edu.postech.csed332.homework6;

import edu.postech.csed332.homework6.events.Event;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * A subject that maintains a set of observers
 */
abstract class Subject {
    private final ArrayList<Observer> observers = new ArrayList<>();

    /**
     * Adds an observer to the set of observers, provided that it is not already added.
     *
     * @param o an observer to be added
     */
    public void addObserver(@NotNull Observer o) {
        if (!observers.contains(o))
            observers.add(o);
    }

    /**
     * Notify all the observers with an argument of type T
     *
     * @param arg an argument
     */
    void notifyObservers(@NotNull Event arg) {
        for (var o : observers)
            o.update(this, arg);
    }
}
