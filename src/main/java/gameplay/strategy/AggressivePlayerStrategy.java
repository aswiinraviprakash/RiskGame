package gameplay.strategy;

import common.LogEntryBuffer;
import constants.GameMessageConstants;
import gameplay.AdvanceOrder;
import gameplay.AirliftOrder;
import gameplay.Card;
import gameplay.DeployOrder;
import gameplay.GameInformation;
import gameplay.Order;
import gameplay.Player;
import gameutils.GameException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import mapparser.GameMap;
import mapparser.GameMap.Country;

/**
 *
 * @author USER
 */
public class AggressivePlayerStrategy implements PlayerStrategy {

    /**
     * member to store logger instance
     */
    private static LogEntryBuffer d_logger = LogEntryBuffer.getInstance();

    /**
     * Contains current game information.
     */
    private GameInformation d_current_game_info;

    public List<Order> createOrders(Player p_player_obj) throws Exception {
        d_current_game_info = GameInformation.getInstance();

        List<Order> l_player_orders = new ArrayList<>();

        //deploy order
        System.out.println("Issuing deploy order");

        executeDeployOrder(p_player_obj, l_player_orders);

        //issue order - attack
        System.out.println("Issuing attack orders");

        executeAttackOrder(p_player_obj, l_player_orders);

        //issue order - moving armies
        System.out.println("Moving armies");
        
        executeMoveArmies(p_player_obj, l_player_orders);

        return l_player_orders;
    }
    
    public void executeMoveArmies(Player p_player_obj, List<Order> p_player_orders){
        
    }

