package edu.postech.csed332.homework5.expression;

import edu.postech.csed332.homework5.ExpVisitor;
import org.jetbrains.annotations.NotNull;

/**
 * An expression to represent: exp + exp
 */
public class PlusExp extends BinaryExp {

    public PlusExp(@NotNull Exp left, @NotNull Exp right) {
        super(left, right);
    }

    @Override
    @NotNull
    public <T> T accept(@NotNull ExpVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
