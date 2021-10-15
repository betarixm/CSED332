package edu.postech.csed332.homework5.expression;

import edu.postech.csed332.homework5.ExpVisitor;
import org.jetbrains.annotations.NotNull;

/**
 * An expression to represent: exp * exp
 */
public class MultiplyExp extends BinaryExp {

    public MultiplyExp(@NotNull Exp left, @NotNull Exp right) {
        super(left, right);
    }

    @Override
    @NotNull
    public <T> T accept(@NotNull ExpVisitor<T> visitor) {
        // TODO implement this
        return null;
    }

}
