package gameplay;

import constants.GameConstants;
import gameutils.GameCommandParser;
import constants.GameMessageConstants;
import gameutils.GameException;
import mapparser.GameMap;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Collections;

/**
 * Class GameStartUpPhase contains current phase, next phase, and current game information
 */
public class GameStartUpPhase extends GamePhase {
    public static final String D_PHASE_NAME = "STARTUP_PHASE";

    private String d_next_phase = ReinforcementPhase.D_PHASE_NAME;

    public GameInformation d_current_game_info;

    private List<String> d_completed_operations = new ArrayList<>();

    /**
     * Method to laad the Game map.
     * @param p_command_details Parsed command details of original command.
     * @throws Exception Displays the message, Command seems to be invalid enter valid one.
     */
    private void loadGameMap(List<GameCommandParser.CommandDetails> p_command_details) throws Exception {

        if (p_command_details.isEmpty()) throw new GameException(GameMessageConstants.D_COMMAND_INVALID + "\nExample Format: " + GameMessageConstants.D_LOADMAP);

        GameCommandParser.CommandDetails l_command_detail = p_command_details.get(0);
        if (l_command_detail.getHasCommandOption()) throw new GameException(GameMessageConstants.D_COMMAND_NO_OPTION_SUPPORT + "\nExample Format: " + GameMessageConstants.D_LOADMAP);

        List<String> l_command_parameters = l_command_detail.getCommandParameters();
        if (!(l_command_parameters.size() == 1)) throw new GameException(GameMessageConstants.D_COMMAND_PARAMETER_INVALID + "\nExample Format: " + GameMessageConstants.D_LOADMAP);

        try {
            String l_gamemap_filename = l_command_parameters.get(0);
            File l_file_dir = new File("").getCanonicalFile();
            l_gamemap_filename = l_file_dir.getParent() + GameConstants.D_MAP_DIRECTORY + l_gamemap_filename;


            File l_gamemap_file = new File(l_gamemap_filename);
            if (!l_gamemap_file.exists()) throw new GameException(GameMessageConstants.D_MAP_LOAD_FAILED);
            mapparser.GameMap l_gamemap_obj = new mapparser.GameMap(l_gamemap_filename);

            d_current_game_info.setCurrenGameMap(l_gamemap_obj);
            d_completed_operations.add("loadmap");
            System.out.println(GameMessageConstants.D_GAMEMAP_LOADED);

        } catch (Exception e) {
            throw new GameException(GameMessageConstants.D_MAP_LOAD_FAILED);
        }
    }

    /**
     * To add or remove players
     * @param p_command_details Parsed command details of original command.
     * @throws Exception If it is empty example format is displayed.
     */
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

    /**
     * Method to assign countries to players
     * @param p_command_details Parsed command details of original command.
     * @throws Exception Command seems to be invalid.
     */
    private void assignCountriesToPlayers(List<GameCommandParser.CommandDetails> p_command_details) throws Exception {

        if (!p_command_details.isEmpty()) throw new GameException(GameMessageConstants.D_COMMAND_INVALID + "\nExample Format: " + GameMessageConstants.D_ASSIGNCOUNTRIES_COMMAND);

        GameMap l_gamemap_obj = d_current_game_info.getGameMap();
        List<GameMap.Country> l_countries = l_gamemap_obj.getCountryObjects();
        Collections.shuffle(l_countries);

        LinkedHashMap<String, Player> l_player_list = d_current_game_info.getPlayerList();

        int l_neutral_countries_count = l_countries.size() % l_player_list.values().size();
        int l_countriesto_assign = (l_countries.size() - l_neutral_countries_count) / l_player_list.values().size();

        int l_start_index = 0;
        int l_end_index = l_countriesto_assign;
        for (java.util.Map.Entry<String, Player> l_player : l_player_list.entrySet()) {
            Player l_player_obj = l_player.getValue();
            List<GameMap.Country> l_player_countries = l_countries.subList(l_start_index, l_end_index);
            l_player_obj.setConqueredCountries(l_player_countries);
            l_start_index = l_countriesto_assign;
            l_end_index = l_end_index + l_countriesto_assign;
        }

        d_completed_operations.add("assigncountries");
        System.out.println(GameMessageConstants.D_COUNTRIES_ASSIGNED);
    }

    /**
     * {@iheritdoc}
     * To validate setup commands provided by the user
     * This method takes a String, validates it, and performs action based on the primary command
     * @param p_input_command The input command to validate and execute.
     * @throws Exception Throws exception if there is an error in the command or if the command is invalid.
     */
    @Override
    public void validateAndExecuteCommands(String p_input_command) throws Exception {
        GameCommandParser l_command_parser = new GameCommandParser(p_input_command);
        String l_primary_command = l_command_parser.getPrimaryCommand();
        List<GameCommandParser.CommandDetails> l_command_details = l_command_parser.getParsedCommandDetails();

        switch (l_primary_command) {
            case "loadmap": {
                loadGameMap(l_command_details);
                break;
            }
            case "gameplayer": {
                addOrRemovePlayers(l_command_details);
                break;
            }
            case "assigncountries": {
                if (d_completed_operations.contains("loadmap") && d_completed_operations.contains("gameplayer")) {
                    assignCountriesToPlayers(l_command_details);
                } else {
                    throw new GameException(GameMessageConstants.D_STARTUP_STEPS_INAVLID);
                }
                break;
            }
            default:
                throw new GameException(GameMessageConstants.D_COMMAND_INVALID);
        }
    }

    /**
     * {@inheritDoc}
     * Methods guide players, where they can perform actions like loading a map, creating players, and assigning countries.
     * Accepts actions till user chooses to terminate game by entering endgame.
     * @param p_game_information The game information object containing game state and data.
     * @throws Exception If there is an error during the execution of startup steps or if an unexpected exception occurs.
     */
    @Override
    public void executePhase(GameInformation p_game_information) throws Exception {
        System.out.println("Start Game with following commands loadmap -> gameplayer -> assigncountries or enter endgame to terminate");
        d_current_game_info = p_game_information;
        BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            try {
                if (d_completed_operations.contains("assigncountries")) {
                    System.out.println(GameMessageConstants.D_GAME_STARTUP_SUCCESS);
                    d_current_game_info.setCurrentPhase(this.d_next_phase);
                    return;
                }

                System.out.println();
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
