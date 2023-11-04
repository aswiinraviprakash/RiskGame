package Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LogEntryBuffer implements Observable {
    public static LogEntryBuffer Logger;

    LogEntryWriter lw = new LogEntryWriter();

    List<Observer> observers = new ArrayList<>();

    public void log(String p_s)
    {
        notifyObservers(p_s);
    }

    public void clear()
    {
        clearObservers();
    }

    public void notifyObservers(String p_S)
    {
        //observers.forEach(p_observer -> p_observer.update(p_S));
        lw.update(p_S);
    }

    public void addObserver(Observer p_Observer)
    {
        this.observers.add(p_Observer);
    }

    public void clearObservers()
    {
        if (!observers.isEmpty())
        {
            observers.clear();
        }
    }
}
