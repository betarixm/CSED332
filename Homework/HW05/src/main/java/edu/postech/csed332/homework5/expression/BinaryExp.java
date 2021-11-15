package edu.postech.csed332.homework5.expression;

import org.jetbrains.annotations.NotNull;

/**
 * An abstract class to represent expressions with a binary operator
 */
public abstract class BinaryExp extends Exp {
    private final Exp left;
    private final Exp right;

    BinaryExp(@NotNull Exp left, @NotNull Exp right) {
        this.left = left;
        this.right = right;
    }

    /**
     * @return e1 of the expression (e1 op e2) for a binary operator op
     */
    @NotNull
    public Exp getLeft() {
        return left;
    }

    /**
     * @return e2 of the expression (e1 op e2) for a binary operator op
     */
    @NotNull
    public Exp getRight() {
        return right;
    }
}
