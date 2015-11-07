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

    private static final int SECONDS_PER_MINUTE = 60;
    private static final int MINUTES_PER_HOUR = 60;
    private static final int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * MINUTES_PER_HOUR;

    private JLabel timeLabel = new JLabel();

    private JButton startBtn = new JButton("Start");
    private JButton pauseBtn = new JButton("Pause");
    private JButton stopBtn = new JButton("Stop");

    private CountTimer countTimer;


    public CountTimerGUI() {
        setTimerText("         ");
        GUI();
    }

    private void GUI() {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();

        panel.setLayout(new BorderLayout());
        timeLabel.setBorder(BorderFactory.createRaisedBevelBorder());
        panel.add(timeLabel, BorderLayout.NORTH);

        startBtn.addActionListener(e -> countTimer.resume());
        pauseBtn.addActionListener(e -> countTimer.pause());
        stopBtn.addActionListener(e -> countTimer.stop());
        JPanel cmdPanel = new JPanel();
        cmdPanel.setLayout(new GridLayout());

        cmdPanel.add(startBtn);
        cmdPanel.add(pauseBtn);
        cmdPanel.add(stopBtn);

        panel.add(cmdPanel, BorderLayout.SOUTH);

        JPanel clrPanel = new JPanel();
        clrPanel.setLayout(new GridLayout(0, 1));

        panel.add(clrPanel, BorderLayout.EAST);

        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();

        countTimer = new CountTimer("tmp.ser");

    }

    private void setTimerText(String sTime) {
        timeLabel.setText(sTime);
    }

    private class CountTimer implements ActionListener {

        private static final int ONE_SECOND = 1000;
        private State state;
        private Timer tmr = new Timer(ONE_SECOND, this);

        public CountTimer(String path) {
            this(new State(-1, -1, path));
        }

        public CountTimer(State state) {
            this.state = state;
            updateTimerText();
            if (this.state.isTimerActive()) {
                resume();
            }
        }

        private void updateTimerText() {
            if (state.getStartTime() == -1) {
                setTimerText(TimeFormat(0));
            } else {
                setTimerText(TimeFormat(getCurrent() / ONE_SECOND));
            }
        }

        private long getCurrent() {
            if (!state.isTimerActive()) {
                return state.getPausedValue();
            } else {
                return System.currentTimeMillis() - state.getStartTime();
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (state.isTimerActive()) {
                updateTimerText();
            }
        }

        public void resume() {
            state.setStartTime(System.currentTimeMillis() - getCurrent());
            state.setTimerActive(true);
            pauseBtn.setVisible(true);
            stopBtn.setVisible(true);
            startBtn.setVisible(false);
            tmr.restart();
        }

        public void pause() {
            state.setPausedValue(getCurrent());
            state.setTimerActive(false);
            pauseBtn.setVisible(false);
            startBtn.setVisible(true);
        }

        public void stop() {
            state.setStartTime(0);
            state.setPausedValue(0);
            state.setTimerActive(false);
            pauseBtn.setVisible(false);
            stopBtn.setVisible(false);
            startBtn.setVisible(true);
            updateTimerText();
        }
    }

    private String TimeFormat(long count) {
        int hours = (int) (count / SECONDS_PER_HOUR);
        int minutes = (int) ((count - hours * SECONDS_PER_HOUR) / SECONDS_PER_MINUTE);
        int seconds = (int) (count - minutes * SECONDS_PER_MINUTE);
        LocalTime localTime = LocalTime.of(hours, minutes, seconds);
        return localTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }
}
