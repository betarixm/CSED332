package edu.postech.csed332.homework2;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ExpTest {

    @Test
    void testParserOK() {
        Exp exp = ExpParser.parse("p1 || p2 && ! p3 || true");
        assertEquals(exp.toString(), "((p1 || (p2 && (! p3))) || true)");
    }

    @Test
    void testParserError() {
        assertThrows(IllegalStateException.class, () -> {
            Exp exp = ExpParser.parse("p1 || p2 && ! p0 || true");
        });
    }

    @Test
    void testConjunctionSimpleEvaluateExps() {
        assertEquals(ExpParser.parse("true && true").evaluate(Map.of()), true);
        assertEquals(ExpParser.parse("true && false").evaluate(Map.of()), false);
        assertEquals(ExpParser.parse("false && false").evaluate(Map.of()), false);
    }

    @Test
    void testConstantSimpleEvaluateExps() {
        assertEquals(ExpParser.parse("true").evaluate(Map.of()), true);
        assertEquals(ExpParser.parse("false").evaluate(Map.of()), false);
    }

    @Test
    void testDisjunctionSimpleEvaluateExps() {
        assertEquals(ExpParser.parse("true || true").evaluate(Map.of()), true);
        assertEquals(ExpParser.parse("true || false").evaluate(Map.of()), true);
        assertEquals(ExpParser.parse("false || false").evaluate(Map.of()), false);
    }

    @Test
    void testNegationSimpleEvaluateExps() {
        assertEquals(ExpParser.parse("! true").evaluate(Map.of()), false);
        assertEquals(ExpParser.parse("! false").evaluate(Map.of()), true);
    }

    @Test
    void testVariableSimpleEvaluateExps() {
        assertEquals(ExpParser.parse("p1").evaluate(Map.of(1, true)), true);
        assertEquals(ExpParser.parse("p1").evaluate(Map.of(1, false)), false);

        assertEquals(ExpParser.parse("p1 && p2").evaluate(Map.of(1, true, 2, true)), true);
        assertEquals(ExpParser.parse("p1 && p2").evaluate(Map.of(1, true, 2, false)), false);
        assertEquals(ExpParser.parse("p1 && p2").evaluate(Map.of(1, false, 2, false)), false);

        assertEquals(ExpParser.parse("p1 || p2").evaluate(Map.of(1, true, 2, true)), true);
        assertEquals(ExpParser.parse("p1 || p2").evaluate(Map.of(1, true, 2, false)), true);
        assertEquals(ExpParser.parse("p1 || p2").evaluate(Map.of(1, false, 2, false)), false);
    }

    @Test
    void testIdentityLaw() {
        assertEquals(ExpParser.parse("p1 && true").simplify().toString(), "(p1)");
        assertEquals(ExpParser.parse("p1 || false").simplify().toString(), "(p1)");
    }

    @Test
    void testIdempotentLaw() {
        assertEquals(ExpParser.parse("p1 && p1").simplify().toString(), "(p1)");
        assertEquals(ExpParser.parse("p1 || p1").simplify().toString(), "(p1)");
    }

    @Test
    void testDominationLaw() {
        assertEquals(ExpParser.parse("p1 && false").simplify().evaluate(Map.of()), false);
        assertEquals(ExpParser.parse("p1 || true").simplify().evaluate(Map.of()), true);
    }

    @Test
    void testNegationLaw() {
        assertEquals(ExpParser.parse("p1 && ! p1").simplify().evaluate(Map.of()), false);
        assertEquals(ExpParser.parse("p1 || ! p1").simplify().evaluate(Map.of()), true);
    }

    @Test
    void testAbsorptionLaw() {
        assertEquals(ExpParser.parse("p1 || ( p1 && p2 )").simplify().toString(), "(p1)");
        assertEquals(ExpParser.parse("p1 && ( p1 || p2 )").simplify().toString(), "(p1)");
    }

    @Test
    void testDeMorgansLaws() {
        assertEquals(ExpParser.parse("!(p1 && p2)").simplify().toString(), "((! p1) || (! p2))");
        assertEquals(ExpParser.parse("!(p1 || p2)").simplify().toString(), "((! p1) && (! p2))");
    }

    @Test
    void testDoubleNegationLaw() {
        assertEquals(ExpParser.parse("! ( ! p1 )").simplify().toString(), "(p1)");
    }

    @Test
    void testDistributiveLaws() {
        assertEquals(ExpParser.parse("p1 || ( p2 && p3 )").simplify().toString(), "((p1 || p2) && (p1 || p3))");
        assertEquals(ExpParser.parse("p1 && ( p2 || p3 )").simplify().toString(), "((p1 && p2) || (p1 && p3))");
    }

    @Test
    void testConjunctionEquals() {
        Conjunction n1 = new Conjunction(new Constant(true));
        Conjunction n2 = new Conjunction(new Constant(false));

        assertEquals(n1, n1);
        assertNotEquals(n1, n2);
    }

    @Test
    void testDisjunctionEquals() {
        Disjunction n1 = new Disjunction(new Constant(true));
        Disjunction n2 = new Disjunction(new Constant(false));

        assertEquals(n1, n1);
        assertNotEquals(n1, n2);
    }

    @Test
    void testNegationEquals() {
        Negation n1 = new Negation(new Constant(true));
        Negation n2 = new Negation(new Constant(false));

        assertEquals(n1, n1);
        assertNotEquals(n1, n2);
    }

    @Test
    void testVariableEquals() {
        Variable n1 = new Variable(1);
        Variable n2 = new Variable(2);

        assertEquals(n1, n1);
        assertNotEquals(n1, n2);
    }

    @Test
    void testConstantEquals() {
        Constant n1 = new Constant(true);
        Constant n2 = new Constant(false);

        assertEquals(n1, n1);
        assertNotEquals(n1, n2);
        assertNotEquals(n1, null);
        assertNotEquals(n1, new Variable(1));
    }

    @Test
    void testVars() {
        Exp exp = ExpParser.parse("p1 || p2 && ! p3 || true");
        assertEquals(exp.vars(), Set.of(1, 2, 3));
    }

    @Test
    void testIllegalVariable() {
        assertThrows(IllegalArgumentException.class, () -> new Variable(-1));
    }

    @Test
    void testGetIdentifier() {
        assertEquals((new Variable(1)).getIdentifier(), 1);
    }
}
