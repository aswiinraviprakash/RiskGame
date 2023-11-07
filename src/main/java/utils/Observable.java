package utils;

public interface Observable {

    /**
     * A function to send a message/notification to Observer.
     *
     * @param p_String the observable
     */
    public void notifyObservers(String p_String);

    /**
     * add observer
     *
     * @param p_Observer observer object
     */
    public void addObserver(Observer p_Observer);

    /**
     * clear observer
     */
    public void clearObservers();
}
