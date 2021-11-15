package edu.postech.csed332.homework5;

import edu.postech.csed332.homework5.expression.*;

/**
 * A visitor to compute the string expression of a given expression
 */
public class ToStringVisitor implements ExpVisitor<String> {
    public String visit(VariableExp exp) {
        return "x" + exp.getName();
    }

    public String visit(NumberExp exp) {
        String result = exp.getValue().toString();
        if (exp.getValue() < 0) {
            result = "(" + result + ")";
        }
        return result;
    }

    public String visit(PlusExp exp) {
        return binaryStringBuilder(exp, "+");
    }

    public String visit(MinusExp exp) {
        return binaryStringBuilder(exp, "-");
    }

    public String visit(MultiplyExp exp) {
        return binaryStringBuilder(exp, "*");
    }

    public String visit(DivideExp exp) {
        return binaryStringBuilder(exp, "/");
    }

    public String visit(ExponentiationExp exp) {
        return binaryStringBuilder(exp, "^");
    }

    private String binaryStringBuilder(BinaryExp exp, String token) {
        return "(" + exp.getLeft().accept(this) + " " + token + " " + exp.getRight().accept(this) + ")";
    }
}
