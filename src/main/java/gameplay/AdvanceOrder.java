package gameplay;

import gameutils.GameException;
import mapparser.GameMap;

public class AdvanceOrder extends Order {

    private GameMap.Country d_source_country;

    private GameMap.Country d_destination_country;

    private int d_armies;

    private GameInformation d_current_game_info;

    public AdvanceOrder(GameMap.Country p_source_country, GameMap.Country p_destination_country, int p_armies) {
        this.d_source_country = p_source_country;
        this.d_destination_country = p_destination_country;
        this.d_armies = p_armies;
    }

    public GameMap.Country getDestinationCountry() {
        return this.d_destination_country;
    }

    public void attackDestinationCountry(Player p_current_player, Player p_destination_player) {

        if (p_current_player != null && p_destination_player != null && (p_current_player.checkDiplomacyRelation(p_destination_player) || p_destination_player.checkDiplomacyRelation(p_current_player))) return;

        int l_destination_armies = d_destination_country.getArmyCount();
        int l_source_armies = d_source_country.getArmyCount();

        if (d_armies > l_source_armies) return;

        if (d_armies > l_destination_armies) {

            d_source_country.setArmyCount(l_source_armies - d_armies);
            d_destination_country.setArmyCount(d_armies - l_destination_armies);
            if (p_destination_player != null) {
                p_destination_player.getConqueredCountries().remove(d_destination_country);
            }
            d_destination_country.setPlayerName(p_current_player.getPlayerName());
            p_current_player.getConqueredCountries().add(d_destination_country);

            Card l_random_card = Card.generateRandomCard();
            p_current_player.addAvailableCard(l_random_card);

        } else if (d_armies <= l_destination_armies) {
            d_source_country.setArmyCount(l_source_armies - d_armies);
            d_destination_country.setArmyCount(l_destination_armies - d_armies);
        }
    }

    public void movesArmiesToDestinationCountry() {
        int l_destination_armies = d_destination_country.getArmyCount();
        int l_source_armies = d_source_country.getArmyCount();
        d_source_country.setArmyCount(l_source_armies - d_armies);
        d_destination_country.setArmyCount(l_destination_armies + d_armies);
    }

    @Override
    public void execute(Player p_player_obj) throws GameException {

        d_current_game_info = GameInformation.getInstance();

        if (!p_player_obj.getConqueredCountries().contains(d_source_country)) return;

        String l_player_name = p_player_obj.getPlayerName();
        boolean isAttackMode = false;

        Player l_destination_player = null;
        String l_destination_player_name = d_destination_country.getPlayerName();
        if (l_destination_player_name == null) {
            isAttackMode = true;
        } else if (!(l_destination_player_name.equals(l_player_name))) {
            isAttackMode = true;
            l_destination_player = d_current_game_info.getPlayerList().get(l_destination_player_name);
        }

        if (isAttackMode) {
            attackDestinationCountry(p_player_obj, l_destination_player);
        } else {
            movesArmiesToDestinationCountry();
        }

    }

}
