package gameplay;

import common.LogEntryBuffer;
import common.Phase;
import gameplay.strategy.HumanPlayerStrategy;
import gameutils.GameCommandParser;
import constants.GameMessageConstants;
import gameutils.GameException;
import mapparser.GameMap;
import mapparser.LoadMapPhase;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Collections;

/**
 * Class GameStartUpPhase contains current phase, next phase, and current game information
 */
public class GameStartUpPhase extends Phase {

    /**
     * member to store logger instance
     */
    private static LogEntryBuffer d_logger = LogEntryBuffer.getInstance();

    /**
     * Contains current game information.
     */
    public GameInformation d_current_game_info;

    /**
     * Method to load the Game map.
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
            LoadMapPhase l_loadmap_phase = new LoadMapPhase(l_gamemap_filename, false);
            l_loadmap_phase.executePhase();
            mapparser.GameMap l_gamemap_obj = l_loadmap_phase.getLoadedMap();

            d_current_game_info.setCurrenGameMap(l_gamemap_obj);
            System.out.println(GameMessageConstants.D_GAMEMAP_LOADED);
            d_logger.addLogger(GameMessageConstants.D_GAMEMAP_LOADED);

        } catch (GameException e) {
            d_logger.addLogger("Load Map Failed: " + e.getMessage());
            throw new GameException("Load Map Failed: " + e.getMessage());
        } catch (Exception e) {
            throw new GameException(GameMessageConstants.D_MAP_LOAD_FAILED);
        }
    }

    /**
     * Method to add or remove players
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
                l_player_obj.setPlayerStrategy(new HumanPlayerStrategy());
                l_player_list.put(l_player_name, l_player_obj);
                d_current_game_info.setPlayerList(l_player_list);

                System.out.println(GameMessageConstants.D_PLAYER_ADDED);
                d_logger.addLogger("Player added successfully");

            } else if (l_command_option.equals("remove")) {

                if (!l_player_list.containsKey(l_player_name)) {
                    throw new GameException(GameMessageConstants.D_PLAYER_NOTFOUND);
                } else {
                    l_player_list.remove(l_player_name);
                    d_current_game_info.setPlayerList(l_player_list);

                    System.out.println(GameMessageConstants.D_PLAYER_REMOVED);
                    d_logger.addLogger("Player removed successfully");
                }

            } else {
                throw new GameException(GameMessageConstants.D_COMMAND_OPTION_INVALID + "\nExample Format : " + GameMessageConstants.D_PLAYER_COMMAND);
            }
        }
    }

    /**
     * Method to assign countries to players.
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
            for (GameMap.Country l_player_country : l_player_countries) {
                l_player_country.setPlayerName(l_player.getKey());
            }
            l_player_obj.setConqueredCountries(l_player_countries);
            l_start_index = l_end_index;
            l_end_index = l_end_index + l_countriesto_assign;
        }

        System.out.println(GameMessageConstants.D_COUNTRIES_ASSIGNED);
        d_logger.addLogger(GameMessageConstants.D_COUNTRIES_ASSIGNED);
    }

    /**
     * To validate setup commands provided by the user
     * This method takes a String, validates it, and performs action based on the primary command
     * @param p_input_command The input command to validate and execute.
     * @throws Exception Throws exception if there is an error in the command or if the command is invalid.
     */
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
                assignCountriesToPlayers(l_command_details);
                break;
            }
            default:
                throw new GameException(GameMessageConstants.D_COMMAND_INVALID);
        }
    }

    private void validateAndAddStrategy(String l_command_input) throws Exception {

        switch (l_command_input) {
            case "human": {
                BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));
                System.out.println("add player for human player strategy (gameplayer -add playername)");
                String l_player_command = l_reader.readLine();
                while (l_player_command != null) {
                    try {

                        if (!l_player_command.startsWith("gameplayer")) {
                            throw new GameException(GameMessageConstants.D_COMMAND_INVALID);
                        } else {
                            validateAndExecuteCommands(l_player_command);
                            break;
                        }

                    } catch (GameException e) {
                        System.out.println(e.getMessage());
                    }

                    l_player_command = l_reader.readLine();
                }
                break;
            }
            case "aggressive": {
                Player l_player_obj = new Player("aggressive");
                l_player_obj.setPlayerStrategy(new HumanPlayerStrategy());
                LinkedHashMap<String, Player> l_player_list = d_current_game_info.getPlayerList();
                l_player_list.put("aggressive", l_player_obj);
                d_current_game_info.setPlayerList(l_player_list);
                break;
            }
            case "benevolent": {
                break;
            }
            case "random": {
                break;
            }
            case "cheater": {
                break;
            }
            default:
                throw new GameException(GameMessageConstants.D_STRATEGIES_INVALID);
        }

    }

    private void handleSingleGameMode() throws Exception {

        BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("start by loading the map file");
        String l_command_input = l_reader.readLine();

        while (l_command_input != null) {
            try {
                if (!l_command_input.startsWith("loadmap")) {
                    throw new GameException(GameMessageConstants.D_COMMAND_INVALID);
                } else {
                    validateAndExecuteCommands(l_command_input);
                    break;
                }

            } catch (GameException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                throw e;
            }

            l_command_input = l_reader.readLine();
        }

        // handling startegies

        System.out.println("enter the player startegies");
        l_command_input = l_reader.readLine();
        while (l_command_input != null) {
            try {
                if (l_command_input.equals("continue")) {

                    if (d_current_game_info.getPlayerList().size() >= 2) {
                        break;
                    } else {
                        throw new GameException(GameMessageConstants.D_STRATEGIES_INSUFFICIENT);
                    }

                } else {
                    validateAndAddStrategy(l_command_input);
                }
            } catch (GameException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                throw e;
            }

            l_command_input = l_reader.readLine();
        }

        System.out.println("start assigning countries to players");
        l_command_input = l_reader.readLine();

        while (l_command_input != null) {
            try {
                if (!l_command_input.startsWith("assigncountries")) {
                    throw new GameException(GameMessageConstants.D_COMMAND_INVALID);
                } else {
                    validateAndExecuteCommands(l_command_input);
                    break;
                }

            } catch (GameException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                throw e;
            }

            l_command_input = l_reader.readLine();
        }
    }

    private void handleTournamentMode() throws Exception {
        GameMode l_current_game_mode = d_current_game_info.getGameMode();
        GameMode.GameDetails l_current_game_detail = l_current_game_mode.getCurrentGameDetails();

        // loading map for the game
        String l_map_path = l_current_game_detail.getCurrentMap();
        validateAndExecuteCommands("loadmap " + l_map_path);

        List<String> l_player_strategies = l_current_game_mode.getStrategies();
        for (String l_player_strategy : l_player_strategies) {
            validateAndAddStrategy(l_player_strategy);
        }

        // assigncountries to player
        validateAndExecuteCommands("assigncountries");
    }


    /**
     * Method deals with processing to the next phase.
     * @return object of reinforcement phase.
     * @throws Exception If there is an error in the execution or validation.
     */
    @Override
    public Phase nextPhase() throws Exception {
        return new ReinforcementPhase();
    }

    /**
     * Methods guide players, where they can perform actions like loading a map, creating players, and assigning countries.
     * Accepts actions till user chooses to terminate game by entering endgame.
     * @throws Exception If there is an error during the execution of startup steps or if an unexpected exception occurs.
     */
    @Override
    public void executePhase() throws Exception {
        System.out.println("Start Game with following commands loadmap -> gameplayer -> assigncountries or enter endgame to terminate");
        d_current_game_info = GameInformation.getInstance();
        BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            if (d_current_game_info.getGameMode().getGameMode().equals(GameMode.Mode.D_SINGLE_GAME_MODE)) {
                handleSingleGameMode();
            } else if (d_current_game_info.getGameMode().getGameMode().equals(GameMode.Mode.D_TOURNAMENT_MODE)) {
                handleTournamentMode();
            }

            System.out.println(GameMessageConstants.D_GAME_STARTUP_SUCCESS);
            d_logger.addLogger(GameMessageConstants.D_GAME_STARTUP_SUCCESS);
            d_current_game_info.setCurrentPhase(this.nextPhase());
        } catch (GameException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            d_logger.addLogger(e.getMessage());
            throw e;
        }
    }

}
