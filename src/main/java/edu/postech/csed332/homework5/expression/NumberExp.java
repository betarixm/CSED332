package edu.postech.csed332.homework5.expression;

import edu.postech.csed332.homework5.ExpVisitor;
import org.jetbrains.annotations.NotNull;

/**
 * A number
 */
public class NumberExp extends Exp {
    @NotNull
    private final Double value;

    public NumberExp(@NotNull Double v) {
        this.value = v;
    }

    /**
     * @return value
     */
    @NotNull
    public Double getValue() {
        return value;
    }

    @Override
    @NotNull
    public <T> T accept(@NotNull ExpVisitor<T> visitor) {
        // TODO implement this
        return null;
    }
}
