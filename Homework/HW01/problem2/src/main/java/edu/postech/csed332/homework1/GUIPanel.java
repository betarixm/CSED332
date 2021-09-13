package edu.postech.csed332.homework1;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * A GUI panel that shows the current status of the game board
 * NOTE: do not modify this file!
 */
public class GUIPanel extends JPanel {
    private static final int unitSize = 64;

    private Image imgAirTow = getImage("airTower.png");
    private Image imgGrdTow = getImage("groundTower.png");
    private Image imgSlot = getImage("slot.png");
    private Image imgGround = getImage("ground.png");
    private Image imgAirMob = getImage("airMob.png");
    private Image imgGrdMob = getImage("groundMob.png");

    private GameBoard board;

    public GUIPanel(GameBoard board) {
        this.board = board;

        Dimension dim = new Dimension(unitSize * board.getWidth(), unitSize * board.getHeight());
        this.setMinimumSize(dim);
        this.setPreferredSize(dim);
    }

    @Override
    public void paintComponent(Graphics g) {
        paintGround(g);
        paintUnit(g, board.getGoalPosition(), imgSlot);
        paintTowers(g);
        paintGroundUnits(g);
        paintAirUnits(g);
    }

    private void paintGround(Graphics g) {
        for (int i = 0; i < board.getWidth(); ++i)
            for (int j = 0; j < board.getHeight(); ++j)
                g.drawImage(imgGround, i * unitSize, j * unitSize, null);
    }

    private void paintTowers(Graphics g) {
        for (Tower tower : board.getTowers()) {
            if (tower instanceof GroundTower)
                paintUnit(g, tower.getPosition(), imgGrdTow);
            else if (tower instanceof AirTower)
                paintUnit(g, tower.getPosition(), imgAirTow);
        }
    }

    private void paintGroundUnits(Graphics g) {
        for (Monster mob : board.getMonsters())
            if (mob instanceof GroundMob)
                paintUnit(g, mob.getPosition(), imgGrdMob);
    }

    private void paintAirUnits(Graphics g) {
        for (Monster mob : board.getMonsters())
            if (mob instanceof AirMob)
                paintUnit(g, mob.getPosition(), imgAirMob);
    }

    private void paintUnit(Graphics g, Position p, Image img) {
        g.drawImage(img, p.getX() * unitSize, p.getY() * unitSize, null);
    }

    private Image getImage(String filename) {
        return new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource(filename))).getImage();
    }
}
