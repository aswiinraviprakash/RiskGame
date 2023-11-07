package gameplay;

import common.Phase;
import constants.GameMessageConstants;
import gameutils.GameCommandParser;
import gameutils.GameCommonUtils;
import gameutils.GameException;
import mapparser.GameMap;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Class for players to issue orders.
 */
public class IssueOrderPhase extends Phase {

    /**
     * Contains current game information.
     */
    private GameInformation d_current_game_info;

    /**
     * Executes deploy orders based on the provided command details.
     * @param p_input_command command given by user.
     * @param p_current_player Current player issuing an order.
     * @throws Exception If an error occurs during order execution.
     */
    public void executeDeployOrder(String p_input_command, Player p_current_player) throws Exception {

        GameCommandParser l_command_parser = new GameCommandParser(p_input_command);
        String l_primary_command = l_command_parser.getPrimaryCommand();
        List<GameCommandParser.CommandDetails> l_command_details = l_command_parser.getParsedCommandDetails();

        if (l_primary_command.equals("showmap")) {
            if (!l_command_details.isEmpty()) throw new GameException(GameMessageConstants.D_COMMAND_INVALID + "\nExample Format: " + GameMessageConstants.D_SHOWMAP);
            GameMap l_gamemap_obj = d_current_game_info.getGameMap();
            l_gamemap_obj.showMap(true);
            return;
        }

        if (!l_primary_command.equals("deploy") || !(l_command_details.size() == 1)) throw new GameException(GameMessageConstants.D_COMMAND_INVALID + "\nExample Format: " + GameMessageConstants.D_DEPLOY_COMMAND);

        GameCommandParser.CommandDetails l_command_detail = l_command_details.get(0);
        if (l_command_detail.getHasCommandOption()) throw new GameException(GameMessageConstants.D_COMMAND_NO_OPTION_SUPPORT);

        List<String> l_command_parameters = l_command_detail.getCommandParameters();
        if (l_command_parameters.size() > 2 || !GameCommonUtils.isNumeric(l_command_parameters.get(1))) throw new GameException(GameMessageConstants.D_COMMAND_PARAMETER_INVALID + "\nExample Format: " + GameMessageConstants.D_DEPLOY_COMMAND);

        String l_country_name = l_command_parameters.get(0);
        int l_armies_number = Integer.parseInt(l_command_parameters.get(1));

        if (!p_current_player.checkIfCountryConquered(l_country_name)) throw new GameException(GameMessageConstants.D_COUNTRY_INVALID_FOR_PLAYER);

        if (l_armies_number > p_current_player.getCurrentArmies()) throw new GameException(GameMessageConstants.D_ARMIES_EXCEEDED + "\nAvailable Armies: " + p_current_player.getCurrentArmies());

        p_current_player.d_current_order = new DeployOrder(l_country_name, l_armies_number);
        p_current_player.issue_order();
        p_current_player.setCurrentArmies(p_current_player.getCurrentArmies() - l_armies_number);
        System.out.println(GameMessageConstants.D_DEPLOY + " " + GameMessageConstants.D_ORDER_ISSUED);
    }

    /**
     * Method executes advanced order phase.
     * @param p_command_details command details.
     * @param p_current_player current player object.
     * @throws Exception
     */
    private void executeAdvanceOrder(List<GameCommandParser.CommandDetails> p_command_details, Player p_current_player) throws Exception {

        GameCommandParser.CommandDetails l_command_detail = p_command_details.get(0);

        List<String> l_command_parameters = l_command_detail.getCommandParameters();
        if (l_command_parameters.size() > 3 || !GameCommonUtils.isNumeric(l_command_parameters.get(2))) throw new GameException(GameMessageConstants.D_COMMAND_PARAMETER_INVALID + "\nExample Format: " + GameMessageConstants.D_ADVANCE_COMMAND);

        String l_from_country_name = l_command_parameters.get(0);
        String l_to_country_name = l_command_parameters.get(1);
        int l_armies_number = Integer.parseInt(l_command_parameters.get(2));

        GameMap.Country l_from_country_obj = d_current_game_info.getGameMap().getCountryByName(l_from_country_name);
        GameMap.Country l_to_country_obj = d_current_game_info.getGameMap().getCountryByName(l_to_country_name);

        // checking if source country belongs to player
        if (l_from_country_obj == null) throw new GameException(GameMessageConstants.D_COUNTRY_INVALID_FOR_PLAYER);
        String l_from_country_player = l_from_country_obj.getPlayerName();
        if (l_from_country_player == null || !l_from_country_player.equals(p_current_player.getPlayerName())) throw new GameException(GameMessageConstants.D_COUNTRY_INVALID_FOR_PLAYER);

        // checking if destination country is valid
        if (l_to_country_obj == null) throw new GameException(GameMessageConstants.D_TO_COUNTRY_INVALID);

        // checking if countries are adjacent
        if (!l_from_country_obj.isCountryAdjacent(l_to_country_name)) throw new GameException(GameMessageConstants.D_COUNTRY_NOT_ADJACENT);

        // checking armies count if it exceeded
        if (l_armies_number > l_from_country_obj.getArmyCount()) throw new GameException(GameMessageConstants.D_ATTACK_ARMIES_EXCEEDED + "\nAvailable Armies: " + l_from_country_obj.getArmyCount());

        p_current_player.d_current_order = new AdvanceOrder(l_from_country_obj, l_to_country_obj, l_armies_number);
        p_current_player.issue_order();
        System.out.println(GameMessageConstants.D_ADVANCE + " " + GameMessageConstants.D_ORDER_ISSUED);
    }

