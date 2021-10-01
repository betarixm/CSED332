package edu.postech.csed332.homework3;

import org.junit.jupiter.api.BeforeEach;

public class DoubleParentPointerMutableTreeTest extends AbstractMutableTreeTest<Double, ParentPointerTree<Double>> {

    @BeforeEach
    void setUp() {
        tree = new ParentPointerTree<>();
        v1 = 1.9;
        v2 = 2.8;
        v3 = 3.7;
        v4 = 4.6;
        v5 = 5.5;
        v6 = 6.4;
        v7 = 7.3;
        v8 = 8.2;
    }

    @Override
    boolean checkInv() {
        return tree.checkInv();
    }

    // TODO: write more white-box test cases to achieve more code coverage, if needed.
    // You do not need to add more test methods, if you tests already meet the desired coverage.
}
