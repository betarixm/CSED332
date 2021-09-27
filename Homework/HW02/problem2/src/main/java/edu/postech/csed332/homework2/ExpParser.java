package edu.postech.csed332.homework2;

import org.antlr.v4.runtime.*;

public class ExpParser {
    /**
     * Parse a string and create its Exp data structure. The syntax is as follows:
     * <p>
     * exp ::=  true  |  false  |  variable  | ! exp  |  ( exp )  |  exp && exp  |  exp || exp
     * <p>
     * where variable has the form pN with positive number N, e.g., p1, p2, p3, ...
     *
     * @param str string to be parsed
     * @return an Exp instance
     * @throws IllegalStateException if the string cannot be parsed as Exp
     */
    public static Exp parse(String str) throws IllegalStateException {

        BooleanExpLexer lex = new BooleanExpLexer(CharStreams.fromString(str));
        BooleanExpParser par = new BooleanExpParser(new CommonTokenStream(lex));

        par.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
                throw new IllegalStateException("Failed to parse at line " + line + " (" + msg + ")", e);
            }
        });

        return new BooleanExpBaseVisitor<Exp>() {
            @Override
            public Exp visitParenExp(BooleanExpParser.ParenExpContext ctx) {
                return visit(ctx.sub);
            }

            @Override
            public Exp visitUnaryExp(BooleanExpParser.UnaryExpContext ctx) {
                return new Negation(visit(ctx.sub));
            }

            @Override
            public Exp visitBinaryExp(BooleanExpParser.BinaryExpContext ctx) {
                switch (ctx.op.getType()) {
                    case BooleanExpLexer.AND:
                        return new Conjunction(visit(ctx.left), visit(ctx.right));
                    case BooleanExpLexer.OR:
                        return new Disjunction(visit(ctx.left), visit(ctx.right));
                    default:
                        throw new UnsupportedOperationException();
                }
            }

            @Override
            public Exp visitConstant(BooleanExpParser.ConstantContext ctx) {
                return new Constant(Boolean.parseBoolean(ctx.value.getText()));
            }

            @Override
            public Exp visitVariable(BooleanExpParser.VariableContext ctx) {
                return new Variable(Integer.parseUnsignedInt(ctx.name.getText().substring(1)));
            }
        }.visit(par.expression());
    }
}
