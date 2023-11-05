package gameplay;

import common.Phase;
import constants.GameConstants;

import java.util.LinkedHashMap;
import java.util.List;

import mapparser.GameMap;

/**
 * This class is responsible for assigning reinforcement armies to players based on the number of countries they have conquered.
 */
public class ReinforcementPhase extends Phase {

    /**
     * Contains current game information
     */
    private GameInformation d_current_game_info;

    /**
     * This method assigns armioes to players.
     * @param p_player_obj The player object to assign armies to.
     * @throws Exception Exception If there is an error while assigning armies.
     */
    private void assignArmiesToPlayers(Player p_player_obj) throws Exception {

        int l_armies_value = GameConstants.D_DEFAULT_ARMY_COUNT;

        int l_country_armies = Math.floorDiv(p_player_obj.getConqueredCountries().size(), 3);
        if (l_country_armies < 3) l_country_armies = 3;

        l_armies_value = l_armies_value + l_country_armies;

        GameMap l_gamemap_obj = d_current_game_info.getGameMap();
        List<GameMap.Continent> l_continents_list = l_gamemap_obj.getContinentObjects();

        for (GameMap.Continent l_continent_obj : l_continents_list) {
            boolean l_continent_conquered = true;
            List<Integer> l_available_countries = l_continent_obj.getCountryIDList();

            for (Integer l_country_id : l_available_countries) {
                if (!p_player_obj.checkIfCountryConquered(l_country_id)) l_continent_conquered = false;
            }

            if (l_continent_conquered) l_armies_value = l_armies_value + l_continent_obj.getSpecialNumber();
        }

        p_player_obj.setCurrentArmies(l_armies_value);
    }

    @Override
    public Phase nextPhase() throws Exception {
        return new IssueOrderPhase();
    }

    /**
     *{@inheritDoc}
     * Responsible for assigning reinforcement armies to players based on the number of countries they have conquered.
     * @throws Exception If an exception occurs, it is rethrown.
     */
    @Override
    public void executePhase() throws Exception {
        System.out.printf("%nAssigning reinforcements....%n");
        d_current_game_info = GameInformation.getInstance();

        LinkedHashMap<String, Player> l_player_list = d_current_game_info.getPlayerList();

        for (java.util.Map.Entry<String, Player> l_player : l_player_list.entrySet()) {
            try {
                Player l_player_obj = l_player.getValue();
                assignArmiesToPlayers(l_player_obj);
            } catch (Exception e) {
                throw e;
            }
        }

        d_current_game_info.setCurrentPhase(this.nextPhase());
    }

}
