package edu.postech.csed332.homework3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;

public class StringAdjacencyMutableGraphTest extends AbstractMutableGraphTest<String, AdjacencyListGraph<String>> {

    @BeforeEach
    void setUp() {
        graph = new AdjacencyListGraph<>();
        v1 = "1";
        v2 = "2";
        v3 = "3";
        v4 = "4";
        v5 = "5";
        v6 = "6";
        v7 = "7";
        v8 = "8";
    }

    @Override
    boolean checkInv() {
        return graph.checkInv();
    }

    @Test
    void testCheckInvEdgeWithUnregistered() throws NoSuchFieldException, IllegalAccessException {
        graph = new AdjacencyListGraph<>();

        Field graphField = graph.getClass().getDeclaredField("adjMap");
        graphField.setAccessible(true);

        SortedMap<String, SortedSet<String>> adjMap = new TreeMap<>();
        graphField.set(graph, adjMap);

        graph.addVertex(v1);
        adjMap.get(v1).add(v8);

        Assertions.assertFalse(checkInv());
    }
}
