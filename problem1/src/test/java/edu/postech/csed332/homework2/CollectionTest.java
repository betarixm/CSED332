package edu.postech.csed332.homework2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CollectionTest {

    @Test
    public void testGetName() {
        Collection col = new Collection("Software");
        Assertions.assertEquals(col.getName(), "Software");
    }

    /*
     * TODO: add  test methods to achieve at least 80% statement coverage of Collection.
     * Each test method should have appropriate JUnit assertions to test a single behavior
     * of the class. You should not add arbitrary code to test methods to just increase coverage.
     */
}
