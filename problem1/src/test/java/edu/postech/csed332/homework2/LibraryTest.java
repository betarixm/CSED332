package edu.postech.csed332.homework2;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class LibraryTest {
    static Set<JSONObject> testCollection = Set.of(new JSONObject("""
            {
                  "name":"collection1",
                  "elements":[
                     {
                        "title":"title11",
                        "authors":[
                           "author11"
                        ]
                     },
                     {
                        "title":"title12",
                        "authors":[
                           "author12",
                           "AUTHOR"
                        ]
                     },
                     {
                        "name":"collection11",
                        "elements":[
                           {
                              "title":"title111",
                              "authors":[
                                 "author111"
                              ]
                           }
                        ]
                     }
                  ]
               }"""
            ), new JSONObject("""
            {
                  "name":"collection2",
                  "elements":[
                     {
                        "title":"title21",
                        "authors":[
                           "author21"
                        ]
                     },
                     {
                        "title":"title22",
                        "authors":[
                           "author22"
                        ]
                     },
                     {
                        "name":"collection21",
                        "elements":[
                           {
                              "title":"title211",
                              "authors":[
                                 "author211",
                                 "AUTHOR"
                              ]
                           }
                        ]
                     }
                  ]
               }"""
            ), new JSONObject("""
            {
                  "name":"collection3",
                  "elements":[
                     {
                        "title":"title31",
                        "authors":[
                           "author31"
                        ]
                     },
                     {
                        "title":"title32",
                        "authors":[
                           "author32",
                           "AUTHOR"
                        ]
                     },
                     {
                        "name":"collection31",
                        "elements":[
                           {
                              "title":"title311",
                              "authors":[
                                 "author311"
                              ]
                           }
                        ]
                     }
                  ]
               }""")
    );

    static JSONArray testJson = new JSONArray();

    static {
        testCollection.forEach(c -> testJson.put(c));
    }

    public String saveTestFile(String testName, String json) throws IOException {
        String filename = testName + "-" + UUID.randomUUID().toString();

        BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
        bw.write(json);
        bw.close();

        return filename;
    }

    public String loadFile(String filename) throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader(filename));
        String json = bf.lines().collect(Collectors.joining());
        bf.close();

        return json;
    }

    @Test
    public void testfindBooksNull() {
        Library lib = new Library();
        Assertions.assertNull(lib.findBooks("any"));
    }

    @Test
    public void testFileConstructor() {
        try {
            String filename = saveTestFile("FileConstructor", testJson.toString());

            Library lib = new Library(filename);

            Assertions.assertTrue(testJson.similar(new JSONArray(lib.serializer())));
        } catch (IOException ignore) {
        }
    }

    @Test
    public void testSaveLibraryToFile() {
        try {
            String inputFilename = saveTestFile("SaveLibraryToFile", testJson.toString());
            String resultFilename = "SaveLibraryToFile-result-" + UUID.randomUUID();
            Library lib = new Library(inputFilename);

            lib.saveLibraryToFile(resultFilename);
            Assertions.assertTrue(testJson.similar(new JSONArray(loadFile(resultFilename))));
        } catch (IOException ignore) {
        }
    }

    @Test
    public void testFindBooks() {
        try {
            String inputFilename = saveTestFile("FindBooks", testJson.toString());
            Library lib = new Library(inputFilename);

            Assertions.assertEquals(
                    Objects.requireNonNull(lib.findBooks("collection1")).stream()
                            .map(Book::getTitle).collect(Collectors.toSet()),
                    Set.of("title11", "title12", "title111")
            );

            Assertions.assertEquals(
                    Objects.requireNonNull(lib.findBooks("collection11")).stream()
                            .map(Book::getTitle).collect(Collectors.toSet()),
                    Set.of("title111")
            );


            Assertions.assertNull(
                    lib.findBooks("Not Collection")
            );

        } catch (IOException ignore) {
        }
    }

    @Test
    public void testFindBooksByAuthor() {
        try {
            String inputFilename = saveTestFile("FindBooks", testJson.toString());
            Library lib = new Library(inputFilename);

            Assertions.assertEquals(
                    lib.findBooksByAuthor("AUTHOR").stream().map(Book::getTitle).collect(Collectors.toSet()),
                    Set.of("title12", "title211", "title32")
            );

            Assertions.assertEquals(
                    lib.findBooksByAuthor("author11").stream().map(Book::getTitle).collect(Collectors.toSet()),
                    Set.of("title11")
            );

            Assertions.assertEquals(
                    lib.findBooksByAuthor("reader").stream().map(Book::getTitle).collect(Collectors.toSet()),
                    Set.of()
            );
        } catch (IOException ignore) {
        }
    }

    @Test
    public void testGetCollections() {
        try {
            String inputFilename = saveTestFile("FindBooks", testJson.toString());
            Library lib = new Library(inputFilename);
            List<Collection> collections = lib.getCollections();

            Assertions.assertEquals(
                    collections.stream().map(c -> new JSONObject(c.getStringRepresentation()).toString()).collect(Collectors.toSet()),
                    testCollection.stream().map(JSONObject::toString).collect(Collectors.toSet())
            );

        } catch (IOException ignore) {
        }
    }

}
