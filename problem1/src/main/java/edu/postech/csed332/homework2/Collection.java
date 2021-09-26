package edu.postech.csed332.homework2;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The Collection class represents a single collection, which contains
 * a name of the collection and also has a list of references to every
 * book and every subcollection in the collection. A collection can
 * also be exported to and imported from a JSON string representation.
 */
public final class Collection extends SerializableElement {
    private List<Element> elements;
    private String name;

    private static final String keyName = "name";
    private static final String keyElements = "elements";

    /**
     * Creates a new collection with the given name.
     *
     * @param name the name of the collection
     */
    public Collection(String name) {
        this.name = name;
        this.elements = new ArrayList<>();
    }

    /**
     * Restores a collection from its string representation in JSON
     *
     * @param stringRepr the string representation
     */
    public static Collection restoreCollection(String stringRepr) {
        JSONObject json = new JSONObject(stringRepr);
        JSONArray elements = new JSONArray(json.getJSONArray(keyElements));

        Collection collection = new Collection(json.getString(keyName));

        for(Object i: elements){
            JSONObject element = (JSONObject) i;

            if(element.has(keyElements)) {
                collection.addElement(restoreCollection(element.toString()));
            } else {
                collection.addElement(new Book(element.toString()));
            }
        }

        return collection;
    }

    /**
     * Returns the JSON string representation of this collection, which
     * contains the name of this collection and all elements (books and
     * subcollections) contained in the collection.
     *
     * @return string representation of this collection
     */
    public String getStringRepresentation() {
        return (new JSONObject(serializer())).toString();
    }

    /**
     * Adds an element to this collection, if the element has no parent
     * collection yet. Otherwise, this method returns false, without
     * actually adding the element to this collection.
     *
     * @param element the element to add
     * @return true on success, false on fail
     */
    public boolean addElement(Element element) {
        if(element.getParentCollection() != null) {
            return false;
        }

        elements.add(element);
        element.setParentCollection(this);

        return true;
    }

    /**
     * Deletes an element from the collection. Returns false if the
     * collection does not have this element. Hint: do not forget to
     * clear parentCollection of given element.
     *
     * @param element the element to remove
     * @return true on success, false on fail
     */
    public boolean deleteElement(Element element) {
        if(element.getParentCollection() != this) {
            return false;
        }

        element.setParentCollection(null);
        elements.remove(element);

        return true;
    }

    /**
     * Returns the name of the collection.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the list of elements.
     *
     * @return the list of elements
     */
    public List<Element> getElements() {
        return elements;
    }

    @Override
    public Map<String, Object> serializer() {
        return Map.of(
                keyName, name,
                keyElements, elements.stream().map(e -> (e instanceof SerializableElement)
                        ? ((SerializableElement) e).serializer()
                        : elements.toString())
                        .toList()
        );
    }
}