    /**
     * Method executes bomb order phase.
     * @param p_command_details command details.
     * @param p_current_player current player object
     * @throws Exception
     */
    private void executeBombOrder(List<GameCommandParser.CommandDetails> p_command_details, Player p_current_player) throws Exception {

        if (!p_current_player.getAvailableCards().containsKey(Card.BOMB) || p_current_player.getAvailableCards().get(Card.BOMB) == 0) throw new GameException(GameMessageConstants.D_CARD_INAVLID + GameMessageConstants.D_BOMB);

        GameCommandParser.CommandDetails l_command_detail = p_command_details.get(0);

        List<String> l_command_parameters = l_command_detail.getCommandParameters();
        if (l_command_parameters.size() != 1) throw new GameException(GameMessageConstants.D_COMMAND_PARAMETER_INVALID + "\nExample Format: " + GameMessageConstants.D_BOMB_COMMAND);

        String l_country_name = l_command_parameters.get(0);

        GameMap.Country l_country_obj = d_current_game_info.getGameMap().getCountryByName(l_country_name);

        // checking if destination country is valid
        if (l_country_obj == null) throw new GameException(GameMessageConstants.D_TO_COUNTRY_INVALID);

        // checking if destination country belongs to different player
        String l_to_country_player = l_country_obj.getPlayerName();
        if (l_to_country_player != null && l_to_country_player.equals(p_current_player.getPlayerName())) throw new GameException(GameMessageConstants.D_BOMB_DESTINATION_INVALID);

        // checking if destination country is adjacent to current any of player country
        boolean l_is_adjacent_country = false;
        for (GameMap.Country l_conquered_country : p_current_player.getConqueredCountries()) {
            if (l_conquered_country.isCountryAdjacent(l_country_name)) {
                l_is_adjacent_country = true;
                break;
            }
        }
        if (!l_is_adjacent_country) throw new GameException(GameMessageConstants.D_DESTINATION_COUNTRY_NOT_ADJACENT);

        p_current_player.d_current_order = new BombOrder(l_country_obj);
        p_current_player.issue_order();
        p_current_player.removeAvailableCard(Card.BOMB);
        System.out.println(GameMessageConstants.D_BOMB + " " + GameMessageConstants.D_ORDER_ISSUED);
    }

    /**
     * Method executes blockade order phase.
     * @param p_command_details command details.
     * @param p_current_player current player object.
     * @throws Exception
     */
    private void executeBlockadeOrder(List<GameCommandParser.CommandDetails> p_command_details, Player p_current_player) throws Exception {

        if (!p_current_player.getAvailableCards().containsKey(Card.BLOCKADE) || p_current_player.getAvailableCards().get(Card.BLOCKADE) == 0) throw new GameException(GameMessageConstants.D_CARD_INAVLID + GameMessageConstants.D_BLOCKADE);

        GameCommandParser.CommandDetails l_command_detail = p_command_details.get(0);

        List<String> l_command_parameters = l_command_detail.getCommandParameters();
        if (l_command_parameters.size() != 1) throw new GameException(GameMessageConstants.D_COMMAND_PARAMETER_INVALID + "\nExample Format: " + GameMessageConstants.D_BLOCKADE_COMMAND);

        String l_country_name = l_command_parameters.get(0);

        GameMap.Country l_country_obj = d_current_game_info.getGameMap().getCountryByName(l_country_name);

        // checking if destination country is valid
        if (l_country_obj == null) throw new GameException(GameMessageConstants.D_TO_COUNTRY_INVALID);

        // checking if destination country belongs to same player
        String l_to_country_player = l_country_obj.getPlayerName();
        if (l_to_country_player == null || !l_to_country_player.equals(p_current_player.getPlayerName())) throw new GameException(GameMessageConstants.D_BLOCKADE_DESTINATION_INVALID);

        p_current_player.d_current_order = new BlockadeOrder(l_country_obj);
        p_current_player.issue_order();
        p_current_player.removeAvailableCard(Card.BLOCKADE);
        System.out.println(GameMessageConstants.D_BLOCKADE + " " + GameMessageConstants.D_ORDER_ISSUED);
    }

