package common;

/**
 * Templates for phases in the game
 */
public abstract class Phase {

    /**
     * Abstract class for the next phase.
     * @throws Exception
     */
    public abstract Phase nextPhase() throws Exception;

    /**
     * Method to execute a game phase.
     * @throws Exception If there is an error in the execution or validation.
     */
    public abstract void executePhase() throws Exception;

}
