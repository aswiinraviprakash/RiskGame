package gameplay.strategy;

import common.LogEntryBuffer;
import gameplay.GameInformation;
import gameplay.Player;
import gameplay.order.Order;
import mapparser.GameMap;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * This class Represents the Cheater Behaviour Strategy
 */
public class CheaterPlayerStrategy implements PlayerStrategy {

    /**
     * member to store logger instance
     */
    private static LogEntryBuffer d_logger = LogEntryBuffer.getInstance();

    /**
     * Contains current game information.
     */
    private GameInformation d_current_game_info;

    /**
     * This method is used to Conquer any neighboring territories to that of the cheater countries
     * @param p_current_player Current player
     */
    public void conquerEnemyCountries(Player p_current_player) {

        GameMap l_game_map = d_current_game_info.getGameMap();
        LinkedHashMap<String, Player> l_players = d_current_game_info.getPlayerList();

        List<GameMap.Country> l_counquered_countries = p_current_player.getConqueredCountries();
        if (l_counquered_countries.isEmpty()) {
            return;
        }

        List<GameMap.Country> l_countries = l_game_map.d_countries;

        for (GameMap.Country l_country : l_countries) {

            if (l_country == null) {
                continue;
            }

            String l_player_name = l_country.getPlayerName();
            if (l_player_name != null && l_player_name.equals(p_current_player.getPlayerName())) {
                continue;
            }

            boolean l_is_adjacent_country = false;
            for (GameMap.Country l_conquered_country : l_counquered_countries) {
                if (l_conquered_country.isCountryAdjacent(l_country.getCountryName())) {
                    l_is_adjacent_country = true;
                    break;
                }
            }

            if (!l_is_adjacent_country) {
                continue;
            }

            if (l_player_name == null) {
                l_country.setPlayerName(p_current_player.getPlayerName());
                p_current_player.getConqueredCountries().add(l_country);
            } else if (l_players.containsKey(l_player_name)) {
                Player l_enemy_player = l_players.get(l_player_name);
                l_enemy_player.getConqueredCountries().remove(l_country);
                l_country.setPlayerName(p_current_player.getPlayerName());
                p_current_player.getConqueredCountries().add(l_country);
            }
        }
    }

    /**
     * This method doubles the armies of the captured countries
     * @param p_current_player The current player
     */
    public void doubleArmiesForCountries(Player p_current_player) {
        GameMap l_game_map = d_current_game_info.getGameMap();

        List<GameMap.Country> l_counquered_countries = p_current_player.getConqueredCountries();
        if (l_counquered_countries.isEmpty()) {
            return;
        }

        for (GameMap.Country l_conquered_country : l_counquered_countries) {
            if (l_conquered_country == null) {
                continue;
            }

            if (!l_game_map.getBorders().containsKey(l_conquered_country.getCountryID())) {
                continue;
            }

            List<Integer> l_country_borders = l_game_map.getBorders().get(l_conquered_country.getCountryID());

            boolean l_has_neighbor_player = false;
            for (Integer l_country_border : l_country_borders) {

                if (l_country_border == l_conquered_country.getCountryID()) {
                    continue;
                }

                GameMap.Country l_neighbor_country = l_game_map.getCountryById(l_country_border);
                if (l_neighbor_country == null) {
                    continue;
                }

                String l_neighbor_player_name = l_neighbor_country.getPlayerName();
                if (l_neighbor_player_name != null && !l_neighbor_player_name.equals(p_current_player.getPlayerName())) {
                    l_has_neighbor_player = true;
                    break;
                }
            }

            if (l_has_neighbor_player) {
                int l_country_armies = l_conquered_country.getArmyCount();
                l_conquered_country.setArmyCount(l_country_armies * 2);
            }
        }
    }

    @Override
    public List<Order> createOrders(Player p_player_obj) throws Exception {

        System.out.println("Player: "+ p_player_obj.getPlayerName() + " turn");

        d_current_game_info = GameInformation.getInstance();

        if (p_player_obj.getConqueredCountries().isEmpty()) {
            return new ArrayList<Order>();
        }

        // conquering all enemy countries
        conquerEnemyCountries(p_player_obj);

        // doubling armies on countries with enemy neighbors
        doubleArmiesForCountries(p_player_obj);

        return new ArrayList<Order>();
    }
}
