package edu.postech.csed332.homework5;

import edu.postech.csed332.homework5.expression.BinaryExp;
import edu.postech.csed332.homework5.expression.Exp;
import edu.postech.csed332.homework5.expression.VariableExp;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * This expression is equivalent to another expression that is syntactically identical up to renaming.
 * For example, "(x1 + x2) * x3 + 1.0 * x1" is equivalent to "(x3 + x1) * x2 + 1.0 * x3", but not
 * equivalent to "(x3 + x1) * x2 + 1.0 * x1".
 */
public class RenamingEquivDecorator extends ExpDecorator {
    private final Map<Integer, Integer> renameOtherToThisMap = new HashMap<>();

    public RenamingEquivDecorator(Exp e) {
        super(e);
    }

    private class RenamingEquivalenceVisitor extends EquivalenceVisitor {

        public RenamingEquivalenceVisitor(@NotNull Exp other) {
            super(other);
        }

        @Override
        public Boolean visit(VariableExp exp) {
            if (other instanceof VariableExp) {
                int otherName = ((VariableExp) other).getName();
                int thisName = exp.getName();

                if (otherName == thisName || Collections.frequency(renameOtherToThisMap.values(), thisName) < 2) {
                    return true;
                }
            }

            return false;
        }

        @Override
        public Boolean binaryTermsSame(BinaryExp exp) {
            BinaryExp binaryOther = (BinaryExp) other;
            return exp.getLeft().accept(new RenamingEquivalenceVisitor(binaryOther.getLeft())) && exp.getRight().accept(new RenamingEquivalenceVisitor(binaryOther.getRight()));
        }

    }
    @Override
    public boolean equiv(@NotNull Exp other) {
        EquivalenceVisitor visitor = new RenamingEquivalenceVisitor(other);
        renameOtherToThisMap.clear();

        return this.accept(visitor);
    }
}
