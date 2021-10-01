package edu.postech.csed332.homework3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IntegerDelegateMutableTreeTest extends AbstractMutableTreeTest<Integer, DelegateTree<Integer>> {

    @BeforeEach
    void setUp() {
        tree = new DelegateTree<>(new AdjacencyListGraph<Integer>());
        v1 = 1;
        v2 = 2;
        v3 = 3;
        v4 = 4;
        v5 = 5;
        v6 = 6;
        v7 = 7;
        v8 = 8;
    }

    @Override
    boolean checkInv() {
        return tree.checkInv();
    }

    // TODO: write more white-box test cases to achieve more code coverage, if needed.
    // You do not need to add more test methods, if you tests already meet the desired coverage.

}
