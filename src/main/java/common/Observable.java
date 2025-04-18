package common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Observable is an Abstract class used for performing observable methods
 */
public abstract class Observable implements Serializable {

    /**
     * member to store list of observers
     */
    private List<Observer> d_observers = new ArrayList<>();

    /**
     * Method to Add Observers
     * @param p_observer Observer parameter
     */
    public void addObserver(Observer p_observer) {
        this.d_observers.add(p_observer);
    }

    /**
     * Method to remove Observers
     * @param p_observer Observer parameter
     */
    public void removeObserver(Observer p_observer) {
        if (!this.d_observers.isEmpty()) this.d_observers.remove(p_observer);
    }

    /**
     * Method to notify Observers
     * @param p_observable Observer parameter
     */
    public void notifyObservers(Observable p_observable) {
        for (Observer l_observer : this.d_observers) {
            l_observer.update(p_observable);
        }
    }

}