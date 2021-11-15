package edu.postech.csed332.homework5;

import edu.postech.csed332.homework5.expression.*;

import java.util.Map;
import java.util.function.BinaryOperator;

/**
 * A visitor to evaluate a value of an expression, given a valuation for each variable
 */
public class EvaluationVisitor implements ExpVisitor<Double> {
    private final Map<Integer, Double> valuation;

    public EvaluationVisitor(Map<Integer, Double> valuation) {
        this.valuation = valuation;
    }

    @Override
    public Double visit(VariableExp exp) {
        return valuation.get(exp.getName());
    }

    @Override
    public Double visit(NumberExp exp) {
        return exp.getValue();
    }

    @Override
    public Double visit(PlusExp exp) {
        return binaryExpEvaluator(exp, Double::sum);
    }

    @Override
    public Double visit(MinusExp exp) {
        return binaryExpEvaluator(exp, (x, y) -> x - y);
    }

    @Override
    public Double visit(MultiplyExp exp) {
        return binaryExpEvaluator(exp, (x, y) -> x * y);
    }

    @Override
    public Double visit(DivideExp exp) {
        return binaryExpEvaluator(exp, (x, y) -> x / y);
    }

    @Override
    public Double visit(ExponentiationExp exp) {
        return binaryExpEvaluator(exp, Math::pow);
    }

    private Double binaryExpEvaluator(BinaryExp exp, BinaryOperator<Double> f) {
        return f.apply(exp.getLeft().accept(this), exp.getRight().accept(this));
    }
}
