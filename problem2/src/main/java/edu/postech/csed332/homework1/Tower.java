package edu.postech.csed332.homework1;

import java.util.Set;

/**
 * A tower that can attack monsters. Note that towers are ground objects.
 * NOTE: do not modify this file!
 */
public interface Tower extends Unit {

    /**
     * Returns the set of monsters that will be removed by this tower.
     *
     * @return the set of monsters to be removed
     */
    Set<Monster> attack();

    @Override
    default boolean isGround() {
        return true;
    }
}