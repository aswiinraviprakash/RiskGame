package gameplay.strategy;

import common.LogEntryBuffer;
import constants.GameMessageConstants;
import gameplay.order.AdvanceOrder;
import gameplay.order.AirliftOrder;
import gameplay.Card;
import gameplay.order.DeployOrder;
import gameplay.GameInformation;
import gameplay.order.Order;
import gameplay.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import mapparser.GameMap;
import mapparser.GameMap.Country;

public class AggressivePlayerStrategy implements PlayerStrategy, Serializable {

    /**
     * member to store logger instance
     */
    private static LogEntryBuffer d_logger = LogEntryBuffer.getInstance();

    /**
     * Contains current game information.
     */
    private GameInformation d_current_game_info;

    /**
     * This method is used to Deploy armies to the player's strongest country
     * @param p_player_obj The player
     * @param p_strongest_country The strongest country
     * @param p_player_orders The list of Player's orders
     * @throws Exception Throws Exception
     */
    public void executeMoveArmies(Player p_player_obj, Country p_strongest_country, List<Order> p_player_orders) throws Exception {

        GameMap l_game_map = d_current_game_info.getGameMap();
        List<Country> l_player_countries = p_player_obj.getConqueredCountries();

        Country l_next_strongest_country = null;
        int l_next_strongest_army_max = Integer.MIN_VALUE;

        for (Country l_player_country : l_player_countries) {
            if (l_player_country.getCountryID() == p_strongest_country.getCountryID()) {
                continue;
            }

            int l_country_army_count = l_player_country.getArmyCount();
            if (l_country_army_count <= l_next_strongest_army_max) {
                continue;
            }

            if (!l_game_map.getBorders().containsKey(l_player_country.getCountryID())) {
                continue;
            }
            List<Integer> l_country_borders = l_game_map.getBorders().get(l_player_country.getCountryID());

            boolean l_has_neighbor_player = false;
            for (Integer l_country_border : l_country_borders) {

                if (l_country_border == l_player_country.getCountryID()) {
                    continue;
                }

                Country l_neighbor_country = l_game_map.getCountryById(l_country_border);
                String l_neighbor_player_name = l_neighbor_country.getPlayerName();
                if (l_neighbor_country != null && l_neighbor_player_name != null && l_neighbor_player_name.equals(p_player_obj.getPlayerName())) {
                    if (l_neighbor_country.getArmyCount() > 2) {
                        l_has_neighbor_player = true;
                        break;
                    }
                }
            }

            if (l_has_neighbor_player) {
                l_next_strongest_country = l_player_country;
                l_next_strongest_army_max = l_country_army_count;
            }
        }

        if (l_next_strongest_country == null) {
            return;
        }

        List<Integer> l_strong_country_borders = l_game_map.getBorders().get(l_next_strongest_country.getCountryID());
        for (Integer l_country_border : l_strong_country_borders) {

            if (l_country_border == l_next_strongest_country.getCountryID()) {
                continue;
            }

            Country l_neighbor_country = l_game_map.getCountryById(l_country_border);
            String l_neighbor_player_name = l_neighbor_country.getPlayerName();

            if (l_neighbor_country != null && l_neighbor_player_name != null && l_neighbor_player_name.equals(p_player_obj.getPlayerName())) {
                if (l_neighbor_country.getArmyCount() > 2) {
                    Order l_current_order = new AdvanceOrder(l_neighbor_country, l_next_strongest_country, l_neighbor_country.getArmyCount() / 2);
                    p_player_orders.add(l_current_order);
                    System.out.println(GameMessageConstants.D_ADVANCE + " " + GameMessageConstants.D_ORDER_ISSUED);
                    d_logger.addLogger(GameMessageConstants.D_ADVANCE + " " + GameMessageConstants.D_ORDER_ISSUED);
                }
            }
        }
    }

