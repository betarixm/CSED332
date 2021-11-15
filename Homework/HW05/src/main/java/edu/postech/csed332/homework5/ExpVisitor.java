package edu.postech.csed332.homework5;

import edu.postech.csed332.homework5.expression.*;

/**
 * A visitor interface for expressions
 *
 * @param <T> type of return values
 */
public interface ExpVisitor<T> {
    T visit(VariableExp exp);

    T visit(NumberExp exp);

    T visit(PlusExp exp);

    T visit(MinusExp exp);

    T visit(MultiplyExp exp);

    T visit(DivideExp exp);

    T visit(ExponentiationExp exp);
}
