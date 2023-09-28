package gameplay;

import constants.GameMessageConstants;
import gameutils.GameCommandParser;
import gameutils.GameCommonUtils;
import gameutils.GameException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class IssueOrderPhase extends GamePhase {

    public static final String D_PHASE_NAME = "ISSUE_ORDER_PHASE";

    private String d_next_phase = "";

    private GameInformation d_current_game_info;


    private void executeDeployOrder(List<GameCommandParser.CommandDetails> p_command_details, Player p_current_player) throws Exception {
        if (p_command_details.isEmpty()) {
            throw new GameException(GameMessageConstants.D_COMMAND_INCOMPLETE + "\nExample Format: " + GameMessageConstants.D_DEPLOY_COMMAND);
        }

        if (p_command_details.size() > 1) {
            throw new GameException(GameMessageConstants.D_COMMAND_INVALID + "\nExample Format: " + GameMessageConstants.D_DEPLOY_COMMAND);
        }

        GameCommandParser.CommandDetails l_command_detail = p_command_details.get(0);
        if (l_command_detail.getHasCommandOption()) {
            throw new GameException(GameMessageConstants.D_COMMAND_NO_OPTION_SUPPORT + "\nExample Format: " + GameMessageConstants.D_DEPLOY_COMMAND);
        }

        List<String> l_command_parameters = l_command_detail.getCommandParameters();
        if (l_command_parameters.size() > 2) {
            throw new GameException(GameMessageConstants.D_COMMAND_PARAMETERS_EXCEEDS + "\nExample Format: " + GameMessageConstants.D_DEPLOY_COMMAND);
        }

        if (!GameCommonUtils.isNumeric(l_command_parameters.get(0)) || !GameCommonUtils.isNumeric(l_command_parameters.get(1))) {
            throw new GameException(GameMessageConstants.D_COMMAND_PARAMETER_INVALID + "\nExample Format: " + GameMessageConstants.D_DEPLOY_COMMAND);
        }

        int l_country_id = Integer.parseInt(l_command_parameters.get(0));
        int l_armies_number = Integer.parseInt(l_command_parameters.get(1));

        if (l_armies_number > p_current_player.getCurrentArmies()) {
            throw new GameException(GameMessageConstants.D_ARMIES_EXCEEDED + "\nAvailable Armies: " + p_current_player.getCurrentArmies());
        }

        Order l_order = new Order(Order.D_DEPLOY_ORDER);
        l_order.setCountryId(l_country_id);
        l_order.setArmiesNumber(l_armies_number);

        p_current_player.setCurrentArmies(p_current_player.getCurrentArmies() - l_armies_number);
        System.out.println(GameMessageConstants.D_ORDER_ISSUED);
    }

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
            default:
                throw new GameException(GameMessageConstants.D_COMMAND_INVALID);
        }
    }

    @Override
    public void executePhase(GameInformation p_game_information) throws Exception {
        System.out.println("start issuing your orders");
        d_current_game_info = p_game_information;

        LinkedHashMap<String, Player> l_player_list = p_game_information.getPlayerList();

        for (Map.Entry<String, Player> l_player : l_player_list.entrySet()) {
            System.out.println("Player "+ l_player.getKey() + "turn");
            Player l_player_obj = l_player.getValue();
            int l_current_armies = l_player_obj.getCurrentArmies();

            while (l_current_armies > 0) {
                System.out.println("Remaining Armies: " + l_current_armies);
                try {
                    System.out.println();
                    BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));
                    String l_input_command = l_reader.readLine();
                    if (l_input_command.equals("endgame")) {
                        d_next_phase = "END_GAME";
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
    }
}
