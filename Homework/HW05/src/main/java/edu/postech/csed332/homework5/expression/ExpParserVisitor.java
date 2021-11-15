package edu.postech.csed332.homework5.expression;

import edu.postech.csed332.homework5.parser.SimpleExpressionBaseVisitor;
import edu.postech.csed332.homework5.parser.SimpleExpressionLexer;
import edu.postech.csed332.homework5.parser.SimpleExpressionParser;
import org.jetbrains.annotations.NotNull;

/**
 * A visitor to generate an Exp object from the parsing result.
 */
class ExpParserVisitor extends SimpleExpressionBaseVisitor<Exp> {

    /**
     * Constructs an Expression object if parenthesis used.
     */
    @Override
    @NotNull
    public Exp visitParenExpression(@NotNull SimpleExpressionParser.ParenExpressionContext ctx) {
        return ctx.sub.accept(this);
    }

    /**
     * Constructs an Expression object for binary operators.
     */
    @Override
    @NotNull
    public Exp visitBinaryExpression(@NotNull SimpleExpressionParser.BinaryExpressionContext ctx) {
        switch (ctx.op.getType()) {
            case SimpleExpressionLexer.PLUS:
                return new PlusExp(ctx.left.accept(this), ctx.right.accept(this));
            case SimpleExpressionLexer.MINUS:
                return new MinusExp(ctx.left.accept(this), ctx.right.accept(this));
            case SimpleExpressionLexer.MULT:
                return new MultiplyExp(ctx.left.accept(this), ctx.right.accept(this));
            case SimpleExpressionLexer.DIVIDE:
                return new DivideExp(ctx.left.accept(this), ctx.right.accept(this));
            case SimpleExpressionLexer.EXPON:
                return new ExponentiationExp(ctx.left.accept(this), ctx.right.accept(this));
            default:
                throw new UnsupportedOperationException();
        }
    }

    /**
     * Constructs an Expression object for numbers.
     */
    @Override
    @NotNull
    public Exp visitNumber(@NotNull SimpleExpressionParser.NumberContext ctx) {
        return new NumberExp(Double.parseDouble(ctx.value.getText()));
    }

    /**
     * Constructs an Expression object for variables.
     */
    @Override
    @NotNull
    public Exp visitVariable(@NotNull SimpleExpressionParser.VariableContext ctx) {
        return new VariableExp(Integer.parseUnsignedInt(ctx.name.getText().substring(1)));
    }
}
