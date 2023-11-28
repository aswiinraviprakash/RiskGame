package gameplay;

import common.LogEntryBuffer;
import common.Phase;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Class for players to issue orders.
 */
public class IssueOrderPhase extends Phase {

    /**
     * member to store logger instance
     */
    private static LogEntryBuffer d_logger = LogEntryBuffer.getInstance();

    /**
     * Contains current game information.
     */
    private GameInformation d_current_game_info;

    /**
     * {@inheritDoc}
     * Method deals with processing to the next phase.
     * @return object of execute order phase.
     * @throws Exception If there is an error in the execution or validation.
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
        d_current_game_info = GameInformation.getInstance();

        LinkedHashMap<String, Player> l_player_list = d_current_game_info.getPlayerList();
        Player l_last_session_player = d_current_game_info.getLastSessionPlayer();
        d_current_game_info.setLastSessionPlayer(null);

        for (Map.Entry<String, Player> l_player : l_player_list.entrySet()) {
            Player l_player_obj = l_player.getValue();
            if (l_last_session_player != null && !l_player_obj.getPlayerName().equals(l_last_session_player.getPlayerName())) {
                continue;
            }

            // issuing player orders
            l_player_obj.issue_order();
            l_last_session_player = null;

            if (d_current_game_info.getCurrentPhase() instanceof EndGamePhase) {
                return;
            }
        }

        d_current_game_info.setCurrentPhase(this.nextPhase());
    }
}