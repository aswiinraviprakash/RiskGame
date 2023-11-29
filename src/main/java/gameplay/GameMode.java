package gameplay;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for gmae mode
 */
public class GameMode implements Serializable {

    /**
     * Enum for mode
     */
    public enum Mode {
        
        /**
         * Enum for the Game mode
         */
        D_SINGLE_GAME_MODE,

        /**
         * Enum for the Tournament Mode
         */
        D_TOURNAMENT_MODE
    }

    /**
     * Class for game details
     */
    public class GameDetails implements Serializable {

        /**
         * Contains current map
         */
        private String d_current_map;

        /**
         * Contains game number
         */
        private int d_game_number;

        /**
         * Contains game winner
         */
        private String d_game_winner;

        /**
         * Contains turns played
         */
        private int d_turns_played;

        /**
         * Getter for current map
         * @return Returns the current map
         */
        public String getCurrentMap() {
            return d_current_map;
        }

        /**
         * Setter for current map
         * @param p_current_map current map
         */
        public void setCurrentMap(String p_current_map) {
            this.d_current_map = p_current_map;
        }

        /**
         * Getter for game number
         * @return Returns the Game's number
         */
        public int getGameNumber() {
            return d_game_number;
        }

        /**
         * Setter for game number
         * @param p_game_number game number
         */
        public void setGameNumber(int p_game_number) {
            this.d_game_number = p_game_number;
        }

        /**
         * Getter for game winner
         * @return Returns the Winner of the game
         */
        public String getGameWinner() {
            return d_game_winner;
        }

        /**
         * Setter for game winner
         * @param p_game_winner game winner
         */
        public void setGameWinner(String p_game_winner) {
            this.d_game_winner = p_game_winner;
        }

        /**
         * Setter for turns played
         * @param p_turns_played turns played
         */
        public void setTurnsPlayed(int p_turns_played) {
            this.d_turns_played = p_turns_played;
        }

        /**
         * Getter for turns played
         * @return turns played
         */
        public int getTurnsPlayed() {
            return this.d_turns_played;
        }
    }

    /**
     * Current game mode
     */
    private Mode d_current_game_mode;

    /**
     * Arraylist of game maps
     */
    private ArrayList<String> d_game_maps = new ArrayList<>();

    /**
     * Arraylist for game strategies
     */
    private ArrayList<String> d_game_strategies = new ArrayList<>();

    /**
     * Contains number of game
     */
    private int d_number_of_games;

    /**
     * Contains number of turns
     */
    private int d_number_of_turns;

    /**
     * Contains total number of games
     */
    private int d_total_number_of_games;

    /**
     * Contains game details
     */
    private GameDetails d_current_game_details;

    /**
     * Contains current game number
     */
    private int d_current_game_number;

    /**
     * Contains map number
     * */
    private int d_current_map_number;

    /**
     * Games played
     */
    private int d_games_played;

    /**
     * List for game details
     */
    private List<GameDetails> d_game_details = new ArrayList<>();

    /**
     * Setter for game mode
     * @param p_current_game_mode The parameter for the game mode
     */
    public void setGameMode(Mode p_current_game_mode) {
        this.d_current_game_mode = p_current_game_mode;
    }

    /**
     * Setter for game map
     * @param p_game_map game map
     */
    public void setGameMap(String p_game_map) {
        this.d_game_maps.add(p_game_map);
    }

    /**
     * Setter for game strategy
     * @param p_game_strategy game strategy
     */
    public void setGameStrategy(String p_game_strategy) {
        this.d_game_strategies.add(p_game_strategy);
    }

    /**
     * Setter for number of games
     * @param p_number_of_games number of games
     */
    public void setNumberOfGames(int p_number_of_games) {
        this.d_number_of_games = p_number_of_games;
    }

    /**
     * Setter for number of turns
     * @param p_number_of_turns number of turns
     */
    public void setNumberOfTurns(int p_number_of_turns) {
        this.d_number_of_turns = p_number_of_turns;
    }

    /**
     * Setter for game details
     * @param p_game_detail game details
     */
    public void setGameDetail(GameDetails p_game_detail) {
        this.d_game_details.add(p_game_detail);
    }

    /**
     * Setter for games played
     * @param p_games_played games played
     */
    public void setGamesPlayed(int p_games_played) {
        this.d_games_played = p_games_played;
    }

    /**
     * Setter for current game number
     * @param p_current_game_number current game number
     */
    public void setCurrentGameNumber(int p_current_game_number) {
        this.d_current_game_number = p_current_game_number;
    }

    /**
     * Setter for current map number
     * @param p_current_map_number current map number
     */
    public void setCurrentMapNumber(int p_current_map_number) {
        this.d_current_map_number = p_current_map_number;
    }

    /**
     * Setter for total number of games
     * @param p_total_number_of_games total number of games
     */
    public void setTotalNumberOfGames(int p_total_number_of_games) {
        this.d_total_number_of_games = p_total_number_of_games;
    }

    /**
     * Setter for current game details
     * @param p_current_game_details game details
     */
    public void setCurrentGameDetails(GameDetails p_current_game_details) {
        this.d_current_game_details = p_current_game_details;
    }

    /**
     * Getter for current game number
     * @return game number
     */
    public int getCurrentGameNumber() {
        return this.d_current_game_number;
    }

    /**
     * Getter for current map number
     * @return current map number
     */
    public int getCurrentMapNumber() {
        return this.d_current_map_number;
    }

    /**
     * Getter for number of games
     * @return Returns the number of Games
     */
    public int getNumberOfGames() {
        return this.d_number_of_games;
    }

    /**
     * Getter for number of turns
     * @return number of turns
     */
    public int getNumberOfTurns() {
        return this.d_number_of_turns;
    }

    /**
     * Getter for game mode
     * @return current game mode
     */
    public Mode getGameMode() {
        return this.d_current_game_mode;
    }

    /**
     * Getter for current game details
     * @return game details
     */
    public List<GameDetails> getGameDetails() {
        return this.d_game_details;
    }

    /**
     * Getter for game strategies
     * @return game strategies
     */
    public List<String> getStrategies() {
        return this.d_game_strategies;
    }

    /**
     * Getter for game maps
     * @return game maps
     */
    public List<String> getGameMaps() {
        return this.d_game_maps;
    }

    /**
     * Getter for total number of games
     * @return total number of games
     */
    public int getTotalNumberOfGames() {
        return this.d_total_number_of_games;
    }

    /**
     * Getter for current game details
     * @return current game details
     */
    public GameDetails getCurrentGameDetails() {
        return this.d_current_game_details;
    }

}
