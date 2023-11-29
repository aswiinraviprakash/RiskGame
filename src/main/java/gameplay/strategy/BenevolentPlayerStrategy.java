package gameplay.strategy;

import common.LogEntryBuffer;
import constants.GameMessageConstants;
import gameplay.order.AdvanceOrder;
import gameplay.order.DeployOrder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import gameplay.Player;
import gameplay.order.Order;
import gameplay.GameInformation;
import mapparser.GameMap;
import mapparser.GameMap.Country;

public class BenevolentPlayerStrategy implements PlayerStrategy, Serializable {

    /**
     * member to store logger instance
     */
    private static LogEntryBuffer d_logger = LogEntryBuffer.getInstance();

    /**
     * Contains current game information.
     */
    private GameInformation d_current_game_info;

    /**
     * This method finds the weakest countries each turn and performs deploy and advance order to them
     * @param p_player_obj The player object
     * @param p_weakest_country Weakest country
     * @param p_player_orders The list of orders
     * @throws Exception Throws Exception
     */
    public void executeMoveArmies(Player p_player_obj, Country p_weakest_country, List<Order> p_player_orders) throws Exception {

        GameMap l_game_map = d_current_game_info.getGameMap();
        List<Country> l_player_countries = p_player_obj.getConqueredCountries();

        Country l_next_weakest_country = null;
        int l_next_weakest_army_min = Integer.MAX_VALUE;

        for (Country l_player_country : l_player_countries) {
            if (l_player_country.getCountryID() == p_weakest_country.getCountryID()) {
                continue;
            }

            int l_country_army_count = l_player_country.getArmyCount();
            if (l_country_army_count >= l_next_weakest_army_min) {
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
                    if (l_neighbor_country.getArmyCount() > 2 && (l_neighbor_country.getArmyCount() - (l_neighbor_country.getArmyCount() / 2)) > l_country_army_count) {
                        l_has_neighbor_player = true;
                        break;
                    }
                }
            }

            if (l_has_neighbor_player) {
                l_next_weakest_country = l_player_country;
                l_next_weakest_army_min = l_country_army_count;
            }
        }

        if (l_next_weakest_country == null) {
            return;
        }

        List<Integer> l_weak_country_borders = l_game_map.getBorders().get(l_next_weakest_country.getCountryID());
        for (Integer l_country_border : l_weak_country_borders) {

            if (l_country_border == l_next_weakest_country.getCountryID()) {
                continue;
            }

            Country l_neighbor_country = l_game_map.getCountryById(l_country_border);
            String l_neighbor_player_name = l_neighbor_country.getPlayerName();

            if (l_neighbor_country != null && l_neighbor_player_name != null && l_neighbor_player_name.equals(p_player_obj.getPlayerName())) {
                if (l_neighbor_country.getArmyCount() > 2 && (l_neighbor_country.getArmyCount() - (l_neighbor_country.getArmyCount() / 2)) > l_next_weakest_country.getArmyCount()) {
                    Order l_current_order = new AdvanceOrder(l_neighbor_country, l_next_weakest_country, l_neighbor_country.getArmyCount() / 2);
                    p_player_orders.add(l_current_order);
                    System.out.println(GameMessageConstants.D_ADVANCE + " " + GameMessageConstants.D_ORDER_ISSUED);
                    d_logger.addLogger(GameMessageConstants.D_ADVANCE + " " + GameMessageConstants.D_ORDER_ISSUED);
                }
            }
        }
    }

    /**
     * Executes the deploy order
     * @param p_current_player The current player
     * @param p_weakest_country The weakest country
     * @param p_player_orders The list of player orders
     * @throws Exception Throws Exception error
     */
    public void executeDeployOrder(Player p_current_player, Country p_weakest_country, List<Order> p_player_orders) throws Exception {

        String l_weakest_country_name = p_weakest_country.getCountryName();
        int l_current_armies = p_current_player.getCurrentArmies();

        Order l_current_order = new DeployOrder(l_weakest_country_name, l_current_armies);
        p_player_orders.add(l_current_order);
        p_current_player.setCurrentArmies(0);

        System.out.println(GameMessageConstants.D_DEPLOY + " " + GameMessageConstants.D_ORDER_ISSUED);
        d_logger.addLogger(GameMessageConstants.D_DEPLOY + " " + GameMessageConstants.D_ORDER_ISSUED);

    }

    @Override
    public List<Order> createOrders(Player p_player_obj) throws Exception {

        System.out.println("Player: "+ p_player_obj.getPlayerName() + " turn");

        d_current_game_info = GameInformation.getInstance();

        List<Order> l_player_orders = new ArrayList<>();
        List<Country> l_counquered_countries = p_player_obj.getConqueredCountries();

        if (l_counquered_countries.isEmpty()) {
            return l_player_orders;
        }

        // finding country with maximum armies
        int l_min_arimies = Integer.MAX_VALUE;
        Country l_weakest_country = null;

        for (Country l_country : l_counquered_countries) {

            if (l_country.getArmyCount() < l_min_arimies) {
                l_min_arimies = l_country.getArmyCount();
                l_weakest_country = l_country;
            }

        }

        // executing deploy order on weakest country
        System.out.println("Issuing deploy order...");
        executeDeployOrder(p_player_obj, l_weakest_country, l_player_orders);

        // executing move armies on next weakest country
        System.out.println("Issuing move armies between country...");
        executeMoveArmies(p_player_obj, l_weakest_country, l_player_orders);

        return l_player_orders;
    }
}