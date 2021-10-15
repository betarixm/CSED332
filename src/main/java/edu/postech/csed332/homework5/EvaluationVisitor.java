package edu.postech.csed332.homework5;

import java.util.Map;

/**
 * A visitor to evaluate a value of an expression, given a valuation for each variable
 */
public class EvaluationVisitor implements ExpVisitor<Double> {
    private final Map<Integer, Double> valuation;

    public EvaluationVisitor(Map<Integer, Double> valuation) {
        this.valuation = valuation;
    }

    // TODO write and implement the visitor methods for EvaluationVisitor, satisfying
    //  the specification of Exp.eval.
}
