package gameplay;

import mapparser.GameMap;

public class BombOrder extends Order {

    private GameMap.Country d_destination_country;

    private GameInformation d_current_game_info;

    public BombOrder(GameMap.Country p_destination_country) {
        this.d_destination_country = p_destination_country;
    }

    public GameMap.Country getDestinationCountry() {
        return this.d_destination_country;
    }

    @Override
    public void execute(Player p_player_obj) throws Exception {

        d_current_game_info = GameInformation.getInstance();

        if (p_player_obj.getConqueredCountries().contains(d_destination_country)) return;

        String l_destination_player = d_destination_country.getPlayerName();
        Player l_destination_player_obj = d_current_game_info.getPlayerList().get(l_destination_player);

        if (p_player_obj != null && l_destination_player_obj != null && (p_player_obj.checkDiplomacyRelation(l_destination_player_obj) || l_destination_player_obj.checkDiplomacyRelation(p_player_obj))) return;

        int l_destination_armies = d_destination_country.getArmyCount();
        d_destination_country.setArmyCount(l_destination_armies / 2);

    }
}