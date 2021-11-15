package edu.postech.csed332.homework6;

import edu.postech.csed332.homework6.events.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.BitSet;
import java.util.HashSet;
import java.util.Optional;

/**
 * A cell that has a number and a set of possibilities. Even cells can contain only even numbers, and odd cells can
 * contain only odd numbers. A cell may have a number of observers, and notifies events to the observers.
 */
public class Cell extends Subject {
    enum Type {EVEN, ODD}

    Type _type;
    Integer _value;
    BitSet _possibilities, _fixedPossibility;


    //TODO: add private member variables for Board

    /**
     * Creates an empty cell with a given type. Initially, no number is assigned.
     *
     * @param type EVEN or ODD
     */
    public Cell(@NotNull Type type) {
        this._type = type;
        this._possibilities = new BitSet(10); // 0 is not used, only use 1-9
        this._fixedPossibility = new BitSet(10);

        for (int i = 1; i <= 9; i++) {
            if ((i % 2) == type.ordinal()) {
                _possibilities.set(i);
            }
            _fixedPossibility.set(i);
        }
    }

    /**
     * Returns the type of this cell.
     *
     * @return the type
     */
    @NotNull
    public Type getType() {
        return _type;
    }

    /**
     * Returns the number of this cell.
     *
     * @return the number; Optional.empty() if no number assigned
     */
    @NotNull
    public Optional<Integer> getNumber() {
        if (_value != null)
            return Optional.of(_value);
        else
            return Optional.empty();
    }

    /**
     * Sets a number of this cell and notifies a SetNumberEvent, provided that the cell has previously no number
     * and the given number to be set is in the set of possibilities.
     *
     * @param number the number
     */
    public void setNumber(int number) {
        if (_value == null && containsPossibility(number)) {
            _value = number;
            notifyObservers(new SetNumberEvent(number));
        }
    }

    public void setFixedNumber(int number){
        _value = number;
        notifyObservers(new SetFixedNumberEvent(number));
    }

    /**
     * Removes the number of this cell and notifies an UnsetNumberEvent, provided that the cell has a number.
     */
    public void unsetNumber() {
        if (_value != null) {
            notifyObservers(new UnsetNumberEvent(_value));
            _value = null;
        }
    }

    /**
     * Adds a group for this cell. This methods should also call addObserver(group).
     *
     * @param group a group
     */
    public void addGroup(@NotNull Group group) {
        addObserver(group);

        //TODO: implement this
    }

    /**
     * Returns true if a given number is in the set of possibilities
     *
     * @param n a number
     * @return true if n is in the set of possibilities
     */
    @NotNull
    public Boolean containsPossibility(int n) {
        return _fixedPossibility.get(n) && _possibilities.get(n);
    }

    /**
     * Returns true if the cell has no possibility
     *
     * @return true if the set of possibilities is empty
     */
    @NotNull
    public Boolean emptyPossibility() {
        BitSet bitSet = (BitSet) _possibilities.clone();
        bitSet.and(_fixedPossibility);
        return bitSet.isEmpty();
    }

    /**
     * Adds the possibility of a given number, if possible, and notifies an EnabledEvent if the set of possibilities,
     * previously empty, becomes non-empty. Even (resp., odd) cells have only even (resp., odd) possibilities. Also,
     * if this number is already used by another cell in the same group with this cell, the number cannot be added to
     * the set of possibilities.
     *
     * @param number the number
     */
    public void addPossibility(int number) {
        if ((number % 2) == _type.ordinal()) {
            if (emptyPossibility()) {
                notifyObservers(new EnabledEvent());
            }
            _possibilities.set(number);
        }
    }

    /*
     * Removes the possibility of a given number. Notifies a DisabledEvent if the set of possibilities becomes empty.
     * Note that even (resp., odd) cells have only even (resp., odd) possibilities.
     *
     * @param number the number
     */
    public void removePossibility(int number) {
        if ((_value == null || _value != number)  && (number % 2) == _type.ordinal()) {
            _possibilities.clear(number);
            if (emptyPossibility())
                notifyObservers(new DisabledEvent());
        }
    }

    public void setFixedPossibility(int number) {
        if ((_value == null || _value != number))
            _fixedPossibility.clear(number);
    }
}
