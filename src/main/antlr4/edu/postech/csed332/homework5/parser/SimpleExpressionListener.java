package edu.postech.csed332.homework5.parser;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link SimpleExpressionParser}.
 */
public interface SimpleExpressionListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by the {@code binaryExpression}
	 * labeled alternative in {@link SimpleExpressionParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterBinaryExpression(SimpleExpressionParser.BinaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code binaryExpression}
	 * labeled alternative in {@link SimpleExpressionParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitBinaryExpression(SimpleExpressionParser.BinaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code number}
	 * labeled alternative in {@link SimpleExpressionParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterNumber(SimpleExpressionParser.NumberContext ctx);
	/**
	 * Exit a parse tree produced by the {@code number}
	 * labeled alternative in {@link SimpleExpressionParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitNumber(SimpleExpressionParser.NumberContext ctx);
	/**
	 * Enter a parse tree produced by the {@code variable}
	 * labeled alternative in {@link SimpleExpressionParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterVariable(SimpleExpressionParser.VariableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code variable}
	 * labeled alternative in {@link SimpleExpressionParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitVariable(SimpleExpressionParser.VariableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parenExpression}
	 * labeled alternative in {@link SimpleExpressionParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterParenExpression(SimpleExpressionParser.ParenExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parenExpression}
	 * labeled alternative in {@link SimpleExpressionParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitParenExpression(SimpleExpressionParser.ParenExpressionContext ctx);
}