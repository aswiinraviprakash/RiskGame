package common;

/**
 * Observer Interface
 */
public interface Observer {

    /**
     * This method is used to update the Observable object
     * @param p_observable Observer parameter
     */
    public void update(Observable p_observable);

}