package edu.postech.csed332.homework2;

import java.util.List;
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
        return subexp.vars();
    }

    @Override
    public Boolean evaluate(Map<Integer, Boolean> assignment) {
        return !subexp.evaluate(assignment);
    }

    @Override
    public Exp simplify() {
        // (0) Simplify subexps
        Exp exp = subexp.simplify();

        if (exp instanceof Conjunction) {
            // (3) De Morgan’s laws
            exp = new Disjunction(
                    ((Conjunction) exp).getSubexps().stream()
                            .map(Negation::new)
                            .toList().toArray(new Exp[0])
            );
        } else if (exp instanceof Disjunction) {
            // (3) De Morgan’s laws
            exp = new Conjunction(
                    ((Disjunction) exp).getSubexps().stream()
                            .map(Negation::new)
                            .toList().toArray(new Exp[0])
            );
        } else if (exp instanceof Negation) {
            // (5) Double negation law
            exp = new Conjunction(((Negation) exp).subexp);
        } else {
            exp = new Negation(exp);
        }

        return exp;
    }

    @Override
    public String toString() {
        return "(! " + subexp + ")";
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

        Negation that = (Negation) obj;

        return this.subexp.equals(that.subexp);
    }

    @Override
    public int hashCode() {
        return subexp.hashCode() * 101;
    }
}
