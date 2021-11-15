package edu.postech.csed332.homework5;


import edu.postech.csed332.homework5.expression.Exp;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class ExpTest {

    @Test
    void testToString() {
        Exp exp = Exp.parseExp("1.0 + 2.0 * x1");
        String expStr = exp.toString();
        assertEquals(expStr, Exp.parseExp(expStr).toString());
    }

    @Test
    void testEvaluation() {
        Exp exp = Exp.parseExp("x1 ^ x2 + 2.0 * x1");
        assertEquals(87.0, exp.eval(getValuation(Arrays.asList(3.0, 4.0))));
    }

    @Test
    void testToStringAndEvaluation() {
        Exp exp = Exp.parseExp("x1 ^ x2 + 2.0 * x1");
        assertEquals(87.0, Exp.parseExp(exp.toString()).eval(getValuation(Arrays.asList(3.0, 4.0))));
    }

    @Test
    void testEquivalence() {
        Exp e1 = Exp.parseExp("1.0 + 2.0 * x1 + x1");
        Exp e2 = Exp.parseExp("1.0 + 2.0 * x1 + x1");
        assertTrue(e1.equiv(e2));
    }

    @Test
    void testEquivalenceWrong() {
        Exp e1 = Exp.parseExp("1.0 + 2.0 * x1 + x1");
        Exp e2 = Exp.parseExp("1.0 + 2.0 * x2 + x3");
        assertFalse(e1.equiv(e2));
    }

    @Test
    void testPrettyPrintDecoration() {
        Exp exp = Exp.parseExp("1234567890123456");
        Exp pExp = new PrettyPrintExpDecorator(exp);
        assertEquals(exp.toString(), "1.234567890123456E15");
        assertEquals(pExp.toString(), "1234567890123456");
    }

    @Test
    void testDefaultValueDecoration() {
        Exp exp = Exp.parseExp("2.0 * x1 + x2");
        Exp dExp = new DefaultValueExpDecorator(exp, 2.0);
        assertEquals(8.0, dExp.eval(getValuation(Collections.singletonList(3.0))));
    }

    @Test
    void testRenamingEquivDecoration() {
        Exp e1 = Exp.parseExp("(x1 + x2) * x3 + 1.0 * x1");
        Exp e2 = Exp.parseExp("(x3 + x1) * x2 + 1.0 * x3");
        Exp de1 = new RenamingEquivDecorator(e1);
        assertTrue(de1.equiv(e2));
    }

    @Test
    void testPrettyPrintAndDefaultValueDecoration() {
        Exp exp = Exp.parseExp("2.0 * x1 + x2");
        Exp dpExp = new DefaultValueExpDecorator(new PrettyPrintExpDecorator(exp), 2.0);
        Exp pdExp = new PrettyPrintExpDecorator(new DefaultValueExpDecorator(exp, 2.0));
        assertEquals(8.0, dpExp.eval(getValuation(Collections.singletonList(3.0))));
        assertEquals(8.0, pdExp.eval(getValuation(Collections.singletonList(3.0))));
    }


    @Test
    void testDefaultValueAndRenamingEquivDecoration() {
        Exp e1 = Exp.parseExp("2.0 * x1 + x2");
        Exp e2 = Exp.parseExp("2.0 * x2 + x1");
        Exp drExp = new DefaultValueExpDecorator(new RenamingEquivDecorator(e1), 2.0);
        Exp rdExp = new RenamingEquivDecorator(new DefaultValueExpDecorator(e1, 2.0));
        assertTrue(drExp.equiv(e2));
        assertTrue(rdExp.equiv(e2));
    }

    /**
     * Create a valuation map from the given list
     *
     * @param values a list
     * @return a valuation map
     */
    @NotNull
    static Map<Integer, Double> getValuation(@NotNull List<Double> values) {
        return Collections.unmodifiableMap(
                IntStream.range(0, values.size()).mapToObj(i -> new AbstractMap.SimpleEntry<>(i + 1, values.get(i)))
                        .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue)));
    }
}
