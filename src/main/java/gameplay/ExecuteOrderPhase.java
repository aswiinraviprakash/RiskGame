package gameplay;

import Utils.LogEntryBuffer;
import Utils.LogEntryWriter;
import Utils.Observable;
import Utils.Observer;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;


/**
 * Responsible for executing the orders issued by the players during the game.
 */
public class ExecuteOrderPhase extends GamePhase {
    public static final String D_PHASE_NAME = "EXECUTE_ORDER_PHASE";

    private Utils.LogEntryBuffer LogEntryBuffer;
    private LogEntryBuffer d_Logger = LogEntryBuffer;


    /**
     * Contains the next phase.
     */
    private String d_next_phase = ReinforcementPhase.D_PHASE_NAME;

    /**
     * Contains number of armies.
     */
    private GameInformation d_current_game_info;

    /**
     * Iterates over the player list and executes each player's orders one by one.
     * @param p_game_information The object containing relevant data.
     * @throws Exception Throws exception if an error occurs.
     */
    @Override
    public void executePhase(GameInformation p_game_information) throws Exception {
        System.out.printf("%nExecuting orders issued....%n");
        d_Logger.log("%nExecuting orders issued....%n");

        d_current_game_info = p_game_information;

        LinkedHashMap<String, Player> l_player_list = p_game_information.getPlayerList();

        for (Map.Entry<String, Player> l_player : l_player_list.entrySet()) {
            try {
                Player l_player_obj = l_player.getValue();
                Order l_next_order = l_player_obj.next_order();

                while (l_next_order != null) {
                    l_next_order.execute(l_player_obj);
                    l_next_order = l_player_obj.next_order();
                }

            } catch (Exception e) {
                throw e;
            }
        }

        d_current_game_info.setCurrentPhase(this.d_next_phase);
    }
}
