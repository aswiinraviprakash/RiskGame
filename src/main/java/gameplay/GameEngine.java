package gameplay;

import gameutils.GameException;

import java.util.HashMap;

public class GameEngine {
    private static HashMap<String, String> D_game_phases;

    static {
        D_game_phases = new HashMap<>();
        D_game_phases.put(GameStartUpPhase.D_PHASE_NAME, "gameplay.GameStartUpPhase");
        D_game_phases.put(IssueOrderPhase.D_PHASE_NAME, "gameplay.IssueOrderPhase");
    }

    private boolean validatePhases(String p_next_phase) {
        if (p_next_phase.isEmpty()) {
            return false;
        }

        if (p_next_phase.equals("TERMINATE_GAME")) {
            System.out.println("GAME TERMINATED");
            return false;
        } else {
            return true;
        }
    }

    public void initializeAndRunEngine() throws Exception {
        System.out.println("---GAME STARTED---");

        try {

            GameInformation l_game_information = new GameInformation();
            String l_current_phase = GameStartUpPhase.D_PHASE_NAME;
            l_game_information.setCurrentPhase(l_current_phase);

            // Executing StartUp phase
            Class l_current_phase_class = Class.forName(D_game_phases.get(l_current_phase));
            GamePhase l_current_phase_obj = (GamePhase) l_current_phase_class.getDeclaredConstructor().newInstance();
            l_current_phase_obj.executePhase(l_game_information);

            l_current_phase = l_game_information.getCurrentPhase();
            if (!validatePhases(l_current_phase)) {
                return;
            }

            while (!l_current_phase.equals("END_GAME")) {
                l_current_phase_class = Class.forName(D_game_phases.get(l_current_phase));
                l_current_phase_obj = (GamePhase) l_current_phase_class.getDeclaredConstructor().newInstance();
                l_current_phase_obj.executePhase(l_game_information);

                l_current_phase = l_game_information.getCurrentPhase();
                if (!validatePhases(l_current_phase)) {
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
