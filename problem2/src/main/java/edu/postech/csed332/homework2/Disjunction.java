package edu.postech.csed332.homework2;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A Boolean expression whose top-level operator is || (or).
 */
public class Disjunction implements Exp {
    private final List<Exp> subexps;

    /**
     * Builds a disjunction of a given list of Boolean expressions
     *
     * @param exps
     */
    public Disjunction(Exp... exps) {
        subexps = Arrays.asList(exps);
    }

    /**
     * Returns the immediate sub-expressions of this expression.
     *
     * @return a list of sub-expressions
     */
    public List<Exp> getSubexps() {
        return subexps;
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
        return "(" + subexps.stream().map(i -> i.toString()).collect(Collectors.joining(" || ")) + ")";
    }
}