package edu.postech.csed332.homework2;

import java.util.Map;
import java.util.Set;

/**
 * A Boolean variable, identified by positive integers
 */
public class Variable implements Exp {
    private final int identifier;

    /**
     * Builds a Boolean variable identified by a given number. Throws
     * IllegalArgumentException if the number is not a positive integer.
     *
     * @param number
     */
    public Variable(int number) {
        if (number > 0)
            this.identifier = number;
        else
            throw new IllegalArgumentException("Variable Id must be a positive integer");
    }

    /**
     * Returns the number identifier of this variable.
     *
     * @return the number
     */
    public Integer getIdentifier() {
        return identifier;
    }

    @Override
    public Set<Integer> vars() {
        // TODO: implement this
        return null;
    }

    @Override
    public Boolean evaluate(Map<Integer, Boolean> assignment) {
        // TODO: implement this
        return null;
    }

    @Override
    public Exp simplify() {
        // TODO: implement this
        return null;
    }

    @Override
    public String toString() {
        return "p" + identifier;
    }
}