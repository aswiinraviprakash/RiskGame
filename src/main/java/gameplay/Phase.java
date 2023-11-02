package gameplay;

/**
 * Templates for phases in the game
 */
public abstract class Phase {

    public abstract Phase nextPhase() throws Exception;

    /**
     * Method to execute a game phase.
     * @param p_game_information The object containing relevant data.
     * @throws Exception If there is an error in the execution or validation.
     */
    public abstract void executePhase(GameInformation p_game_information) throws Exception;

}
