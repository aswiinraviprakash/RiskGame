package gameplay;

import common.LogEntryBuffer;
import common.Phase;
import constants.GameConstants;
import constants.GameMessageConstants;
import gameutils.GameCommandParser;
import gameutils.GameCommonUtils;
import gameutils.GameException;
import mapparser.LoadMapPhase;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * This class is responsible for executing the entire game process.
 */
public class GameEngine {

    /**
     * member to store logger instance
     */
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
     * Method to hadndle load game
     * @param p_command_details list of command details
     * @throws Exception throws if any errors are caught in the code block
     */
    private void handleLoadGame(List<GameCommandParser.CommandDetails> p_command_details) throws Exception {
        if (p_command_details.isEmpty()) throw new GameException(GameMessageConstants.D_COMMAND_INVALID + "\nExample Format: " + GameMessageConstants.D_LOADGAME);

        GameCommandParser.CommandDetails l_command_detail = p_command_details.get(0);
        if (l_command_detail.getHasCommandOption()) throw new GameException(GameMessageConstants.D_COMMAND_NO_OPTION_SUPPORT + "\nExample Format: " + GameMessageConstants.D_LOADGAME);

        List<String> l_command_parameters = l_command_detail.getCommandParameters();
        if (!(l_command_parameters.size() == 1)) throw new GameException(GameMessageConstants.D_COMMAND_PARAMETER_INVALID + "\nExample Format: " + GameMessageConstants.D_LOADGAME);

        try {

            String l_game_filename = l_command_parameters.get(0);
            LoadGamePhase l_loadgame_phase = new LoadGamePhase(l_game_filename);
            l_loadgame_phase.executePhase();

        } catch (Exception e) {
            d_logger.addLogger(GameMessageConstants.D_GAME_LOAD_FAILED);
            throw new GameException(GameMessageConstants.D_GAME_LOAD_FAILED);
        }

    }

