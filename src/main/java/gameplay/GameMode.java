package gameplay;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameMode implements Serializable {

    public enum Mode {
        D_SINGLE_GAME_MODE,

        D_TOURNAMENT_MODE
    }

    public class GameDetails implements Serializable {

        private String d_current_map;

        private int d_game_number;

        private String d_game_winner;

        private int d_turns_played;

        public String getCurrentMap() {
            return d_current_map;
        }

        public void setCurrentMap(String p_current_map) {
            this.d_current_map = p_current_map;
        }

        public int getGameNumber() {
            return d_game_number;
        }

        public void setGameNumber(int p_game_number) {
            this.d_game_number = p_game_number;
        }

        public String getGameWinner() {
            return d_game_winner;
        }

        public void setGameWinner(String p_game_winner) {
            this.d_game_winner = p_game_winner;
        }

        public void setTurnsPlayed(int p_turns_played) {
            this.d_turns_played = p_turns_played;
        }

        public int getTurnsPlayed() {
            return this.d_turns_played;
        }
    }

    private Mode d_current_game_mode;

    private ArrayList<String> d_game_maps = new ArrayList<>();

    private ArrayList<String> d_game_strategies = new ArrayList<>();

    private int d_number_of_games;

    private int d_number_of_turns;

    private int d_total_number_of_games;

    private GameDetails d_current_game_details;

    private int d_current_game_number;

    private int d_current_map_number;

    private int d_games_played;

    private List<GameDetails> d_game_details = new ArrayList<>();

    public void setGameMode(Mode p_current_game_mode) {
        this.d_current_game_mode = p_current_game_mode;
    }

    public void setGameMap(String p_game_map) {
        this.d_game_maps.add(p_game_map);
    }

    public void setGameStrategy(String p_game_strategy) {
        this.d_game_strategies.add(p_game_strategy);
    }

    public void setNumberOfGames(int p_number_of_games) {
        this.d_number_of_games = p_number_of_games;
    }

    public void setNumberOfTurns(int p_number_of_turns) {
        this.d_number_of_turns = p_number_of_turns;
    }

    public void setGameDetail(GameDetails p_game_detail) {
        this.d_game_details.add(p_game_detail);
    }

    public void setGamesPlayed(int p_games_played) {
        this.d_games_played = p_games_played;
    }

    public void setCurrentGameNumber(int p_current_game_number) {
        this.d_current_game_number = p_current_game_number;
    }

    public void setCurrentMapNumber(int p_current_map_number) {
        this.d_current_map_number = p_current_map_number;
    }

    public void setTotalNumberOfGames(int p_total_number_of_games) {
        this.d_total_number_of_games = p_total_number_of_games;
    }

    public void setCurrentGameDetails(GameDetails p_current_game_details) {
        this.d_current_game_details = p_current_game_details;
    }

    public int getCurrentGameNumber() {
        return this.d_current_game_number;
    }

    public int getCurrentMapNumber() {
        return this.d_current_map_number;
    }

    public int getNumberOfGames() {
        return this.d_number_of_games;
    }

    public int getNumberOfTurns() {
        return this.d_number_of_turns;
    }


    public Mode getGameMode() {
        return this.d_current_game_mode;
    }

    public List<GameDetails> getGameDetails() {
        return this.d_game_details;
    }

    public List<String> getStrategies() {
        return this.d_game_strategies;
    }

    public List<String> getGameMaps() {
        return this.d_game_maps;
    }

    public int getTotalNumberOfGames() {
        return this.d_total_number_of_games;
    }

    public GameDetails getCurrentGameDetails() {
        return this.d_current_game_details;
    }

}