    /**
     * Method executes the airlift order phase.
     * @param p_command_details command details.
     * @param p_current_player current player object.
     * @throws Exception
     */
    private void executeAirliftOrder(List<GameCommandParser.CommandDetails> p_command_details, Player p_current_player) throws Exception {

        if (!p_current_player.getAvailableCards().containsKey(Card.AIRLIFT) || p_current_player.getAvailableCards().get(Card.AIRLIFT) == 0) throw new GameException(GameMessageConstants.D_CARD_INAVLID + GameMessageConstants.D_AIRLIFT);

        GameCommandParser.CommandDetails l_command_detail = p_command_details.get(0);

        List<String> l_command_parameters = l_command_detail.getCommandParameters();
        if (l_command_parameters.size() > 3 || !GameCommonUtils.isNumeric(l_command_parameters.get(2))) throw new GameException(GameMessageConstants.D_COMMAND_PARAMETER_INVALID + "\nExample Format: " + GameMessageConstants.D_AIRLIFT_COMMAND);

        String l_from_country_name = l_command_parameters.get(0);
        String l_to_country_name = l_command_parameters.get(1);
        int l_armies_number = Integer.parseInt(l_command_parameters.get(2));

        GameMap.Country l_from_country_obj = d_current_game_info.getGameMap().getCountryByName(l_from_country_name);
        GameMap.Country l_to_country_obj = d_current_game_info.getGameMap().getCountryByName(l_to_country_name);

        // checking if source country belongs to player
        if (l_from_country_obj == null) throw new GameException(GameMessageConstants.D_COUNTRY_INVALID_FOR_PLAYER);
        String l_from_country_player = l_from_country_obj.getPlayerName();
        if (l_from_country_player == null || !l_from_country_player.equals(p_current_player.getPlayerName())) throw new GameException(GameMessageConstants.D_COUNTRY_INVALID_FOR_PLAYER);

        // checking if destination country is valid
        if (l_to_country_obj == null) throw new GameException(GameMessageConstants.D_TO_COUNTRY_INVALID);

        // checking armies count if it exceeded
        if (l_armies_number > l_from_country_obj.getArmyCount()) throw new GameException(GameMessageConstants.D_ATTACK_ARMIES_EXCEEDED + "\nAvailable Armies: " + l_from_country_obj.getArmyCount());

        p_current_player.d_current_order = new AirliftOrder(l_from_country_obj, l_to_country_obj, l_armies_number);
        p_current_player.issue_order();
        p_current_player.removeAvailableCard(Card.AIRLIFT);
        System.out.println(GameMessageConstants.D_AIRLIFT + " " + GameMessageConstants.D_ORDER_ISSUED);
    }

    /**
     * Method executes the diplomacy phase.
     * @param p_command_details command details.
     * @param p_current_player current player object.
     * @throws Exception
     */
    private void executeDiplomacy(List<GameCommandParser.CommandDetails> p_command_details, Player p_current_player) throws Exception {

        if (!p_current_player.getAvailableCards().containsKey(Card.DIPLOMACY) || p_current_player.getAvailableCards().get(Card.DIPLOMACY) == 0) throw new GameException(GameMessageConstants.D_CARD_INAVLID + GameMessageConstants.D_DIPLOMACY);

        GameCommandParser.CommandDetails l_command_detail = p_command_details.get(0);

        List<String> l_command_parameters = l_command_detail.getCommandParameters();
        if (l_command_parameters.size() != 1) throw new GameException(GameMessageConstants.D_COMMAND_PARAMETER_INVALID + "\nExample Format: " + GameMessageConstants.D_DIPLOMACY_COMMAND);

        String l_target_player = l_command_parameters.get(0);

        if (!d_current_game_info.getPlayerList().containsKey(l_target_player)) throw new GameException(GameMessageConstants.D_DIPLOMCY_DESTINATION_INVALID);

        Player l_target_player_obj = d_current_game_info.getPlayerList().get(l_target_player);

        p_current_player.d_current_order = new DiplomacyOrder(l_target_player_obj);
        p_current_player.issue_order();
        p_current_player.removeAvailableCard(Card.DIPLOMACY);
        System.out.println(GameMessageConstants.D_DIPLOMACY + " " + GameMessageConstants.D_ORDER_ISSUED);
    }

