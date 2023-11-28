package common;

import java.io.Serializable;

/**
 * Templates for phases in the game
 */
public abstract class Phase implements Serializable {

    /**
     * Abstract class for the next phase.
     * @throws Exception If there is an error in the execution or validation.
     * @return Returns the next phase
     */
    public abstract Phase nextPhase() throws Exception;

    /**
     * Method to execute a game phase.
     * @throws Exception If there is an error in the execution or validation.
     */
    public abstract void executePhase() throws Exception;

}
