package edu.postech.csed332.homework5;

import edu.postech.csed332.homework5.expression.*;
import org.jetbrains.annotations.NotNull;

/**
 * A visitor to check whether a given expression is syntactically equivalent to another expression.
 */
public class EquivalenceVisitor implements ExpVisitor<Boolean> {

    protected Exp other;

    public EquivalenceVisitor(@NotNull Exp other) {
        this.other = other;
    }

    @Override
    public Boolean visit(VariableExp exp) {
        return other instanceof VariableExp && exp.getName() == ((VariableExp) other).getName();
    }

    @Override
    public Boolean visit(NumberExp exp) {
        return other instanceof NumberExp && exp.getValue().equals(((NumberExp) other).getValue());
    }

    @Override
    public Boolean visit(PlusExp exp) {
        return other instanceof PlusExp && binaryTermsSame(exp);
    }

    @Override
    public Boolean visit(MinusExp exp) {
        return other instanceof MinusExp && binaryTermsSame(exp);
    }

    @Override
    public Boolean visit(MultiplyExp exp) {
        return other instanceof MultiplyExp && binaryTermsSame(exp);
    }

    @Override
    public Boolean visit(DivideExp exp) {
        return other instanceof DivideExp && binaryTermsSame(exp);
    }

    @Override
    public Boolean visit(ExponentiationExp exp) {
        return other instanceof ExponentiationExp && binaryTermsSame(exp);
    }

    public Boolean binaryTermsSame(BinaryExp exp) {
        BinaryExp binaryOther = (BinaryExp) other;
        return exp.getLeft().accept(new EquivalenceVisitor(binaryOther.getLeft())) && exp.getRight().accept(new EquivalenceVisitor(binaryOther.getRight()));
    }
}
