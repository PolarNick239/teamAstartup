package ru.ifmo.ctddev.teamA;

import javax.swing.*;

/**
 * @author Ilya Isaev
 */
public class SimpleEx extends JFrame {

    public SimpleEx() {

        initUI();
    }

    private void initUI() {

        setTitle("Simple example");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }


}
