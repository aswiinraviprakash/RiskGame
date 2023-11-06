package gameplay;

import java.util.List;

import mapparser.GameMap;

public class BombOrder extends Order {

    private GameMap.Country d_destination_country;

    public BombOrder(GameMap.Country p_destination_country) {
        this.d_destination_country = p_destination_country;
    }

    @Override
    public void execute(Player p_player_obj) throws Exception {
        int l_destination_armies = d_destination_country.getArmyCount();
        d_destination_country.setArmyCount(l_destination_armies / 2);
    }
}