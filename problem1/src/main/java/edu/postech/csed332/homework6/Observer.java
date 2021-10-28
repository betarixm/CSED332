package edu.postech.csed332.homework6;

import edu.postech.csed332.homework6.events.Event;

/**
 * An observer that can observe subjects.
 */
interface Observer {
    /**
     * This method is called whenever the observed object is changed.
     *
     * @param caller the subject
     * @param arg    an argument passed to caller
     */
    void update(Subject caller, Event arg);
}
