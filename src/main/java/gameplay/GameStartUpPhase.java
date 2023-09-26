package gameplay;

import gameutils.GameCommandParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class GameStartUpPhase extends GamePhase {
    public static final String D_PHASE_NAME = "STARTUP_PHASE";

    private String d_next_phase = "";

    @Override
    public String getNextPhase() {
        return d_next_phase;
    }

    @Override
    public void validateAndExecuteCommands(String p_input_command) {
        GameCommandParser l_command_parser = new GameCommandParser(p_input_command);
        String l_primary_command = l_command_parser.getPrimaryCommand();

        switch (l_primary_command) {
            case "loadmap": {
                System.out.println("loadmap");
                break;
            }
            case "gameplayer": {
                System.out.println("gameplayer");
                break;
            }
            case "assigncountries": {
                System.out.println("assigncountries");
                break;
            }
            default:
                System.out.println("please enter a valid command that are supported");
        }
    }

    @Override
    public void executePhase() throws Exception {
        System.out.println("Start Game with following steps 1. Loadmap 2. Create Players 3. Assign Countries or endgame to End the Game");
        while (true) {
            try {
                BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));
                String l_input_command = l_reader.readLine();
                if (l_input_command.equals("endgame")) {
                    d_next_phase = "END_GAME";
                    return;
                }
                validateAndExecuteCommands(l_input_command);

            } catch (Exception e) {
                throw e;
            }
        }
    }
}
