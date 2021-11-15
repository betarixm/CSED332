package edu.postech.csed332.homework5.expression;

import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;
import org.jetbrains.annotations.NotNull;

/**
 * A Expression generator JUnit quick check. It randomly generates expression
 * instances.
 *
 * @author kmbae
 */
public class ExpGenerator extends Generator<Exp> {

    private Variable variableParam;

    public ExpGenerator() {
        super(Exp.class);
    }

    @NotNull
    @Override
    public Exp generate(@NotNull SourceOfRandomness random, @NotNull GenerationStatus status) {
        int size = status.size();
        int varSize = variableParam != null ? Integer.min(size, variableParam.number()) : size;
        return generate(random, size, varSize > 0 ? random.nextInt(varSize) : 0, false);
    }

    public void configure(Variable c) {
        this.variableParam = c;
    }

    /**
     * Randomly create a Expression instance of given size.
     *
     * @param random     a random number generator
     * @param size       the number of operators in expression
     * @param vars       the number of "possible" Expression variables
     * @param isExponent true if generating an exponent
     * @return a randomly generated expression
     */
    @NotNull
    private Exp generate(@NotNull SourceOfRandomness random, int size, int vars, boolean isExponent) {
        if (size > 0) {
            int choice = random.nextInt(isExponent ? 4 : 5); // does not generate a tower of exponentiation
            int formulaSize = choice > 0 ? size - 1 : random.nextInt(size);

            switch (choice) {
                case 0: // plus
                    return new PlusExp(generate(random, formulaSize, vars, isExponent),
                            generate(random, size - formulaSize - 1, vars, isExponent));
                case 1: // minus
                    return new MinusExp(generate(random, formulaSize, vars, isExponent),
                            generate(random, size - formulaSize - 1, vars, isExponent));
                case 2: // multiply
                    return new MultiplyExp(generate(random, formulaSize, vars, isExponent),
                            generate(random, size - formulaSize - 1, vars, isExponent));
                case 3: // divide
                    return new DivideExp(generate(random, formulaSize, vars, isExponent),
                            generate(random, size - formulaSize - 1, vars, isExponent));
                default: // exponentiation
                    return new ExponentiationExp(generate(random, formulaSize, vars, isExponent),
                            generate(random, size - formulaSize - 1, vars, true));
            }
        } else {
            if (vars > 0 && random.nextBoolean()) {
                return new VariableExp(random.nextInt(vars) + 1);
            } else {
                return isExponent ? new NumberExp(random.nextDouble(1, 10))
                        : new NumberExp(random.nextDouble(-100, 100));
            }
        }
    }
}
