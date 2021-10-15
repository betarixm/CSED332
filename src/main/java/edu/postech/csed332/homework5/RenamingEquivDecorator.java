package edu.postech.csed332.homework5;

import edu.postech.csed332.homework5.expression.Exp;
import org.jetbrains.annotations.NotNull;

/**
 * This expression is equivalent to another expression that is syntactically identical up to renaming.
 * For example, "(x1 + x2) * x3 + 1.0 * x1" is equivalent to "(x3 + x1) * x2 + 1.0 * x3", but not
 * equivalent to "(x3 + x1) * x2 + 1.0 * x1".
 */
public class RenamingEquivDecorator extends ExpDecorator {

    public RenamingEquivDecorator(Exp e) {
        super(e);
    }

    @Override
    public boolean equiv(@NotNull Exp other) {
        // TODO implement this
        return false;
    }
}
