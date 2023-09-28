package gameplay;

import java.util.List;

public class Player {

    private String d_player_name;

    private int d_current_armies;

    private List<Order> d_orders_list;

    public Player(String l_player_name) {
        this.d_player_name = l_player_name;
    }

    public String getPlayerName() {
        return this.d_player_name;
    }

    public void setCurrentArmies(int p_current_armies) {
        this.d_current_armies = p_current_armies;
    }

    public int getCurrentArmies() {
        return this.d_current_armies;
    }

}
