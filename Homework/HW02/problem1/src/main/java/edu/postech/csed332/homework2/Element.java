package edu.postech.csed332.homework2;

/**
 * The Element class is the superclass of both Book and Collection.
 * It contains a reference to the parent collection that directly
 * contains this element.
 * <p>
 * An element can directly belong to at most one parent collection,
 * but that collection can belong to other collections. Therefore,
 * an element can indirectly belong to several collections.
 * <p>
 * However, no collection can (even indirectly) belong to itself.
 * The graph of connections along parentCollection should be acyclic.
 * It is optional for your library code to enforce this constraint.
 * <p>
 * NOTE: do not modify the Element class.
 */
public abstract class Element {
    private Collection parentCollection;

    /**
     * Get the parent collection of this element.
     *
     * @return parent collection
     */
    public Collection getParentCollection() {
        return parentCollection;
    }

    /**
     * Set the parent collection for this element.
     *
     * @param collection collection to be set as parent
     */
    public void setParentCollection(Collection collection) {
        parentCollection = collection;
    }
}
