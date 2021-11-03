package edu.postech.csed332.homework6;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class GameUI {
    private static final int unitSize = 10;
    private static final int width = 45;
    private static final int height = 45;

    final JFrame top;

    public GameUI(Board board) {
        top = new JFrame("Even/Odd Sudoku");
        top.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        top.setLayout(new GridLayout(3, 3));

        Dimension dim = new Dimension(unitSize * width, unitSize * height);
        top.setMinimumSize(dim);
        top.setPreferredSize(dim);

        createCellUI(board);

        top.pack();
        top.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Board board = new Board(GameInstanceFactory.createGameInstance());
            new GameUI(board);
        });
    }

    /**
     * Create UI for cells
     * @param board
     */
    private void createCellUI(Board board) {
        CellUI[][] cells = new CellUI[9][9];
        JPanel[][] squares = new JPanel[3][3];

        for(int i = 0; i < squares.length; i++) {
            for(int j = 0; j < squares[i].length; j++){
                squares[i][j] = new JPanel();
                squares[i][j].setLayout(new GridLayout(3, 3));

                for(int k = 0; k < 3; k++) {
                    for(int l = 0; l < 3; l++) {
                        cells[k + 3 * i][l + 3 * j] = new CellUI(board.getCell(k + 3 * i, l + 3 * j));
                        squares[i][j].add(cells[k + 3 * i][l + 3 * j]);
                    }
                }

                top.add(squares[i][j]);
            }
        }
    }

}
