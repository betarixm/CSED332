package edu.postech.csed332.homework2;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CollectionTest {
    JSONObject testJson = new JSONObject("""
            {
               "name":"collection",
               "elements":[
                  {
                     "title":"title1",
                     "authors":[
                        "author1"
                     ]
                  },
                  {
                     "title":"title2",
                     "authors":[
                        "author2"
                     ]
                  },
                  {
                     "name":"subcollection",
                     "elements":[
                        {
                           "title":"title1",
                           "authors":[
                              "author1"
                           ]
                        }
                     ]
                  }
               ]
            }""");

    @Test
    public void testGetName() {
        Collection col = new Collection("Software");
        Assertions.assertEquals(col.getName(), "Software");
    }

    @Test
    public void testRestoreCollectionAndGetStringRepresentation() {
        Collection collection = Collection.restoreCollection(testJson.toString());
        Assertions.assertTrue(testJson.similar(new JSONObject(collection.getStringRepresentation())));
    }

    @Test
    public void testAddElement() {
        Collection collection = new Collection("collection");
        Book book = new Book("title", List.of("author1"));

        Assertions.assertTrue(collection.addElement(book));
        Assertions.assertEquals(book.getParentCollection(), collection);

        Assertions.assertFalse(collection.addElement(book));
    }

    @Test
    public void testDeleteElement() {
        Collection collection = new Collection("collection");
        Book book = new Book("title", List.of("author1"));

        Assertions.assertFalse(collection.deleteElement(book));

        Assertions.assertTrue(collection.addElement(book));
        Assertions.assertEquals(book.getParentCollection(), collection);

        Assertions.assertTrue(collection.deleteElement(book));
        Assertions.assertNull(book.getParentCollection());
    }

    @Test
    public void testGetElements() {
        Collection collection = new Collection("collection");
        List<Book> books = List.of(
                new Book("book1", List.of("author1")),
                new Book("book2", List.of("author2")),
                new Book("book3", List.of("author3"))
        );

        for(Book book: books) {
            collection.addElement(book);
        }

        Assertions.assertEquals(collection.getElements(), books);
    }
}
