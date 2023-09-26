package gameplay;

import java.util.HashMap;

public class GameEngine {

    private String d_current_phase = GameStartUpPhase.D_PHASE_NAME;

    private static HashMap<String, String> D_game_phases;

    static {
        D_game_phases = new HashMap<>();
        D_game_phases.put(GameStartUpPhase.D_PHASE_NAME, "gameplay.GameStartUpPhase");
    }

    public void initializeAndRunEngine() throws Exception {
        System.out.println("---GAME STARTED---");
        while (!d_current_phase.equals("END_GAME")) {

            try {

                Class l_current_phase_class = Class.forName(D_game_phases.get(d_current_phase));
                GamePhase l_current_phase_obj = (GamePhase) l_current_phase_class.getDeclaredConstructor().newInstance();
                l_current_phase_obj.executePhase();
                String l_next_phase = l_current_phase_obj.getNextPhase();
                if (!l_next_phase.isEmpty()) {
                    if (l_next_phase.equals("END_GAME")) {
                        System.out.println("ENDING GAME...");
                        return;
                    }
                    else {
                        d_current_phase = l_next_phase;
                    }
                }

            } catch (Exception e) {
                throw e;
            }

        }
    }

}
