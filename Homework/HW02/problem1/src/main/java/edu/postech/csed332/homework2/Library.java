package edu.postech.csed332.homework2;

import org.json.JSONArray;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * A container class for all collections (that eventually contain all
 * books). A library can be exported to or imported from a JSON file.
 */
public final class Library {
    private List<Collection> collections;

    /**
     * Builds a new, empty library.
     */
    public Library() {
        this.collections = new ArrayList<>();
    }

    /**
     * Builds a new library and restores its contents from a file.
     *
     * @param fileName the file from where to restore the library.
     */
    public Library(String fileName) {
        this.collections = new ArrayList<>();
        try {
            BufferedReader bf = new BufferedReader(new FileReader(fileName));
            String json = bf.lines().collect(Collectors.joining());
            bf.close();

            this.collections = deserializer(json);
        } catch (IOException e) {
            this.collections = new ArrayList<>();
        }
    }

    /**
     * Saves the contents of the library to the given file.
     *
     * @param fileName the file where to save the library
     */
    public void saveLibraryToFile(String fileName) {
        String json = (new JSONArray(serializer())).toString();

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
            bw.write(json);
            bw.flush();
            bw.close();
        } catch (IOException ignored) {
        }
    }

    /**
     * Returns the set of all books that belong to the collections
     * of a given name. Note that different collections may have the
     * same name. Return null if there is no collection of the
     * given name, and the empty set of there are such collections but
     * all of them are empty. For example, suppose that
     * - "Computer Science" is a top collection.
     * - "Operating Systems" is a collection under "Computer Science".
     * - "Linux Kernel" is a book under "Operating System".
     * - "Software Engineering" is a collection under "Computer Science".
     * - "Software Design Methods" is a bool under "Software Engineering".
     * Then, the findBooks method for "Computer Science" returns the set
     * of two books "Linux Kernel" and "Software Design Methods".
     *
     * @param collection a collection name
     * @return a set of books
     */
    public Set<Book> findBooks(String collection) {
        Set<Book> result = new HashSet<>();

        collections.forEach(c -> recursiveFindBooks(c, collection, result, false));

        return (result.size() > 0) ? result : null;
    }

    private void recursiveFindBooks(Collection collection, String target, Set<Book> books, boolean isChild) {
        if (target.equals(collection.getName())) {
            isChild = true;
        }

        for (Element e : collection.getElements()) {
            if (e instanceof Collection) {
                recursiveFindBooks((Collection) e, target, books, isChild);
            } else if (isChild && e instanceof Book) {
                books.add((Book) e);
            }
        }
    }

    /**
     * Return the set of all books written by a given author in this
     * collection (including the sub-collections). Return the empty
     * set if there is no book written by the author. Note that a book
     * may involve multiple authors.
     *
     * @param author an author
     * @return Return the set of books written by the given author
     */
    public Set<Book> findBooksByAuthor(String author) {
        Set<Book> result = new HashSet<>();

        collections.forEach(c -> recursiveFindBooksByAuthor(c, author, result));

        return result;
    }

    private void recursiveFindBooksByAuthor(Collection collection, String target, Set<Book> books) {
        for (Element e: collection.getElements()) {
            if (e instanceof Collection) {
                recursiveFindBooksByAuthor((Collection) e, target, books);
            } else if (e instanceof Book && ((Book) e).getAuthors().contains(target)) {
                books.add((Book) e);
            }
        }
    }

    /**
     * Returns the collections contained in the library.
     *
     * @return library contained elements
     */
    public List<Collection> getCollections() {
        return collections;
    }

    public List<Map<String, Object>> serializer() {
        return collections.stream().map(Collection::serializer).toList();
    }

    public List<Collection> deserializer(String json) {
        JSONArray arr = new JSONArray(json);

        return IntStream.range(0, arr.length())
                .mapToObj(arr::getJSONObject)
                .map(j -> Collection.restoreCollection(j.toString()))
                .toList();
    }
}
