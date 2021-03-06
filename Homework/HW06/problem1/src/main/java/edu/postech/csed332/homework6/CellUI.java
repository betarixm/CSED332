package edu.postech.csed332.homework6;

import edu.postech.csed332.homework6.events.DisabledEvent;
import edu.postech.csed332.homework6.events.EnabledEvent;
import edu.postech.csed332.homework6.events.Event;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;

public class CellUI extends JTextField implements Observer {

    /**
     * create a cell UI
     *
     * @param cell a cell model
     */
    CellUI(Cell cell) {
        cell.addObserver(this);
        initCellUI(cell);

        if (cell.getNumber().isEmpty()) {
            addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {}

                @Override
                public void keyReleased(KeyEvent e) {
                    char key = e.getKeyChar();
                    if (('1' <= key) && (key <= '9') && cell.containsPossibility(key - '0') && cell.getNumber().isEmpty()) {
                        cell.setNumber(key - '0');
                    } else if (key == KeyEvent.VK_BACK_SPACE) {
                        if (getText().isEmpty()) {
                            cell.unsetNumber();
                            setText("");
                        } else {
                            cell.setNumber(Integer.parseInt(getText()));
                        }
                    } else {
                        setText("");
                        cell.unsetNumber();
                    }
                }

                @Override
                public void keyPressed(KeyEvent e) {}
            });

        } else {
            setText(cell.getNumber().get().toString());
        }
    }

    /**
     * Mark this cell UI as active
     */
    public void setActivate() {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setEditable(true);
    }

    /**
     * Mark this cell UI as inactive
     */
    public void setDeactivate() {
        setBorder(BorderFactory.createLineBorder(Color.RED));
        setEditable(false);
    }

    /**
     * Whenever a cell is changed, this function is called. EnabledEvent and DisabledEvent are handled here.
     * If the underlying cell is enabled, mark this cell UI as active. If the underlying cell is disabled, mark
     * this cell UI as inactive.
     *
     * @param caller the subject
     * @param arg    an argument passed to caller
     */
    @Override
    public void update(Subject caller, Event arg) {
        if (arg instanceof DisabledEvent) {
            setDeactivate();
        } else if (arg instanceof EnabledEvent) {
            setActivate();
        }
    }

    /**
     * Initialize this cell UI
     *
     * @param cell a cell model
     */
    private void initCellUI(Cell cell) {
        setFont(new Font("Times", Font.BOLD, 30));
        setHorizontalAlignment(JTextField.CENTER);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        if (cell.getType() == Cell.Type.EVEN)
            setBackground(Color.LIGHT_GRAY);

        if (cell.getNumber().isPresent()) {
            setForeground(Color.BLUE);
            setText(cell.getNumber().get().toString());
            setEditable(false);
        }
    }
}