    public void executeAttackOrder(Player p_current_player, List<Order> p_player_orders) throws Exception {

        d_current_game_info = GameInformation.getInstance();
        GameMap l_map = d_current_game_info.getGameMap();

        List<Country> l_conq_countries = p_current_player.getConqueredCountries();

        //finding strongest country
        Country l_strong_country = null;

        int max_army_count = Integer.MIN_VALUE;

        for (int l_index = 0; l_index < l_conq_countries.size(); l_index++) {
            if (l_conq_countries.get(l_index).getArmyCount() > max_army_count) {
                max_army_count = l_conq_countries.get(l_index).getArmyCount();
                l_strong_country = l_conq_countries.get(l_index);
            }
        }

        //find the stronger opponent country (adj and non adj)
        Country l_strong_opponent_country = null;
        int l_opponent_army_max = Integer.MIN_VALUE;

        List<Country> l_countries = l_map.getCountryObjects();

        for (int l_index = 0; l_index < l_countries.size(); l_index++) {
            Country l_neighbour = l_map.getCountryById(l_countries.get(l_index).getCountryID());
            if (l_map.getCountryById(l_countries.get(l_index).getCountryID()).getPlayerName().
                    compareTo(p_current_player.getPlayerName()) != 0) {
                if (l_opponent_army_max < l_neighbour.getArmyCount()) {
                    l_opponent_army_max = l_neighbour.getArmyCount();
                    l_strong_opponent_country = l_neighbour;
                }

            }
        }

        // finding the strongest opponent neighbouring country
        int l_opponent_army_max_adj = Integer.MIN_VALUE;
        Country l_strong_opponent_country_adj = null;
        int l_armies_number = -1;

        LinkedHashMap<Integer, List<Integer>> l_borders_list = new LinkedHashMap<Integer, List<Integer>>();

        List<Integer> l_neighbours = l_borders_list.get(l_strong_country.getCountryID());

        for (int l_index = 0; l_index < l_neighbours.size(); l_index++) {
            if (l_map.getCountryById(l_neighbours.get(l_index)).getPlayerName().compareTo(p_current_player.getPlayerName()) != 0) {
                Country l_neighbour_adj = l_map.getCountryById(l_neighbours.get(l_index));
                if (l_opponent_army_max_adj < l_neighbour_adj.getArmyCount()) {
                    l_opponent_army_max_adj = l_neighbour_adj.getArmyCount();
                    l_strong_opponent_country_adj = l_neighbour_adj;
                }
            }
        }

        if (l_strong_opponent_country_adj == null) {
            throw new GameException(GameMessageConstants.D_INTERNAL_ERROR);
        }

        if (l_strong_opponent_country == null) {
            throw new GameException(GameMessageConstants.D_INTERNAL_ERROR);
        }

        //check if player has airlift card
        boolean l_has_airlift = p_current_player.getAvailableCards().containsKey(Card.AIRLIFT)
                && p_current_player.getAvailableCards().get(Card.AIRLIFT) >= 1;

        if (l_strong_opponent_country_adj.equals(l_strong_opponent_country)) {
            //don't use airlift - advance order
            Order l_current_order = new AdvanceOrder(l_strong_country, l_strong_opponent_country_adj, l_armies_number);
            p_player_orders.add(l_current_order);
            System.out.println(GameMessageConstants.D_ADVANCE + " " + GameMessageConstants.D_ORDER_ISSUED);
            d_logger.addLogger(GameMessageConstants.D_ADVANCE + " " + GameMessageConstants.D_ORDER_ISSUED);
        } else if (l_strong_opponent_country.getArmyCount() > l_strong_opponent_country_adj.getArmyCount() && l_has_airlift) {
            //use airlift on non adj country
            Order l_current_order = new AirliftOrder(l_strong_country, l_strong_opponent_country, l_armies_number);
            p_player_orders.add(l_current_order);
            p_current_player.removeAvailableCard(Card.AIRLIFT);
            System.out.println(GameMessageConstants.D_AIRLIFT + " " + GameMessageConstants.D_ORDER_ISSUED);
            d_logger.addLogger(GameMessageConstants.D_AIRLIFT + " " + GameMessageConstants.D_ORDER_ISSUED);
        } else if (l_strong_opponent_country.getArmyCount() > l_strong_opponent_country_adj.getArmyCount() && !l_has_airlift) {
            //adv order on adj country
            Order l_current_order = new AdvanceOrder(l_strong_country, l_strong_opponent_country_adj, l_armies_number);
            p_player_orders.add(l_current_order);
            System.out.println(GameMessageConstants.D_ADVANCE + " " + GameMessageConstants.D_ORDER_ISSUED);
            d_logger.addLogger(GameMessageConstants.D_ADVANCE + " " + GameMessageConstants.D_ORDER_ISSUED);
        } else if (l_strong_opponent_country.getArmyCount() <= l_strong_opponent_country_adj.getArmyCount()) {
            //adv order on adj country
            Order l_current_order = new AdvanceOrder(l_strong_country, l_strong_opponent_country_adj, l_armies_number);
            p_player_orders.add(l_current_order);
            System.out.println(GameMessageConstants.D_ADVANCE + " " + GameMessageConstants.D_ORDER_ISSUED);
            d_logger.addLogger(GameMessageConstants.D_ADVANCE + " " + GameMessageConstants.D_ORDER_ISSUED);
        }

    }

    public void executeDeployOrder(Player p_current_player, List<Order> p_player_orders) throws Exception {

        String l_country_name = "";
        int l_armies_number = p_current_player.getCurrentArmies();

        //finding country to deploy armies too
        List<Country> l_conq_countries = p_current_player.getConqueredCountries();

        //finding the strongest country
        Country l_strong_country = null;

        int max_army_count = Integer.MIN_VALUE;

        for (int l_index = 0; l_index < l_conq_countries.size(); l_index++) {
            if (l_conq_countries.get(l_index).getArmyCount() > max_army_count) {
                max_army_count = l_conq_countries.get(l_index).getArmyCount();
                l_strong_country = l_conq_countries.get(l_index);
            }
        }

        if (l_strong_country == null) {
            throw new GameException(GameMessageConstants.D_INTERNAL_ERROR);
        }

        l_country_name = l_strong_country.getCountryName();

        Order l_current_order = new DeployOrder(l_country_name, l_armies_number);
        p_player_orders.add(l_current_order);
        p_current_player.setCurrentArmies(0);
        System.out.println(GameMessageConstants.D_DEPLOY + " " + GameMessageConstants.D_ORDER_ISSUED);
        d_logger.addLogger(GameMessageConstants.D_DEPLOY + " " + GameMessageConstants.D_ORDER_ISSUED);

    }

}