    /**
     * {@inheritDoc}
     * Validates and Executes commands for Issue_order_phase.
     * @param p_input_command Input command provided by the player.
     * @param p_current_player Current player issuing the command.
     * @throws Exception If an error occurs during validation or execution.
     */
    public void validateAndExecuteCommands(String p_input_command, Player p_current_player) throws Exception {
        GameCommandParser l_command_parser = new GameCommandParser(p_input_command);
        String l_primary_command = l_command_parser.getPrimaryCommand();
        List<GameCommandParser.CommandDetails> l_command_details = l_command_parser.getParsedCommandDetails();

        if (l_primary_command.equals("showmap")) {
            if (!l_command_details.isEmpty()) throw new GameException(GameMessageConstants.D_COMMAND_INVALID + "\nExample Format: " + GameMessageConstants.D_SHOWMAP);
            GameMap l_gamemap_obj = d_current_game_info.getGameMap();
            l_gamemap_obj.showMap(true);
            return;
        }

        if (!(l_command_details.size() == 1)) throw new GameException(GameMessageConstants.D_COMMAND_INVALID);

        GameCommandParser.CommandDetails l_command_detail = l_command_details.get(0);
        if (l_command_detail.getHasCommandOption()) throw new GameException(GameMessageConstants.D_COMMAND_NO_OPTION_SUPPORT);

        switch (l_primary_command) {
            case "advance": {
                executeAdvanceOrder(l_command_details, p_current_player);
                break;
            }
            case "bomb": {
                executeBombOrder(l_command_details, p_current_player);
                break;
            }
            case "blockade": {
                executeBlockadeOrder(l_command_details, p_current_player);
                break;
            }
            case "airlift": {
                executeAirliftOrder(l_command_details, p_current_player);
                break;
            }
            case "negotiate": {
                executeDiplomacy(l_command_details, p_current_player);
                break;
            }
            default:
                throw new GameException(GameMessageConstants.D_COMMAND_INVALID);
        }
    }

    /**
     * Method deals with processing to the next phase.
     * @return object of execute order phase.
     * @throws Exception
     */
    @Override
    public Phase nextPhase() throws Exception {
        return new ExecuteOrderPhase();
    }

    /**
     * {@inheritDoc}
     *  Allows player to issue orders.
     * @throws Exception If an error occurs.
     */
    @Override
    public void executePhase() throws Exception {
        System.out.printf("%nstart issuing your orders or enter endgame to terminate%n");
        d_current_game_info = GameInformation.getInstance();

        BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));

        LinkedHashMap<String, Player> l_player_list = d_current_game_info.getPlayerList();

        for (Map.Entry<String, Player> l_player : l_player_list.entrySet()) {
            Player l_player_obj = l_player.getValue();
            int l_current_armies = l_player_obj.getCurrentArmies();
            System.out.println("Start by issuing deploy order");

            String l_input_command;
            // Executing deploy order
            while (l_current_armies > 0) {
                System.out.println("Player: "+ l_player.getKey() + " turn");
                System.out.println("Remaining Armies: " + l_current_armies);
                l_player_obj.d_current_order = null;
                try {
                    System.out.println();
                    l_input_command = l_reader.readLine();
                    if (l_input_command.equals("endgame")) {
                        d_current_game_info.setCurrentPhase(new EndGamePhase());
                        return;
                    }
                    executeDeployOrder(l_input_command, l_player_obj);
                    l_current_armies = l_player_obj.getCurrentArmies();

                } catch (GameException e) {
                    System.out.println(e.getMessage());
                } catch (Exception e) {
                    throw e;
                }
            }

            // Executing other order types
            System.out.println("start issuing other orders");
            System.out.println("Player: "+ l_player.getKey() + " turn");
            if (!l_player_obj.printAvailableCards().isEmpty()) System.out.println("Available Cards -> " + l_player_obj.printAvailableCards());
            l_input_command = l_reader.readLine();
            while (!l_input_command.equals("commit") && !l_input_command.equals("endgame")) {
                try {
                    validateAndExecuteCommands(l_input_command, l_player_obj);
                } catch (GameException e) {
                    System.out.println(e.getMessage());
                } catch (Exception e) {
                    throw e;
                }

                if (!l_player_obj.printAvailableCards().isEmpty()) System.out.println("Available Cards -> " + l_player_obj.printAvailableCards());
                l_input_command = l_reader.readLine();
                if (l_input_command.equals("endgame")) {
                    d_current_game_info.setCurrentPhase(new EndGamePhase());
                    return;
                }
            }
        }

        d_current_game_info.setCurrentPhase(this.nextPhase());
    }
}