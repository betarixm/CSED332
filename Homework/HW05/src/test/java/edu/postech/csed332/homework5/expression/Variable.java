package edu.postech.csed332.homework5.expression;

import com.pholser.junit.quickcheck.generator.GeneratorConfiguration;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Using a variable parameter, the total number of variables in generated
 * expressions can be bounded up to the given number.
 *
 * @author kmbae
 */
@Target({PARAMETER, FIELD, ANNOTATION_TYPE, TYPE_USE})
@Retention(RUNTIME)
@GeneratorConfiguration
public @interface Variable {
    int number() default Integer.MAX_VALUE;
}
