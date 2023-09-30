package gameplay;

import gameutils.GameCommandParser;
import constants.GameMessageConstants;
import gameutils.GameException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class GameStartUpPhase extends GamePhase {
    public static final String D_PHASE_NAME = "STARTUP_PHASE";

    private String d_next_phase = "";

    private GameInformation d_current_game_info;

    private List<String> d_completed_operations = new ArrayList<>();

    private void addOrRemovePlayers(List<GameCommandParser.CommandDetails> p_command_details) throws Exception {

        if (p_command_details.isEmpty()) throw new GameException(GameMessageConstants.D_COMMAND_INVALID + "\nExample Format: " + GameMessageConstants.D_PLAYER_COMMAND);

        for (GameCommandParser.CommandDetails l_command_detail : p_command_details) {

            List<String> l_command_parameters = l_command_detail.getCommandParameters();
            if (l_command_parameters.isEmpty()) throw new GameException(GameMessageConstants.D_COMMAND_PARAMETER_INVALID + "\nExample Format: " + GameMessageConstants.D_PLAYER_COMMAND);

            String l_command_option = l_command_detail.getCommandOption();
            String l_player_name = String.join(" ", l_command_parameters);

            LinkedHashMap<String, Player> l_player_list = d_current_game_info.getPlayerList();

            if (l_command_option.equals("add")) {

                if (l_player_list.containsKey(l_player_name)) {
                    throw new GameException(GameMessageConstants.D_PLAYER_EXISTS);
                }
                Player l_player_obj = new Player(l_player_name);
                l_player_list.put(l_player_name, l_player_obj);
                d_current_game_info.setPlayerList(l_player_list);

                System.out.println(GameMessageConstants.D_PLAYER_ADDED);

            } else if (l_command_option.equals("remove")) {

                if (!l_player_list.containsKey(l_player_name)) {
                    throw new GameException(GameMessageConstants.D_PLAYER_NOTFOUND);
                } else {
                    l_player_list.remove(l_player_name);
                    d_current_game_info.setPlayerList(l_player_list);

                    System.out.println(GameMessageConstants.D_PLAYER_REMOVED);
                }

            } else {
                throw new GameException(GameMessageConstants.D_COMMAND_OPTION_INVALID + "\nExample Format : " + GameMessageConstants.D_PLAYER_COMMAND);
            }
        }

        if (d_current_game_info.getPlayerList().size() >= 2) d_completed_operations.add("gameplayer");
    }

    @Override
    public void validateAndExecuteCommands(String p_input_command) throws Exception {
        GameCommandParser l_command_parser = new GameCommandParser(p_input_command);
        String l_primary_command = l_command_parser.getPrimaryCommand();
        List<GameCommandParser.CommandDetails> l_command_details = l_command_parser.getParsedCommandDetails();

        switch (l_primary_command) {
            case "loadmap": {
                System.out.println("loadmap");
                break;
            }
            case "gameplayer": {
                addOrRemovePlayers(l_command_details);
                break;
            }
            case "assigncountries": {
                if (d_completed_operations.contains("loadmap") && d_completed_operations.contains("gameplayer")) {
                    System.out.println("assigncountries");
                } else {
                    throw new GameException(GameMessageConstants.D_STARTUP_STEPS_INAVLID);
                }
                break;
            }
            default:
                throw new GameException(GameMessageConstants.D_COMMAND_INVALID);
        }
    }

    @Override
    public void executePhase(GameInformation p_game_information) throws Exception {
        System.out.println("Start Game with following steps 1. Loadmap 2. Create Players 3. Assign Countries or enter endgame to terminate");
        d_current_game_info = p_game_information;

        while (true) {
            try {
                if (d_completed_operations.contains("assigncountries")) {
                    System.out.println(GameMessageConstants.D_GAME_STARTUP_SUCCESS);
                    return;
                }

                System.out.println();
                BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));
                String l_input_command = l_reader.readLine();
                if (l_input_command.equals("endgame")) {
                    d_current_game_info.setCurrentPhase("END_GAME");
                    return;
                }
                validateAndExecuteCommands(l_input_command);

            } catch (GameException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                throw e;
            }
        }
    }

}
