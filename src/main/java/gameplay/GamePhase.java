package gameplay;

public abstract class GamePhase {

    public void validateAndExecuteCommands(String p_input_command) throws Exception { };

    public abstract void executePhase() throws Exception;

    public abstract String getNextPhase() throws Exception;

}
