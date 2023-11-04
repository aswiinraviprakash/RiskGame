package Utils;

import java.util.ArrayList;
import java.util.List;

public interface Observable {


    default void addObserver()
    {
    }

    void notifyObservers(String p_S);

    default void clearObservers()
    {
    }

    default void notfiyObservers()
    {
    }

    default void addLogger(String p_S)
    {

    }



}
