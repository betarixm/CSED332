package edu.postech.csed332.homework1;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Random;

/**
 * A Game UI that contains a message board, a GUI panel, and two
 * control buttons (next, randomize).
 * NOTE: do not modify this file!
 */
public class GameUI {
    private final static int width = 20;
    private final static int height = 10;

    private GUIPanel gamePanel;
    private JButton nextButton;
    private JLabel message;

    GameUI(GameBoard board) {
        createGUI(board);
        randomizeGame(board);
        updateUI(board);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameBoard board = new GameBoard(width, height);
            new GameUI(board);
        });
    }

    private void createGUI(GameBoard board) {
        JFrame top = new JFrame("Problem 2");
        top.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        top.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        message = new JLabel();
        message.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridy = 0;
        gbc.ipady = 30;
        top.add(message, gbc);

        nextButton = new JButton("Next");
        nextButton.addActionListener(e -> {
            board.step();
            updateUI(board);
        });
        gbc.gridy = 1;
        gbc.ipady = 0;
        top.add(nextButton, gbc);

        JButton initButton = new JButton("Randomize");
        initButton.addActionListener(e -> {
            randomizeGame(board);
            updateUI(board);
        });
        gbc.gridy = 2;
        top.add(initButton, gbc);

        gamePanel = new GUIPanel(board);
        gbc.gridy = 3;
        top.add(gamePanel, gbc);

        top.pack();
        top.setVisible(true);
    }

    private void updateUI(GameBoard board) {
        gamePanel.repaint();
        if (!board.isValid()) {
            message.setForeground(Color.RED);
            message.setText("Invariants are violated!");
            nextButton.setEnabled(false);
        } else if (board.getNumMobs() > 0) {
            message.setForeground(Color.BLACK);
            message.setText(String.format("# Monsters : %d,    # Escaped : %d,    Killed : %d",
                    board.getNumMobs(), board.getNumMobsEscaped(), board.getNumMobsKilled()));
            nextButton.setEnabled(true);
        } else {
            message.setForeground(Color.BLUE);
            message.setText(String.format("No mosters!    (%d escaped, %d killed)",
                    board.getNumMobsEscaped(), board.getNumMobsKilled()));
            nextButton.setEnabled(false);
        }
    }

    private void randomizeGame(GameBoard board) {
        final int total = board.getWidth() * board.getHeight() / 5;
        Random rand = new Random();

        HashSet<Position> locs = new HashSet<>(total);
        while (locs.size() < total) {
            Position rp = new Position(rand.nextInt(board.getWidth()), rand.nextInt(board.getHeight()));
            if (rp.getDistance(board.getGoalPosition()) > 2)
                locs.add(rp);
        }

        board.clear();
        for (Position pos : locs) {
            Unit obj;
            int coin = rand.nextInt(8);

            if (coin < 1)
                obj = new AirTower(board);
            else if (coin < 3)
                obj = new GroundTower(board);
            else if (coin < 5)
                obj = new AirMob(board);
            else
                obj = new GroundMob(board);
            board.placeUnit(obj, pos);
        }
    }

}
