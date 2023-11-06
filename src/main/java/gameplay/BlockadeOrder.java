package gameplay;

import mapparser.GameMap;

public class BlockadeOrder extends Order {

    private GameMap.Country d_destination_country;
    
    public BlockadeOrder(GameMap.Country p_destination_country){
        this.d_destination_country = p_destination_country;
    }
    
    @Override
    public void execute(Player p_player_obj) throws Exception {
        if (!p_player_obj.getConqueredCountries().contains(d_destination_country)) return;

        int l_destination_armies = d_destination_country.getArmyCount();
        d_destination_country.setArmyCount(l_destination_armies * 3);
        p_player_obj.getConqueredCountries().remove(d_destination_country);
        d_destination_country.setPlayerName(null);
    }
    
}