    /**
     *
     * @param p_command_details list of commmand details
     * @throws Exception throws if any errors are caught in the code block
     */
    private void handleSingleGameMode(List<GameCommandParser.CommandDetails> p_command_details) throws Exception {
        if (!p_command_details.isEmpty()) throw new GameException(GameMessageConstants.D_COMMAND_INVALID + "\nExample Format: " + GameMessageConstants.D_SINGLEGAME_MODE);

        System.out.println("start new game(startgame) or load game(loadgame filename)");
        BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));
        String l_input_command = l_reader.readLine();

        gametype:
        while (l_input_command != null) {
            GameCommandParser l_command_parser = new GameCommandParser(l_input_command);
            String l_primary_command = l_command_parser.getPrimaryCommand();
            List<GameCommandParser.CommandDetails> l_command_details = l_command_parser.getParsedCommandDetails();

            try {
                switch (l_primary_command) {
                    case "startgame": {
                        GameMode gameMode = new GameMode();
                        gameMode.setGameMode(GameMode.Mode.D_SINGLE_GAME_MODE);
                        GameInformation l_current_game_info = GameInformation.getInstance();
                        l_current_game_info.setGameMode(gameMode);
                        l_current_game_info.setGameState(GameConstants.GameState.D_START_GAME);
                        break gametype;
                    }
                    case "loadgame": {
                        handleLoadGame(l_command_details);
                        break gametype;
                    }
                    default:
                        throw new GameException(GameMessageConstants.D_COMMAND_INVALID);
                }
            } catch (GameException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                throw e;
            }

            l_input_command = l_reader.readLine();
        }

    }

    /**
     *  Method to handle tournament mode
     * @param p_command_details list of command details
     * @throws Exception throws if any errors are caught in the code block
     */
    public void handleTournamentMode(List<GameCommandParser.CommandDetails> p_command_details) throws Exception {

        if (p_command_details.size() != 4) throw new GameException(GameMessageConstants.D_COMMAND_INVALID + "\nExample Format: " + GameMessageConstants.D_TOURNAMENT_MODE);

        String[] l_command_options = new String[] {"M", "P", "G", "D"};
        GameMode l_game_mode = new GameMode();
        l_game_mode.setGameMode(GameMode.Mode.D_TOURNAMENT_MODE);

        int l_index = 0;
        for (String l_command : l_command_options) {
            GameCommandParser.CommandDetails l_command_detail = p_command_details.get(l_index);
            if (!l_command_detail.getCommandOption().equals(l_command)) throw new GameException(GameMessageConstants.D_COMMAND_OPTION_INVALID + "\nExample Format: " + GameMessageConstants.D_TOURNAMENT_MODE);
            List<String> l_command_parameters = l_command_detail.getCommandParameters();
            if (l_command_parameters.size() != 1) throw new GameException(GameMessageConstants.D_COMMAND_PARAMETER_INVALID + "\nExample Format: " + GameMessageConstants.D_TOURNAMENT_MODE);

            switch (l_command) {
                case "M": {
                    String[] l_command_values = l_command_parameters.get(0).split(",");
                    for (String l_command_value : l_command_values) {
                        try {
                            LoadMapPhase l_loadmap_phase = new LoadMapPhase(l_command_value, false);
                            l_loadmap_phase.executePhase();
                            mapparser.GameMap l_gamemap_obj = l_loadmap_phase.getLoadedMap();
                        } catch (GameException e) {
                            throw new GameException("Load Map Failed for map " + l_command_value +": "+ e.getMessage());
                        } catch (Exception e) {
                            throw new GameException(GameMessageConstants.D_MAP_LOAD_FAILED);
                        }

                        l_game_mode.setGameMap(l_command_value);
                    }
                    break;
                }
                case "P": {
                    String[] l_command_values = l_command_parameters.get(0).split(",");
                    for (String l_command_value : l_command_values) {
                        if (!GameConstants.D_GAME_COMPUTER_STRATEGIES.contains(l_command_value)) throw new GameException(l_command_value + ": "+ GameMessageConstants.D_STRATEGIES_INVALID);
                        if (l_game_mode.getStrategies().contains(l_command_value)) throw new GameException(GameMessageConstants.D_STRATEGIES_DUPLICATE);
                        l_game_mode.setGameStrategy(l_command_value);
                    }
                    break;
                }
                case "G": {
                    if (!GameCommonUtils.isNumeric(l_command_parameters.get(0))) throw new GameException(GameMessageConstants.D_COMMAND_PARAMETER_INVALID + "\nExample Format: " + GameMessageConstants.D_TOURNAMENT_MODE);
                    l_game_mode.setNumberOfGames(Integer.parseInt(l_command_parameters.get(0)));
                    break;
                }
                case "D": {
                    if (!GameCommonUtils.isNumeric(l_command_parameters.get(0))) throw new GameException(GameMessageConstants.D_COMMAND_PARAMETER_INVALID + "\nExample Format: " + GameMessageConstants.D_TOURNAMENT_MODE);
                    l_game_mode.setNumberOfTurns(Integer.parseInt(l_command_parameters.get(0)));
                    break;
                }
            }
            l_index = l_index + 1;
        }

        GameInformation l_current_game_info = GameInformation.getInstance();
        l_current_game_info.setGameMode(l_game_mode);
    }

    /**
     * Method to handle game modes
     * @throws Exception throws if any errors are caught in the code block
     */
    private void handleGameModes() throws Exception {

        System.out.println("select the game mode -> singlegame or tournament");
        BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));
        String l_input_command = l_reader.readLine();

        gamemode:
        while (l_input_command != null) {
            GameCommandParser l_command_parser = new GameCommandParser(l_input_command);
            String l_primary_command = l_command_parser.getPrimaryCommand();
            List<GameCommandParser.CommandDetails> l_command_details = l_command_parser.getParsedCommandDetails();

            try {
                switch (l_primary_command) {
                    case "singlegame": {
                        handleSingleGameMode(l_command_details);
                        break gamemode;
                    }
                    case "tournament": {
                        handleTournamentMode(l_command_details);
                        break gamemode;
                    }
                    default:
                        throw new GameException(GameMessageConstants.D_COMMAND_INVALID);
                }
            } catch (GameException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                throw e;
            }

            l_input_command = l_reader.readLine();
        }
    }


    /**
     * Method to print tournament results
     */
    public static void drawTable() {
        String tables_border = "+---------------";
        for (int i = 0; i < 4; i++) {
            System.out.print(tables_border);
        }
        System.out.format("+%n");
        System.out.format("%-16s %-16s %-16s %-16s", "Game", "Map", "Result", "Turns");
        System.out.format("%n");
        for (int i = 0; i < 4; i++) {
            System.out.format(tables_border);
        }
        System.out.format("+%n");
    }

    private static void printTournamentModeResults() {
        GameMode l_current_game_mode = GameInformation.getInstance().getGameMode();
        List<GameMode.GameDetails> l_game_details = l_current_game_mode.getGameDetails();
        int counter = 1;
        for (GameMode.GameDetails l_game_detail : l_game_details) {
            if (l_game_detail.getGameNumber() == counter) {
                drawTable();
                ++counter;
            }
            if (l_game_detail.getGameWinner() == null) {
                System.out.format("%-16s %-16s %-16s %-16s%n", l_game_detail.getGameNumber(), l_game_detail.getCurrentMap(), "Draw", l_game_detail.getTurnsPlayed());
            }
            else {
                System.out.format("%-16s %-16s %-16s %-16s%n", l_game_detail.getGameNumber(), l_game_detail.getCurrentMap(), l_game_detail.getGameWinner() , l_game_detail.getTurnsPlayed());
            }
        }
    }

    /**
     * This Method executes Startup phase.
     * @throws Exception Throws exception when an error occurs, else continues till End Game.
     */
    public void initializeAndRunEngine() throws Exception {
        System.out.println("---GAME STARTED---");
        d_logger.addLogger("---GAME STARTED---");
        GameInformation.loadGameInfoInstance(null);

        try {

            // handling game modes
            handleGameModes();

            GameInformation l_game_information = GameInformation.getInstance();
            GameMode l_current_game_mode = l_game_information.getGameMode();

            if (l_current_game_mode.getGameMode().equals(GameMode.Mode.D_SINGLE_GAME_MODE)) {

                Phase l_current_phase_obj;

                if (l_game_information.getGameState().equals(GameConstants.GameState.D_START_GAME)) {
                    l_current_phase_obj = new GameStartUpPhase();
                    l_game_information.setCurrentPhase(l_current_phase_obj);

                    // Executing StartUp phase
                    l_current_phase_obj.executePhase();

                } else if (l_game_information.getGameState().equals(GameConstants.GameState.D_LOAD_GAME)) {

                    l_current_phase_obj = l_game_information.getLastSessionPhase();
                    l_game_information.setCurrentPhase(l_current_phase_obj);
                    l_game_information.setLastSessionPhase(null);
                }

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

            } else if (l_current_game_mode.getGameMode().equals(GameMode.Mode.D_TOURNAMENT_MODE)) {
                int l_total_number_games = l_current_game_mode.getNumberOfGames() * l_current_game_mode.getGameMaps().size();
                l_current_game_mode.setTotalNumberOfGames(l_total_number_games);

                l_current_game_mode.setCurrentGameNumber(1);
                l_current_game_mode.setCurrentMapNumber(1);

                for (int l_index = 1; l_index <= l_total_number_games; l_index++) {
                    GameMode.GameDetails l_game_details = l_current_game_mode.new GameDetails();
                    int l_current_game_number = l_current_game_mode.getCurrentGameNumber();
                    int l_current_map_number = l_current_game_mode.getCurrentMapNumber();
                    String l_current_map = l_current_game_mode.getGameMaps().get(l_current_map_number - 1);

                    l_game_details.setGameNumber(l_current_game_number);
                    l_game_details.setCurrentMap(l_current_map);
                    l_current_game_mode.setCurrentGameDetails(l_game_details);

                    Phase l_current_phase_obj = new GameStartUpPhase();
                    l_game_information.setCurrentPhase(l_current_phase_obj);

                    // Executing StartUp phase
                    l_current_phase_obj.executePhase();

                    l_current_phase_obj = l_game_information.getCurrentPhase();

                    while (!(l_current_phase_obj instanceof EndGamePhase)) {
                        l_current_phase_obj.executePhase();

                        l_current_phase_obj = l_game_information.getCurrentPhase();
                        if (l_current_phase_obj instanceof ReinforcementPhase) {

                            l_game_details = l_current_game_mode.getCurrentGameDetails();
                            int l_turns_played = l_game_details.getTurnsPlayed();
                            l_turns_played = l_turns_played + 1;
                            l_game_details.setTurnsPlayed(l_turns_played);

                            if (l_turns_played == l_current_game_mode.getNumberOfTurns()) {
                                l_game_details.setGameWinner(null);
                                l_current_game_mode.setGameDetail(l_game_details);
                                break;
                            }
                        }
                    }

                    l_current_game_mode.setCurrentGameDetails(null);
                    l_current_map_number = l_current_map_number + 1;
                    if (l_current_map_number > l_current_game_mode.getGameMaps().size()) {
                        l_current_game_number = l_current_game_number + 1;
                        l_current_map_number = 1;

                    }
                    l_current_game_mode.setCurrentMapNumber(l_current_map_number);
                    l_current_game_mode.setCurrentGameNumber(l_current_game_number);

                    // resetting game values
                    l_game_information.setPlayerList(new LinkedHashMap<>());
                    l_game_information.setCurrenGameMap(null);
                    l_game_information.setCurrentPhase(null);
                }

                printTournamentModeResults();
            }

        } catch (GameException e) {
            System.out.println(e.getMessage());
            d_logger.addLogger(e.getMessage());
        } catch (Exception e) {
            d_logger.addLogger(e.getMessage());
            throw e;
        }

    }

}
