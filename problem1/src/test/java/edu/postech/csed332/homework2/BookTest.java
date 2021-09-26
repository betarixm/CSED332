package edu.postech.csed332.homework2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class BookTest {

    @Test
    public void testGetTitle() {
        Book book = new Book("Unit Testing", Arrays.asList("Name 1", "Name 2"));
        Assertions.assertEquals(book.getTitle(), "Unit Testing");
    }

    /*
     * TODO: add  test methods to achieve at least 80% statement coverage of Book.
     * Each test method should have appropriate JUnit assertions to test a single behavior
     * of the class. You should not add arbitrary code to test methods to just increase coverage.
     */
}
