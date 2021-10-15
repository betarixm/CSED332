package edu.postech.csed332.homework5;

import edu.postech.csed332.homework5.expression.Exp;
import edu.postech.csed332.homework5.expression.NumberExp;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

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
        ExpVisitor<String> visitor = new ToStringVisitor() {
            @Override
            public String visit(NumberExp exp) {
                return new BigDecimal(exp.getValue().toString()).toPlainString();
            }
        };

        return this.accept(visitor);
    }

}
