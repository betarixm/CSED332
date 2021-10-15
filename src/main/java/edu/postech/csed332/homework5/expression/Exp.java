package edu.postech.csed332.homework5.expression;

import edu.postech.csed332.homework5.EquivalenceVisitor;
import edu.postech.csed332.homework5.EvaluationVisitor;
import edu.postech.csed332.homework5.ExpVisitor;
import edu.postech.csed332.homework5.ToStringVisitor;
import edu.postech.csed332.homework5.parser.SimpleExpressionLexer;
import edu.postech.csed332.homework5.parser.SimpleExpressionParser;
import org.antlr.v4.runtime.*;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.NoSuchElementException;

/**
 * The Exp class represents arithmetic expressions with variables.
 */
public abstract class Exp {

    /**
     * Returns the string representation of this expression.
     *
     * @return the string representation of this expression.
     */
    public String toString() {
        return accept(new ToStringVisitor());
    }

    /**
     * Evaluates the value of expression, provided that the value of each variable is given in the valuation.
     *
     * @param valuation a valuation
     * @return the value of the expression under the valuation
     * @throws NoSuchElementException if there is a variable not in valuation
     */
    @NotNull
    public Double eval(@NotNull Map<Integer, Double> valuation) {
        return accept(new EvaluationVisitor(valuation));
    }

    /**
     * Checks if a given expression is syntactically the same as this expression.
     *
     * @param other an expression
     * @return true if other is syntactically the same as this expression
     */
    public boolean equiv(@NotNull Exp other) {
        return accept(new EquivalenceVisitor(other));
    }

    /**
     * Performs an operation given an implementation of ExpVisitor
     *
     * @param visitor a visitor
     * @return the result
     */
    @NotNull
    public abstract <T> T accept(ExpVisitor<T> visitor);

    /**
     * Parses a string and creates its expression data structure.
     *
     * @param str string to be parsed
     * @return Expression instance
     * @throws IllegalStateException if the string cannot be parsed as Expression
     */
    @NotNull
    public static Exp parseExp(@NotNull String str) {
        SimpleExpressionLexer lex = new SimpleExpressionLexer(CharStreams.fromString(str));
        SimpleExpressionParser par = new SimpleExpressionParser(new CommonTokenStream(lex));
        par.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line,
                                    int charPositionInLine, String msg, RecognitionException e) {
                throw new IllegalStateException("Failed to parse at line " + line + " (" + msg + ")", e);
            }
        });
        return new ExpParserVisitor().visit(par.exp());
    }
}
