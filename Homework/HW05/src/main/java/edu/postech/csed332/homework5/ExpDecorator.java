package edu.postech.csed332.homework5;

import edu.postech.csed332.homework5.expression.Exp;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * A base decorator class
 */
public class ExpDecorator extends Exp {
    private final Exp expression;

    ExpDecorator(Exp e) {
        expression = e;
    }

    @Override
    public String toString() {
        return expression.toString();
    }

    @Override
    @NotNull
    public Double eval(@NotNull Map<Integer, Double> valuation) {
        return expression.eval(valuation);
    }

    @Override
    public boolean equiv(@NotNull Exp other) {
        return expression.equiv(other);
    }

    @Override
    public <T> @NotNull T accept(ExpVisitor<T> visitor) {
        return expression.accept(visitor);
    }
}
