package gameplay;

import java.util.LinkedHashMap;
import mapparser.GameMap;

/**
 *  This class is used to set current phase, current map and other information.
 */
public class GameInformation {

    private String d_current_phase;

    private LinkedHashMap<String, Player> d_player_list = new LinkedHashMap<>();

    private mapparser.GameMap d_current_game_map;

    /**
     * Sets current phase of the game.
     * @param p_current_phase Current Phase
     */

    public void setCurrentPhase(String p_current_phase) {
        this.d_current_phase = p_current_phase;
    }

    /**
     * Sets list of players in the game.
     * @param p_player_list List of players.
     */
    public void setPlayerList(LinkedHashMap<String, Player> p_player_list) {
        this.d_player_list = p_player_list;
    }

    /**
     *  Sets the current game map.
     * @param p_current_game_map Current game map.
     */
    public void setCurrenGameMap(GameMap p_current_game_map) {
        this.d_current_game_map = p_current_game_map;
    }

    /**
     * Get Current phase of the game.
     * @return Current game phase.
     */
    public String getCurrentPhase() {
        return this.d_current_phase;
    }

    /**
     * Gets the List of players.
     * @return List of players.
     */
    public LinkedHashMap<String, Player> getPlayerList() {
        return this.d_player_list;
    }

    /**
     * Gets the current map.
     * @return Current map.
     */
    public mapparser.GameMap getGameMap() {
        return this.d_current_game_map;
    }

}
