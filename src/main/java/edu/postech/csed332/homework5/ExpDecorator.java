package edu.postech.csed332.homework5;

import edu.postech.csed332.homework5.expression.Exp;

/**
 * A base decorator class
 */
public class ExpDecorator extends Exp {
    private final Exp expression;

    ExpDecorator(Exp e) {
        expression = e;
    }

    // TODO implement all the methods of ExpDecorator

}
