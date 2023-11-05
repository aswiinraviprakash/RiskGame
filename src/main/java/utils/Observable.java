package utils;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable{
    private List<Observer> observers = new ArrayList<Observer>();

    public void addObserver(Observer observer){
        this.observers.add(observer);
    }

    public void removeObserver(Observer observer){
        if(!this.observers.isEmpty())
        this.observers.remove(observer);
    }

    public void notifyObservers(Observable observable){
        for(Observer observer : this.observers){
            observer.update(observable);
        }
    }



}