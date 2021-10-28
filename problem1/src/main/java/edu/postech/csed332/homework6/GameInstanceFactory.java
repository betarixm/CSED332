package edu.postech.csed332.homework6;

import java.util.Optional;

public class GameInstanceFactory {
    private final static Integer[][] numbers = new Integer[][]{
            {2, null, null, null, null, null, 4, 7, null},
            {7, null, null, null, null, null, null, null, null},
            {null, 9, null, null, null, null, null, null, null},

            {null, null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null, null},

            {null, null, 1, null, null, null, null, null, 8},
            {null, null, null, null, null, null, null, 3, null},
            {null, null, null, 6, 5, null, null, null, null}
    };

    private final static boolean[][] evenFlags = new boolean[][]{
            {true, false, true, false, true, false, true, false, false},
            {false, true, true, false, false, true, true, false, false},
            {false, false, false, true, true, false, false, true, true},

            {false, false, true, false, true, false, false, true, true},
            {false, true, false, true, true, true, false, false, false},
            {true, false, true, false, false, false, true, true, false},

            {false, true, false, false, false, true, true, false, true},
            {true, false, false, true, false, true, false, false, true},
            {true, true, false, true, false, false, false, true, false}
    };

    public static GameInstance createGameInstance() {
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                assert numbers[i][j] == null ||
                        (evenFlags[i][j] && numbers[i][j] % 2 == 0) ||
                        (!evenFlags[i][j] && numbers[i][j] % 2 == 1);

        return new GameInstance() {
            @Override
            public Optional<Integer> getNumbers(int i, int j) {
                return Optional.ofNullable(numbers[i][j]);
            }

            @Override
            public boolean isEven(int i, int j) {
                return evenFlags[i][j];
            }
        };
    }

}
