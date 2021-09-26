package edu.postech.csed332.homework2;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A Boolean expression whose top-level operator is && (and).
 */
public class Conjunction extends BinaryExp {
    /**
     * Builds a conjunction of a given list of Boolean expressions
     *
     * @param exps
     */
    public Conjunction(Exp... exps) {
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
        return subexps.stream().allMatch(e -> e.evaluate(assignment));
    }

    @Override
    public Exp simplify() {
        Constant FALSE = new Constant(false);

        // (0) Simplify subexps
        List<Exp> exps = subexps.stream().map(Exp::simplify).toList();

        // (1) Identity and idempotent laws
        exps = exps.stream().filter(e -> {
            return !((e instanceof Constant) && ((Constant) e).getValue());
        }).toList();

        exps = exps.stream().distinct().toList();

        // (2) Domination and negation laws
        if (exps.stream().anyMatch(e -> e.equals(FALSE))) {
            return FALSE;
        }

        List<Exp> negationSubexps = exps.stream()
                .filter(e -> e instanceof Negation)
                .map(e -> ((Negation) e).getSubexp())
                .toList();

        if (exps.stream().anyMatch(negationSubexps::contains)) {
            return FALSE;
        }

        // (4) Absorption laws
        List<Disjunction> disjunctionSubexps = exps.stream()
                .filter(e -> e instanceof Disjunction)
                .map(e -> (Disjunction) e)
                .toList();

        List<Exp> finalExps = exps;
        disjunctionSubexps = disjunctionSubexps.stream().filter(d -> {
            for (Exp _d : d.getSubexps()) {
                if (finalExps.contains(_d)) {
                    return false;
                }
            }
            return true;
        }).toList();


        List<Disjunction> finalDisjunctionSubexps = disjunctionSubexps;
        exps = exps.stream()
                .filter(e -> !(e instanceof Disjunction && !finalDisjunctionSubexps.contains(e)))
                .toList();

        if (exps.size() < 2) {
            return new Conjunction(exps.get(0));
        }

        // (6) Distributive laws
        List<Exp> tmpExps = new ArrayList<>();

        for (int cursor = 0; cursor < exps.size() - 1; cursor++) {
            if (exps.get(cursor + 1) instanceof Disjunction) {
                Exp cur = exps.get(cursor);

                tmpExps.add(new Disjunction(
                        ((Disjunction) exps.get(cursor + 1)).getSubexps().stream()
                                .map(e -> new Conjunction(cur, e))
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

        return new Conjunction(tmpExps.toArray(new Exp[0]));
    }

    @Override
    public String toString() {
        return "(" + subexps.stream().map(i -> i.toString()).collect(Collectors.joining(" && ")) + ")";
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

        Conjunction that = (Conjunction) obj;

        return this.subexps.containsAll(that.subexps) && that.subexps.containsAll(this.subexps);
    }

    @Override
    public int hashCode() {
        int result = 0;
        int i = 0;
        for (Exp e : subexps) {
            result += e.hashCode() * Math.pow(23, ++i);
        }
        return result;
    }

}