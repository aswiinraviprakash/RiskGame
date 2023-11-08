package gameplay;

import common.LogEntryBuffer;
import common.Phase;
import gameutils.GameException;

/**
 * This class is responsible for executing the entire game process.
 */
public class GameEngine {

    private static LogEntryBuffer d_logger = LogEntryBuffer.getInstance();

    /**
     * Prints information about the game.
     * @param p_next_phase Contains the next phase.
     * @return Returns the boolean value, False Game is terminated.
     */
    private boolean validatePhases(Phase p_next_phase) throws Exception {
        if (p_next_phase == null) {
            return false;
        }

        if (p_next_phase instanceof EndGamePhase) {
            p_next_phase.executePhase();
            return false;
        } else {
            return true;
        }
    }

    /**
     * This Method executes Startup phase.
     * @throws Exception Throws exception when an error occurs, else continues till End Game.
     */
    public void initializeAndRunEngine() throws Exception {
        System.out.println("---GAME STARTED---");
        d_logger.addLogger("---GAME STARTED---");


        try {

            GameInformation l_game_information = GameInformation.getInstance();
            Phase l_current_phase_obj = new GameStartUpPhase();
            l_game_information.setCurrentPhase(l_current_phase_obj);

            // Executing StartUp phase
            l_current_phase_obj.executePhase();

            l_current_phase_obj = l_game_information.getCurrentPhase();
            if (!validatePhases(l_current_phase_obj)) {
                return;
            }

            while (!(l_current_phase_obj instanceof EndGamePhase)) {
                l_current_phase_obj.executePhase();

                l_current_phase_obj = l_game_information.getCurrentPhase();
                if (!validatePhases(l_current_phase_obj)) {
                    return;
                }
            }

        } catch (GameException e) {
            System.out.println(e.getMessage());
            d_logger.addLogger(e.getMessage());
        } catch (Exception e) {
            d_logger.addLogger(e.toString());
            throw e;
        }

    }

}
