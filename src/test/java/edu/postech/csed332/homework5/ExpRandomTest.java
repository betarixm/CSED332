package edu.postech.csed332.homework5;

import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.pholser.junit.quickcheck.From;
import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.generator.InRange;
import com.pholser.junit.quickcheck.generator.Size;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import edu.postech.csed332.homework5.expression.Exp;
import edu.postech.csed332.homework5.expression.ExpGenerator;
import edu.postech.csed332.homework5.expression.Variable;
import org.jetbrains.annotations.NotNull;
import org.junit.runner.RunWith;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(JUnitQuickcheck.class)
public class ExpRandomTest {

    /**
     * Check whether a string representation of an expression is parsed into an
     * equivalent expression.
     *
     * @param exp a randomly generated expression
     */
    @Property(trials = 200)
    public void testToString(@NotNull @From(ExpGenerator.class) Exp exp) {
        Exp g = Exp.parseExp(exp.toString());
        assertEquals(exp.toString(), g.toString());
    }

    /**
     * Check whether an expression is correctly evaluated (the Javaluator library is
     * used as a test oracle).
     *
     * @param exp    a randomly generated exp (up to 10 variables)
     * @param values a randomly generate double list of size 10
     */
    @Property(trials = 200)
    public void testEvaluation(@NotNull @From(ExpGenerator.class) @Variable(number = 10) Exp exp,
                               @NotNull @Size(min = 10, max = 10) List<Double> values) {
        Map<Integer, Double> valuation = ExpTest.getValuation(values);
        String substExp = applyValuation(valuation, exp.toString());
        try {
            assertEquals(exp.eval(valuation), (new DoubleEvaluator()).evaluate(substExp));
        } catch (IllegalArgumentException e) {
            // Ignore Javaluator exception
        }
    }

    /**
     * Check if PrettyPrintExpDecorator is correctly implemented. It should now
     * contain exponents in the string representation, should not change the
     * evaluation result, and should be parsed as an equivalent expression.
     *
     * @param exp    a randomly generated exp (up to 10 variables)
     * @param values a randomly generate double list of size 10
     */
    @Property(trials = 200)
    public void testPrettyPrintDecoration(@NotNull @From(ExpGenerator.class) @Variable(number = 10) Exp exp,
                                          @NotNull @Size(min = 10, max = 10) List<Double> values) {
        Exp pExp = new PrettyPrintExpDecorator(exp);
        Map<Integer, Double> valuation = ExpTest.getValuation(values);
        assertFalse(pExp.toString().contains("E"));
        assertEquals(exp.eval(valuation), pExp.eval(valuation));
        assertEquals(exp.eval(valuation), Exp.parseExp(pExp.toString()).eval(valuation));
    }

    /**
     * Check if DefaultValueExpDecorator is correctly implemented. It should not
     * alter any string representation, and should be correctly composed with
     * PrettyPrintExpDecorator. The evaluation result should be correct.
     *
     * @param exp          a randomly generated expression
     * @param defaultValue a randomly generated default value for variables (>= 1.0)
     */
    @Property(trials = 200)
    public void testDefaultValueDecoration(@NotNull @From(ExpGenerator.class) Exp exp,
                                           @NotNull @InRange(min = "1.0") Double defaultValue) {
        Exp dExp = new DefaultValueExpDecorator(exp, defaultValue);
        Exp dpExp = new DefaultValueExpDecorator(new PrettyPrintExpDecorator(exp), defaultValue);
        Exp pdExp = new PrettyPrintExpDecorator(new DefaultValueExpDecorator(exp, defaultValue));
        Map<Integer, Double> noValuation = ExpTest.getValuation(Collections.emptyList());

        String substExp = exp.toString().replaceAll("x\\d+", valueToString(defaultValue));
        try {
            Double oracle = (new DoubleEvaluator()).evaluate(substExp);

            assertEquals(exp.toString(), dExp.toString());
            assertEquals(dpExp.toString(), pdExp.toString());
            assertEquals(dExp.eval(noValuation), oracle);
            assertEquals(dpExp.eval(noValuation), oracle);
            assertEquals(pdExp.eval(noValuation), oracle);
        } catch (IllegalArgumentException e) {
            // Ignore Javaluator exception
        }
    }

    @NotNull
    static private String valueToString(Double value) {
        return "(" + BigDecimal.valueOf(value).toString() + ")";
    }

    static private String applyValuation(@NotNull Map<Integer, Double> valuation, String substExp) {
        for (Entry<Integer, Double> e : valuation.entrySet())
            substExp = substExp.replaceAll("x" + e.getKey(), valueToString(e.getValue()));
        return substExp;
    }
}
