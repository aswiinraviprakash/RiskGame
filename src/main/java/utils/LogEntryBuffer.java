package utils;

import java.util.ArrayList;
import java.util.List;

public class LogEntryBuffer extends Observable {
    public static LogEntryBuffer Logger;

    private String p_log_string;

    //LogEntryWriter lw = new LogEntryWriter();

    public LogEntryBuffer(){
        this.p_log_string = null;
    }

    public String getLogString(){
        return this.p_log_string;
    }

    public void log(String p_s)
    {
        p_log_string = p_s;
        notifyObservers(this);
    }

    public void clear()
    {
     //   clearObservers();
    }

   // public void notifyObservers(String p_S)
    //{
        //observers.forEach(p_observer -> p_observer.update(p_S));
     //   lw.update(p_S);
    //}

    //public void addObserver(Observer p_Observer)
    //{
     //   this.observers.add(p_Observer);
    //}

    //public void clearObservers()
    //{
     //   if (!observers.isEmpty())
      //  {
      //      observers.clear();
       // }
   // }
}