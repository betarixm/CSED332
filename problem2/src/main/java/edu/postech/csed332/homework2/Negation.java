package edu.postech.csed332.homework2;

import java.util.Map;
import java.util.Set;

/**
 * A Boolean expression whose top-level operator is ! (not).
 */
public class Negation implements Exp {
    private final Exp subexp;

    /**
     * Builds a negated expression of a given Boolean expression.
     *
     * @param exp a Boolean expression
     */
    public Negation(Exp exp) {
        subexp = exp;
    }

    /**
     * Returns the immediate sub-expression of this expression.
     *
     * @return a sub-expression
     */
    public Exp getSubexp() {
        return subexp;
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
        return "(! " + subexp + ")";
    }
}
