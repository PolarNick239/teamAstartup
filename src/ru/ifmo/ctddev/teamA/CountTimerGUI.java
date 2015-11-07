package ru.ifmo.ctddev.teamA;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Ilya Isaev
 */
public class CountTimerGUI {

    private JFrame frame;
    private JPanel panel;

    private JLabel timeLabel = new JLabel();

    private JButton startBtn = new JButton("Start");
    private JButton pauseBtn = new JButton("Pause");

    private CountTimer cntd;


    public CountTimerGUI() {
        setTimerText("         ");
        GUI();
    }

    private void GUI() {
        frame = new JFrame();
        panel = new JPanel();

        panel.setLayout(new BorderLayout());
        timeLabel.setBorder(BorderFactory.createRaisedBevelBorder());
        panel.add(timeLabel, BorderLayout.NORTH);

        startBtn.addActionListener(e -> cntd.resume());
        pauseBtn.addActionListener(e -> cntd.pause());

        JPanel cmdPanel = new JPanel();
        cmdPanel.setLayout(new GridLayout());

        cmdPanel.add(startBtn);
        cmdPanel.add(pauseBtn);

        panel.add(cmdPanel, BorderLayout.SOUTH);

        JPanel clrPanel = new JPanel();
        clrPanel.setLayout(new GridLayout(0, 1));

        panel.add(clrPanel, BorderLayout.EAST);

        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();

        cntd = new CountTimer();

    }

    private void setTimerText(String sTime) {
        timeLabel.setText(sTime);
    }

    private class CountTimer implements ActionListener {

        private static final int ONE_SECOND = 1000;
        private int count = 0;
        private boolean isTimerActive = false;
        private Timer tmr = new Timer(ONE_SECOND, this);

        public CountTimer() {
            count = 0;
            setTimerText(TimeFormat(count));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (isTimerActive) {
                count++;
                setTimerText(TimeFormat(count));
            }
        }

        public void resume() {
            isTimerActive = true;
            tmr.restart();
        }

        public void pause() {
            isTimerActive = false;
        }
    }

    private String TimeFormat(int count) {
        int hours = count / 3600;
        int minutes = (count - hours * 3600) / 60;
        int seconds = count - minutes * 60;
        LocalTime localTime = LocalTime.of(hours, minutes, seconds);
        return localTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }
}
