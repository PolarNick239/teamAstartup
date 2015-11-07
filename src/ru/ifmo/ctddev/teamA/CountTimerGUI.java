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
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();

        cntd = new CountTimer("tmp.ser");

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
                setTimerText(TimeFormat(getCurrent() / 1000));
            }
        }

        private long getCurrent() {
            if (!state.isTimerActive()) {
                return state.getPausedValue();
            }else {
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
            tmr.restart();
        }

        public void pause() {
            state.setPausedValue(getCurrent());
            state.setTimerActive(false);
        }
    }

    private String TimeFormat(long count) {
        int hours = (int) (count / 3600);
        int minutes = (int) ((count - hours * 3600) / 60);
        int seconds = (int) (count - minutes * 60);
        LocalTime localTime = LocalTime.of(hours, minutes, seconds);
        return localTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }
}
