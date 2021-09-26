package edu.postech.csed332.homework2;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A Boolean expression whose top-level operator is || (or).
 */
public class Disjunction extends BinaryExp {
    /**
     * Builds a disjunction of a given list of Boolean expressions
     *
     * @param exps
     */
    public Disjunction(Exp... exps) {
        super(Arrays.asList(exps));
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
    public Boolean evaluate(Map<Integer, Boolean> assignment) {
        return subexps.stream().anyMatch(e -> e.evaluate(assignment));
    }

    @Override
    public Exp simplify() {
        Constant TRUE = new Constant(true);

        // (0) Simplify subexps
        List<Exp> exps = subexps.stream().map(Exp::simplify).toList();

        // (1) Identity and idempotent laws
        exps = exps.stream().filter(e -> {
            return !((e instanceof Constant)
                    && !((Constant) e).getValue());
        }).toList();

        exps = exps.stream().distinct().toList();

        // (2) Domination and negation laws
        if (exps.stream().anyMatch(e -> e.equals(TRUE))) {
            return TRUE;
        }

        List<Exp> negationSubexps = exps.stream()
                .filter(e -> e instanceof Negation)
                .map(e -> ((Negation) e).getSubexp())
                .toList();

        if (exps.stream().anyMatch(negationSubexps::contains)) {
            return TRUE;
        }

        // (4) Absorption laws
        List<Conjunction> conjunctionSubexps = exps.stream()
                .filter(e -> e instanceof Conjunction)
                .map(e -> (Conjunction) e)
                .toList();

        List<Exp> finalExps = exps;
        conjunctionSubexps = conjunctionSubexps.stream().filter(d -> {
            for (Exp _d : d.getSubexps()) {
                if (finalExps.contains(_d)) {
                    return false;
                }
            }
            return true;
        }).toList();

        List<Conjunction> finalConjunctionSubexps = conjunctionSubexps;
        exps = exps.stream()
                .filter(e -> !(e instanceof Conjunction && !finalConjunctionSubexps.contains(e)))
                .toList();

        if (exps.size() < 2) {
            return new Disjunction(exps.get(0));
        }

        // (6) Distributive laws
        List<Exp> tmpExps = new ArrayList<>();

        for (int cursor = 0; cursor < exps.size() - 1; cursor++) {
            if (exps.get(cursor + 1) instanceof Conjunction) {
                Exp cur = exps.get(cursor);

                tmpExps.add(new Conjunction(
                        ((Conjunction) exps.get(cursor + 1)).getSubexps().stream()
                                .map(e -> new Disjunction(cur, e))
                                .toList().toArray(new Exp[0])
                ));

                cursor++;
            } else {
                tmpExps.add(exps.get(cursor));
                if (cursor == exps.size() - 2) {
                    tmpExps.add(exps.get(cursor + 1));
                }
            }
        }

        if (tmpExps.size() == 1) {
            return tmpExps.get(0);
        }

        return new Disjunction(tmpExps.toArray(new Exp[0]));
    }

    @Override
    public String toString() {
        return "(" + subexps.stream().map(i -> i.toString()).collect(Collectors.joining(" || ")) + ")";
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

        Disjunction that = (Disjunction) obj;

        return this.subexps.containsAll(that.subexps) && that.subexps.containsAll(this.subexps);
    }

    @Override
    public int hashCode() {
        int result = 0;
        int i = 0;
        for (Exp e : subexps) {
            result += e.hashCode() * Math.pow(31, ++i);
        }
        return result;
    }
}