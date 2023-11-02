package gameplay;

import gameutils.GameException;

/**
 * This class is responsible for executing the entire game process.
 */

public class GameEngine {

    /**
     * Prints information about the game.
     * @param p_next_phase Contains the next phase.
     * @return Returns the boolean value, False Game is terminated.
     */
    private boolean validatePhases(Phase p_next_phase, GameInformation p_game_information) throws Exception {
        if (p_next_phase == null) {
            return false;
        }

        if (p_next_phase instanceof EndGamePhase) {
            p_next_phase.executePhase(p_game_information);
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

        try {

            GameInformation l_game_information = new GameInformation();
            Phase l_current_phase_obj = new GameStartUpPhase();
            l_game_information.setCurrentPhase(l_current_phase_obj);

            // Executing StartUp phase
            l_current_phase_obj.executePhase(l_game_information);

            l_current_phase_obj = l_game_information.getCurrentPhase();
            if (!validatePhases(l_current_phase_obj, l_game_information)) {
                return;
            }

            while (!(l_current_phase_obj instanceof EndGamePhase)) {
                l_current_phase_obj.executePhase(l_game_information);

                l_current_phase_obj = l_game_information.getCurrentPhase();
                if (!validatePhases(l_current_phase_obj, l_game_information)) {
                    return;
                }
            }

        } catch (GameException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            throw e;
        }

    }

}
