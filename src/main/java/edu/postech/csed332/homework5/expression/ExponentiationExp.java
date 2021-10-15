package edu.postech.csed332.homework5.expression;

import edu.postech.csed332.homework5.ExpVisitor;
import org.jetbrains.annotations.NotNull;

/**
 * An expression to represent: exp ^ exp
 */
public class ExponentiationExp extends BinaryExp {

    public ExponentiationExp(@NotNull Exp base, @NotNull Exp exponent) {
        super(base,exponent);
    }

    @Override
    @NotNull
    public <T> T accept(@NotNull ExpVisitor<T> visitor) {
        // TODO implement this
        return null;
    }
}
