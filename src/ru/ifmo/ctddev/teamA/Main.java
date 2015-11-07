package ru.ifmo.ctddev.teamA;

import java.awt.*;

public class Main {

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                SimpleEx ex = new SimpleEx();
                ex.setVisible(true);
            }
        });
    }
}
