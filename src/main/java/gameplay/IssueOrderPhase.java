package gameplay;

import constants.GameMessageConstants;
import gameutils.GameCommandParser;
import gameutils.GameCommonUtils;
import gameutils.GameException;
import mapparser.GameMap;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Class for players to issue orders.
 */
public class IssueOrderPhase extends GamePhase {

    public static final String D_PHASE_NAME = "ISSUE_ORDER_PHASE";

    /**
     * Contains the next phase.
     */
    private String d_next_phase = ExecuteOrderPhase.D_PHASE_NAME;

    /**
     * Contains current game information.
     */
    private GameInformation d_current_game_info;

    /**
     * Executes deploy orders based on the provided command details.
     *
     * @param p_command_details Parsed command details.
     * @param p_current_player Current player issuing an order.
     * @throws Exception If an error occurs during order execution.
     */
    private void executeDeployOrder(List<GameCommandParser.CommandDetails> p_command_details, Player p_current_player) throws Exception {

        if (!(p_command_details.size() == 1)) {
            throw new GameException(GameMessageConstants.D_COMMAND_INVALID + "\nExample Format: " + GameMessageConstants.D_DEPLOY_COMMAND);
        }

        GameCommandParser.CommandDetails l_command_detail = p_command_details.get(0);
        if (l_command_detail.getHasCommandOption()) {
            throw new GameException(GameMessageConstants.D_COMMAND_NO_OPTION_SUPPORT + "\nExample Format: " + GameMessageConstants.D_DEPLOY_COMMAND);
        }

        List<String> l_command_parameters = l_command_detail.getCommandParameters();
        if (l_command_parameters.size() > 2 || !GameCommonUtils.isNumeric(l_command_parameters.get(1))) {
            throw new GameException(GameMessageConstants.D_COMMAND_PARAMETER_INVALID + "\nExample Format: " + GameMessageConstants.D_DEPLOY_COMMAND);
        }

        String l_country_name = l_command_parameters.get(0);
        int l_armies_number = Integer.parseInt(l_command_parameters.get(1));

        if (!p_current_player.checkIfCountryConquered(l_country_name)) {
            throw new GameException(GameMessageConstants.D_COUNTRY_INVALID_FOR_PLAYER);
        }

        if (l_armies_number > p_current_player.getCurrentArmies()) {
            throw new GameException(GameMessageConstants.D_ARMIES_EXCEEDED + "\nAvailable Armies: " + p_current_player.getCurrentArmies());
        }

        p_current_player.d_current_order = new DeployOrder(l_country_name, l_armies_number);
        p_current_player.issue_order();
        p_current_player.setCurrentArmies(p_current_player.getCurrentArmies() - l_armies_number);
        System.out.println(GameMessageConstants.D_ORDER_ISSUED);
    }

    /**
     * {@inheritDoc} Validates and Executes commands for Issue_order_phase.
     *
     * @param p_input_command Input command provided by the player.
     * @param p_current_player Current player issuing the command.
     * @throws Exception If an error occurs during validation or execution.
     */
    @Override
    public void validateAndExecuteCommands(String p_input_command, Player p_current_player) throws Exception {
        GameCommandParser l_command_parser = new GameCommandParser(p_input_command);
        String l_primary_command = l_command_parser.getPrimaryCommand();
        List<GameCommandParser.CommandDetails> l_command_details = l_command_parser.getParsedCommandDetails();

        switch (l_primary_command) {
            case "deploy": {
                executeDeployOrder(l_command_details, p_current_player);
                break;
            }
            case "showmap": {
                if (!l_command_details.isEmpty()) {
                    throw new GameException(GameMessageConstants.D_COMMAND_INVALID + "\nExample Format: " + GameMessageConstants.D_SHOWMAP);
                }
                GameMap l_gamemap_obj = d_current_game_info.getGameMap();
                LinkedHashMap<String, Player> l_players_list = d_current_game_info.getPlayerList();
                //  l_gamemap_obj.showMap(l_players_list.values().stream().toList());
                l_gamemap_obj.showMap();
                break;
            }
            default:
                throw new GameException(GameMessageConstants.D_COMMAND_INVALID);
        }
    }

    /**
     * {@inheritDoc} Allows player to issue orders.
     *
     * @param p_game_information Relevant information to the current ignore.
     * @throws Exception If an error occurs.
     */
    @Override
    public void executePhase(GameInformation p_game_information) throws Exception {
        System.out.printf("%nstart issuing your orders or enter endgame to terminate%n");
        d_current_game_info = p_game_information;

        BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));

        LinkedHashMap<String, Player> l_player_list = p_game_information.getPlayerList();

        for (Map.Entry<String, Player> l_player : l_player_list.entrySet()) {
            Player l_player_obj = l_player.getValue();
            int l_current_armies = l_player_obj.getCurrentArmies();

            while (l_current_armies > 0) {
                System.out.println("Player: " + l_player.getKey() + " turn");
                System.out.println("Remaining Armies: " + l_current_armies);
                l_player_obj.d_current_order = null;
                try {
                    System.out.println();
                    String l_input_command = l_reader.readLine();
                    if (l_input_command.equals("endgame")) {
                        d_current_game_info.setCurrentPhase("END_GAME");
                        return;
                    }
                    validateAndExecuteCommands(l_input_command, l_player_obj);
                    l_current_armies = l_player_obj.getCurrentArmies();

                } catch (GameException e) {
                    System.out.println(e.getMessage());
                } catch (Exception e) {
                    throw e;
                }
            }
        }

        d_current_game_info.setCurrentPhase(this.d_next_phase);
    }
}
