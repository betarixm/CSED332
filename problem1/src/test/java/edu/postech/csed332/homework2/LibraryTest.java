package edu.postech.csed332.homework2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LibraryTest {

    @Test
    public void testfindBooksNull() {
        Library lib = new Library();
        Assertions.assertNull(lib.findBooks("any"));
    }

    /*
     * TODO: add  test methods to achieve at least 80% statement coverage of Library.
     * Each test method should have appropriate JUnit assertions to test a single behavior
     * of the class. You should not add arbitrary code to test methods to just increase coverage.
     */
}
