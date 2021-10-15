package edu.postech.csed332.homework5;

import edu.postech.csed332.homework5.expression.Exp;
import org.jetbrains.annotations.NotNull;

/**
 * The string representation is given without exponents of double values. For example,
 * 12345678, not 1.2345678E7. (Hint: use java.math.BigDecimal)
 */
public class PrettyPrintExpDecorator extends ExpDecorator {

    public PrettyPrintExpDecorator(Exp e) {
        super(e);
    }

    @NotNull
    @Override
    public String toString() {
        // TODO implement this
        return null;
    }

}
