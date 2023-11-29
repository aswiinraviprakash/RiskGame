package gameplay;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import common.Phase;
import java.io.Serializable;

import constants.GameConstants;
import mapparser.GameMap;

/**
 *  This class is used to set current phase, current map and other information.
 */
public class GameInformation implements Serializable {

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

    /**
     * Contains last session of player
     */
    private Player d_last_session_player;

    /**
     * Contains last session phase
     */
    private Phase d_last_session_phase;

    /**
     * Contains game mode
     */
    private GameMode d_game_mode;

    /**
     * Contains gme state
     */
    private GameConstants.GameState d_game_state;

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

    /**
     * Setter to last session player
     * @param p_player_obj
     */
    public void setLastSessionPlayer(Player p_player_obj) {
        this.d_last_session_player = p_player_obj;
    }

    /**
     * Getter for last session player
     * @return last session player
     */
    public Player getLastSessionPlayer() {
        return this.d_last_session_player;
    }

    /**
     * Setter for last session phase
     * @param l_last_session_phase last session player
     */
    public void setLastSessionPhase(Phase l_last_session_phase) {
        this.d_last_session_phase = l_last_session_phase;
    }

    /**
     * Getter for last session phase
     * @return last session phase
     */
    public Phase getLastSessionPhase() {
        return this.d_last_session_phase;
    }

    /**
     * Setter for set game mode
     * @param p_game_mode game mode
     */
    public void setGameMode (GameMode p_game_mode) {
        this.d_game_mode = p_game_mode;
    }

    /**
     * Getter for game mode
     * @return
     */
    public GameMode getGameMode() {
        return this.d_game_mode;
    }

    /**
     * Setter for state
     * @param p_game_state
     */
    public void setGameState(GameConstants.GameState p_game_state) {
        this.d_game_state = p_game_state;
    }

    /**
     * Getter for game state
     * @return game state
     */
    public GameConstants.GameState getGameState() {
        return this.d_game_state;
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

    /**
     * Method to load game information instance
     * @param p_game_info_instance
     */
    public static void loadGameInfoInstance(GameInformation p_game_info_instance) {
        d_game_info_instance = p_game_info_instance;
    }

}
