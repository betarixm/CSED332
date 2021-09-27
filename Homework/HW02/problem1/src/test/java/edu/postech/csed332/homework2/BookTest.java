package edu.postech.csed332.homework2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class BookTest {

    @Test
    public void testGetTitle() {
        Book book = new Book("Unit Testing", Arrays.asList("Name 1", "Name 2"));
        Assertions.assertEquals(book.getTitle(), "Unit Testing");
    }

    @Test
    public void testConstructFromJson() {
        String json = "{\"title\":\"title1\",\"authors\":[\"author1\",\"author2\"]}";
        Book book = new Book(json);

        Assertions.assertEquals(book.getTitle(), "title1");
        Assertions.assertEquals(book.getAuthors(), List.of("author1", "author2"));
    }

    @Test
    void testGetStringRepresentation() {
        String json = "{\"title\":\"title1\",\"authors\":[\"author1\",\"author2\"]}";
        Book book = new Book(json);

        Assertions.assertEquals(book.getStringRepresentation(), json);
    }

    @Test
    void testGetContainingCollections() {
        Collection collection = new Collection("collection");
        Collection subcollection = new Collection("subcollection");

        Book book = new Book("title", List.of("author"));

        book.setParentCollection(subcollection);
        subcollection.setParentCollection(collection);

        Assertions.assertEquals(book.getContainingCollections(), List.of(subcollection, collection));
    }
}
