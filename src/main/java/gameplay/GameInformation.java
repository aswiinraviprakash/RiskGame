package gameplay;

import java.util.LinkedHashMap;
import mapparser.Map;

public class GameInformation {

    private String d_current_phase;

    private LinkedHashMap<String, Player> d_player_list = new LinkedHashMap<>();

    private mapparser.Map d_current_game_map;

    public void setCurrentPhase(String p_current_phase) {
        this.d_current_phase = p_current_phase;
    }

    public void setPlayerList(LinkedHashMap<String, Player> p_player_list) {
        this.d_player_list = p_player_list;
    }

    public void setCurrenGameMap(Map p_current_game_map) {
        this.d_current_game_map = p_current_game_map;
    }

    public String getCurrentPhase() {
        return this.d_current_phase;
    }

    public LinkedHashMap<String, Player> getPlayerList() {
        return this.d_player_list;
    }

    public mapparser.Map getGameMap() {
        return this.d_current_game_map;
    }

}
