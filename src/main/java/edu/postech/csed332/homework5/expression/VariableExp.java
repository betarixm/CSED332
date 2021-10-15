package edu.postech.csed332.homework5.expression;

import edu.postech.csed332.homework5.ExpVisitor;
import org.jetbrains.annotations.NotNull;

/**
 * A variable, identified by positive integers
 */
public class VariableExp extends Exp {
    private final int name;

    public VariableExp(int n) {
        assert n > 0;
        this.name = n;
    }

    /**
     * @return a non-negative integer to represent ids.
     */
    public int getName() {
        return name;
    }

    @Override
    @NotNull
    public <T> T accept(@NotNull ExpVisitor<T> visitor) {
        // TODO implement this
        return null;
    }
}
