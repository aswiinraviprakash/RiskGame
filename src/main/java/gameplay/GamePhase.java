package gameplay;

/**
 * Templates for phases in the game
 */
public abstract class GamePhase {

    /**
     * Validates and executes a game command based on the provided input.
     * Validates and executes commands
     * @param p_input_command Input command to be executed and validated.
     * @throws Exception If there is an error in the execution or validation.
     */
    public void validateAndExecuteCommands(String p_input_command) throws Exception { };

    /**
     * Validates and executes a game command based on the provided input.
     * @param p_input_command Input command to be executed and validated.
     * @param p_player_obj Player object associated with the command.
     * @throws Exception If there is an error in the execution or validation.
     */
    public void validateAndExecuteCommands(String p_input_command, Player p_player_obj) throws Exception { };

    /**
     * Method to execute a game phase.
     * @param p_game_information The object containing relevant data.
     * @throws Exception If there is an error in the execution or validation.
     */
    public abstract void executePhase(GameInformation p_game_information) throws Exception;

}
