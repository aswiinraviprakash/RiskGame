package utils;

import java.util.ArrayList;
import java.util.Objects;

/**
 * The class deals with the logs written to a buffer.
 */
public class LogEntryBuffer implements Observable {
    /**
     * A static object of LogEntryBuffer
     */
    private static LogEntryBuffer Logger;
    /**
     * A list of observers
     */
    private List<Observer> d_ObserverList = new ArrayList<>();

    /**
     * A constructor for LogEntryBuffer
     */
    private LogEntryBuffer() {

    }

    /**
     * A function to get the instance of LogEntryBuffer
     * @return LogEntryBuffer Logger
     */
    public static LogEntryBuffer getInstance() {
        if (Objects.isNull(Logger)) {
            Logger = new LogEntryBuffer();
        }
        return Logger;
    }

    /**
     * This method gets the information from the game and notifies the Observer.
     *
     * @param p_s The message to be notified
     */
    public void logs(String p_s) {
        notifyObservers(p_s);
    }
    
    /**
     * Clear logs
     */
    public void clear() {
        clearObservers();
    }

    /**
     * This method updates the Observer with the message.
     *
     * @param p_s The message to be updated
     */
    @Override
    public void notifyObservers(String p_s) {
        d_ObserverList.forEach(p_observer -> p_observer.log(p_s));
    }

    /**
     * A function to add an observer to the list of observers
     * @param p_Observer The observer to be added
     */
    @Override
    public void addObserver(Observer p_Observer) {
        this.d_ObserverList.add(p_Observer);
    }

    /**
     * A function to format the list of observers in the list.
     */
    @Override
    public void notifyObservers() {
        d_ObserverList.forEach(Observer::clearLogs);
    }

}
