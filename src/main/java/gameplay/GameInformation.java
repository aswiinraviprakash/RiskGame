package gameplay;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import common.Phase;
import java.io.Serializable;
import mapparser.GameMap;

/**
 *  This class is used to set current phase, current map and other information.
 */
public class GameInformation implements Serializable{

    /**
     * member to store gameinformation instance
     */
    private static GameInformation d_game_info_instance = null;

    /**
     * Contains the current phase.
     */
    private Phase d_current_phase;

    /**
     * Contains Player list.
     */
    private LinkedHashMap<String, Player> d_player_list = new LinkedHashMap<>();

    /**
     * member to store players issued with a card
     */
    private ArrayList<String> d_card_issued_players = null;

    /**
     * Contains game map.
     */
    private mapparser.GameMap d_current_game_map;

    private Player d_last_session_player;

    /**
     * private constructor for creating gameinformation object
     */
    private GameInformation() {}

    /**
     * Method returns the information of the game at an instance.
     * @return information of the game instance.
     */
    public static GameInformation getInstance() {
        if (d_game_info_instance == null) d_game_info_instance = new GameInformation();
        return d_game_info_instance;
    }

    /**
     * Sets current phase of the game.
     * @param p_current_phase Current Phase
     */
    public void setCurrentPhase(Phase p_current_phase) {
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
    public Phase getCurrentPhase() {
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

    public void setLastSessionPlayer(Player p_player_obj) {
        this.d_last_session_player = p_player_obj;
    }

    public Player getLastSessionPlayer() {
        return this.d_last_session_player;
    }

    /**
     * Method to set cards to the player
     * @param p_player_name Parameter to assign the name of the player
     */
    public void setCardIssuedPlayer(String p_player_name) {
        if (this.d_card_issued_players == null) this.d_card_issued_players = new ArrayList<>();
        d_card_issued_players.add(p_player_name);
    }

    /**
     * Checks if the player is issued a card
     * @param p_player_name Parameter to assign the name of the player
     * @return Returns True or False
     */
    public boolean isPlayerIssuedCard(String p_player_name) {
        if (this.d_card_issued_players == null) return false;
        return d_card_issued_players.contains(p_player_name);
    }

    /**
     * This method removes the card set to the players
     */
    public void resetCardIssued() {
        this.d_card_issued_players = null;
    }

}
