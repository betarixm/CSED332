package edu.postech.csed332.homework2;

import java.util.Map;
import java.util.Set;

/**
 * A Boolean constant, either true or false.
 */
public class Constant implements Exp {
    private final boolean value;

    /**
     * Builds a Boolean expression, given a Boolean value.
     *
     * @param value a Boolean value
     */
    public Constant(boolean value) {
        this.value = value;
    }

    /**
     * @return true or false
     */
    public boolean getValue() {
        return value;
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
        return Boolean.toString(value);
    }
}