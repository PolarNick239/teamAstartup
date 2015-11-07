package ru.ifmo.ctddev.teamA;

import java.io.*;
import java.util.logging.Logger;

/**
 * @author Polyarnyi Nickolay, PolarNick239
 */
public class State implements Serializable {
    public static final Logger LOG = Logger.getLogger(State.class.getName());

    private long startTime;
    private long pausedValue;
    private boolean isTimerActive = false;
    private String path;

    public State(long startTime, long pausedValue, String path) {
        this.startTime = startTime;
        this.pausedValue = pausedValue;
        this.path = path;
        this.load();
    }

    public long getStartTime() {
        return startTime;
    }

    public long getPausedValue() {
        return pausedValue;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
        save();
    }

    public void setPausedValue(long pausedValue) {
        this.pausedValue = pausedValue;
        save();
    }

    public boolean isTimerActive() {
        return isTimerActive;
    }

    public void setTimerActive(boolean isTimerActive) {
        this.isTimerActive = isTimerActive;
        save();
    }

    private void load() {
        try (
                InputStream file = new FileInputStream(this.path);
                InputStream buffer = new BufferedInputStream(file);
                ObjectInput input = new ObjectInputStream(buffer)
        ) {
            State state = (State) input.readObject();
            this.setPausedValue(state.getPausedValue());
            this.setStartTime(state.getStartTime());
            this.setTimerActive(state.isTimerActive());
        } catch (InvalidClassException | FileNotFoundException ignored) {
            //TODO YOU MUST NOT IGNORE EXCEPTIONS BITCHES
        } catch (IOException | ClassNotFoundException e) {
            LOG.severe(e.getMessage());
        }
    }

    private void save() {
        try (OutputStream file = new FileOutputStream(this.path);
             OutputStream buffer = new BufferedOutputStream(file);
             ObjectOutput output = new ObjectOutputStream(buffer)
        ) {
            output.writeObject(this);
        } catch (IOException ex) {
            LOG.severe(ex.getMessage());
        }
    }

}
