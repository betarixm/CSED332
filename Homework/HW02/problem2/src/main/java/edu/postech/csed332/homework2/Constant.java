package edu.postech.csed332.homework2;

import java.util.Collections;
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
        return Collections.emptySet();
    }

    @Override
    public Boolean evaluate(Map<Integer, Boolean> assignment) {
        return value;
    }

    @Override
    public Exp simplify() {
        return new Constant(value);
    }

    @Override
    public String toString() {
        return Boolean.toString(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (this.getClass() != obj.getClass()) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        Constant that = (Constant) obj;

        return this.value == that.value;
    }

    @Override
    public int hashCode() {
        return value ? 1 : 2;
    }
}