    /**
     * This method is used to initiate the attack
     * @param p_current_player The current player
     * @param p_strongest_country The strongsest country
     * @param p_player_orders The List of player orders
     * @throws Exception Throws Exception 
     */
    public void executeAttackOrder(Player p_current_player, Country p_strongest_country, List<Order> p_player_orders) throws Exception {

        GameMap l_game_map = d_current_game_info.getGameMap();

        List<Country> l_countries = l_game_map.getCountryObjects();
        boolean l_has_airlift_Card = p_current_player.getAvailableCards().containsKey(Card.AIRLIFT) &&
                                 p_current_player.getAvailableCards().get(Card.AIRLIFT) >= 1;

        int l_strongest_country_armies = p_strongest_country.getArmyCount();

        // find the stronger opponent country
        Country l_strong_opponent_country = null;
        int l_opponent_army_max = Integer.MIN_VALUE;
        boolean l_is_country_adjacent = false;

        for (Country l_country : l_countries) {

            boolean l_country_adjacent = false;
            // checking for opponent country
            String l_country_player_name = l_country.getPlayerName();
            if (l_country_player_name != null && l_country_player_name.equals(p_current_player.getPlayerName())) {
                continue;
            }

            // checking armies count
            if (l_country.getArmyCount() >= l_strongest_country_armies) {
                continue;
            }

            l_country_adjacent = p_strongest_country.isCountryAdjacent(l_country.getCountryName());

            if (!l_has_airlift_Card && !l_country_adjacent) {
                continue;
            }

            if (l_country.getArmyCount() > l_opponent_army_max) {
                l_strong_opponent_country = l_country;
                l_opponent_army_max = l_country.getArmyCount();
                l_is_country_adjacent = l_country_adjacent;
            }
        }

        if (l_strong_opponent_country == null) {
            return;
        }

        if (l_is_country_adjacent) {
            Order l_current_order = new AdvanceOrder(p_strongest_country, l_strong_opponent_country, l_opponent_army_max + 1);
            p_player_orders.add(l_current_order);
            System.out.println(GameMessageConstants.D_ADVANCE + " " + GameMessageConstants.D_ORDER_ISSUED);
            d_logger.addLogger(GameMessageConstants.D_ADVANCE + " " + GameMessageConstants.D_ORDER_ISSUED);

        } else if (l_has_airlift_Card) {
            Order l_current_order = new AirliftOrder(p_strongest_country, l_strong_opponent_country, l_opponent_army_max + 1);
            p_player_orders.add(l_current_order);
            p_current_player.removeAvailableCard(Card.AIRLIFT);
            System.out.println(GameMessageConstants.D_AIRLIFT + " " + GameMessageConstants.D_ORDER_ISSUED);
            d_logger.addLogger(GameMessageConstants.D_AIRLIFT + " " + GameMessageConstants.D_ORDER_ISSUED);

        }
    }

    /**
     * This method is used to execute the Deploy Order
     * @param p_current_player The current Order
     * @param p_strongest_country The strongest countries
     * @param p_player_orders The List of Player orders
     * @throws Exception Throws Exception 
     */
    public void executeDeployOrder(Player p_current_player, Country p_strongest_country, List<Order> p_player_orders) throws Exception {

        String l_strongest_country_name = p_strongest_country.getCountryName();
        int l_current_armies = p_current_player.getCurrentArmies();

        Order l_current_order = new DeployOrder(l_strongest_country_name, l_current_armies);
        p_player_orders.add(l_current_order);
        p_current_player.setCurrentArmies(0);

        System.out.println(GameMessageConstants.D_DEPLOY + " " + GameMessageConstants.D_ORDER_ISSUED);
        d_logger.addLogger(GameMessageConstants.D_DEPLOY + " " + GameMessageConstants.D_ORDER_ISSUED);

    }

    public List<Order> createOrders(Player p_player_obj) throws Exception {

        System.out.println("Player: "+ p_player_obj.getPlayerName() + " turn");

        d_current_game_info = GameInformation.getInstance();

        List<Order> l_player_orders = new ArrayList<>();
        List<Country> l_counquered_countries = p_player_obj.getConqueredCountries();

        if (l_counquered_countries.isEmpty()) {
            return l_player_orders;
        }

        // finding country with maximum armies
        int l_max_arimies = Integer.MIN_VALUE;
        Country l_strongest_country = null;

        for (Country l_country : l_counquered_countries) {

            if (l_country.getArmyCount() > l_max_arimies) {
                l_max_arimies = l_country.getArmyCount();
                l_strongest_country = l_country;
            }

        }

        // executing deploy order on strongest country
        System.out.println("Issuing deploy order...");
        executeDeployOrder(p_player_obj, l_strongest_country, l_player_orders);

        // executing attack on strongest opponent country
        System.out.println("Issuing attack order...");
        executeAttackOrder(p_player_obj, l_strongest_country, l_player_orders);

        // executing moves armies on next strongest country
        System.out.println("Issuing move armies between country...");
        executeMoveArmies(p_player_obj, l_strongest_country, l_player_orders);

        return l_player_orders;
    }

}
