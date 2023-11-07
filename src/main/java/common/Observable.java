package common;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable {
    private List<Observer> d_observers = new ArrayList<>();

    public void addObserver(Observer p_observer) {
        this.d_observers.add(p_observer);
    }

    public void removeObserver(Observer p_observer) {
        if (!this.d_observers.isEmpty()) this.d_observers.remove(p_observer);
    }

    public void notifyObservers(Observable p_observable) {
        for (Observer l_observer : this.d_observers) {
            l_observer.update(p_observable);
        }
    }

}