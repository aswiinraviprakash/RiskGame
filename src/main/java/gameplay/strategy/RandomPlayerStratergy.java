package gameplay.strategy;

import common.LogEntryBuffer;
import constants.GameMessageConstants;
import gameplay.order.AdvanceOrder;
import gameplay.order.AirliftOrder;
import gameplay.order.BlockadeOrder;
import gameplay.order.BombOrder;
import gameplay.Card;
import gameplay.order.DeployOrder;
import gameplay.GameInformation;
import gameplay.order.Order;
import gameplay.Player;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import mapparser.GameMap;
import mapparser.GameMap.Country;

/**
 * This class represents the behaviour of Random Player Strategy
 */
public class RandomPlayerStratergy implements PlayerStrategy {

    /**
     * member to store logger instance
     */
    private static LogEntryBuffer d_logger = LogEntryBuffer.getInstance();

    /**
     * Contains current game information.
     */
    private GameInformation d_current_game_info;
    
    /**
     * Executes the Advance Order
     * @param p_current_player The current Player
     * @param p_player_orders The list of Player's orders
     * @throws Exception Throws Exception if the method fails
     */
    public void executeMoveArmies(Player p_current_player, List<Order> p_player_orders) throws Exception {
        Random l_random = new Random();
        Country l_from_country_obj;
        Country l_to_country_obj;
        int l_armies_number;

        GameMap l_game_map = d_current_game_info.getGameMap();

        if (p_current_player.d_conquered_countries.isEmpty()) {
            return;
        }

        //select from country
        int l_country_index = l_random.nextInt(p_current_player.d_conquered_countries.size());
        l_from_country_obj = p_current_player.d_conquered_countries.get(l_country_index);
        if (l_from_country_obj == null || l_from_country_obj.getArmyCount() <= 0) {
            return;
        }

        if (!l_game_map.getBorders().containsKey(l_from_country_obj.getCountryID())) {
            return;
        }

        List<Integer> l_from_country_borders = l_game_map.getBorders().get(l_from_country_obj.getCountryID());
        if (l_from_country_borders.isEmpty()) {
            return;
        }

        // select to country
        List<Country> l_countries_adj = new ArrayList<Country>();

        for (Integer l_from_country_border : l_from_country_borders) {
            if (l_from_country_border == l_from_country_obj.getCountryID()) {
                continue;
            }

            Country l_border_country = l_game_map.getCountryById(l_from_country_border);
            if (l_border_country == null) {
                continue;
            }

            String l_border_player = l_border_country.getPlayerName();
            if (l_border_player == null || !l_border_player.equals(l_from_country_obj.getPlayerName())) {
                continue;
            }

            l_countries_adj.add(l_border_country);
        }

        if (l_countries_adj.isEmpty()) {
            return;
        }

        l_country_index = l_random.nextInt(l_countries_adj.size());
        l_to_country_obj = l_countries_adj.get(l_country_index);
        if (l_to_country_obj == null) {
            return;
        }

        //generate army count
        l_armies_number = l_random.nextInt(l_from_country_obj.getArmyCount());

        if (l_armies_number <= 0 && l_armies_number > l_from_country_obj.getArmyCount()) {
            return;
        }
        
        Order l_current_order = new AdvanceOrder(l_from_country_obj, l_to_country_obj, l_armies_number);
        p_player_orders.add(l_current_order);
        System.out.println(GameMessageConstants.D_ADVANCE + " " + GameMessageConstants.D_ORDER_ISSUED);
        d_logger.addLogger(GameMessageConstants.D_ADVANCE + " " + GameMessageConstants.D_ORDER_ISSUED);
    }

    /**
     * Executes the Blockade Order
     * @param p_current_player The Current player
     * @param p_player_orders The player's orders
     * @throws Exception Throws Exception if the Order Fails.
     */
    public void executeBlockade(Player p_current_player, List<Order> p_player_orders) throws Exception {
        Random l_random = new Random();
        Country l_country_blockade;

        List<Country> l_conq_countries = p_current_player.d_conquered_countries;
        if (l_conq_countries.isEmpty()) {
            return;
        }

        l_country_blockade = l_conq_countries.get(l_random.nextInt(l_conq_countries.size()));

        if (l_country_blockade == null) {
           return;
        }

        Order l_current_order = new BlockadeOrder(l_country_blockade);
        p_player_orders.add(l_current_order);
        p_current_player.removeAvailableCard(Card.BLOCKADE);
        System.out.println(GameMessageConstants.D_BLOCKADE + " " + GameMessageConstants.D_ORDER_ISSUED);
        d_logger.addLogger(GameMessageConstants.D_BLOCKADE + " " + GameMessageConstants.D_ORDER_ISSUED);
    }

    /**
     * Executes the Bomb order
     * @param p_current_player The current player
     * @param p_player_orders The player's orders
     * @throws Exception Throws exception if the method fails.
     */
    public void executeBomb(Player p_current_player, List<Order> p_player_orders) throws Exception {
        Random l_random = new Random();
        Country l_country_bomb;

        GameMap l_game_map = d_current_game_info.getGameMap();
        List<Country> l_counquered_countries = p_current_player.getConqueredCountries();
        if (l_counquered_countries.isEmpty()) {
            return;
        }

        // find all the adjacent countries -- make sure he cant bomb his own country
        List<Country> l_countries = l_game_map.d_countries;
        List<Country> l_neighbour_countries = new ArrayList<>();

        for (Country l_country : l_countries) {

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

            l_neighbour_countries.add(l_country);
        }

        if (l_neighbour_countries.isEmpty()) {
            return;
        }

        l_country_bomb = l_neighbour_countries.get(l_random.nextInt(l_neighbour_countries.size()));
        if (l_country_bomb == null) {
            return;
        }

        Order l_current_order = new BombOrder(l_country_bomb);
        p_player_orders.add(l_current_order);
        p_current_player.removeAvailableCard(Card.BOMB);
        System.out.println(GameMessageConstants.D_BOMB + " " + GameMessageConstants.D_ORDER_ISSUED);
        d_logger.addLogger(GameMessageConstants.D_BOMB + " " + GameMessageConstants.D_ORDER_ISSUED);
    }

    /**
     * Executes the Airlift Order
     * @param p_current_player The current player
     * @param p_player_orders The player's orders
     * @throws Exception Throws Exception if the method Fails.
     */
    public void executeAirlift(Player p_current_player, List<Order> p_player_orders) throws Exception {
        Random l_random = new Random();
        Country l_from_country_obj;
        Country l_to_country_obj;
        int l_armies_number;

        GameMap l_game_map = d_current_game_info.getGameMap();

        if (p_current_player.d_conquered_countries.isEmpty()) {
            return;
        }

        // selecting from country
        int l_country_index = l_random.nextInt(p_current_player.d_conquered_countries.size());
        l_from_country_obj = p_current_player.d_conquered_countries.get(l_country_index);
        if (l_from_country_obj == null || l_from_country_obj.getArmyCount() <= 0) {
            return;
        }

        // selecting to country
        List<Country> l_countries = l_game_map.getCountryObjects();
        List<Country> l_countries_opp = new ArrayList<Country>();

        for (Country l_country : l_countries) {

            if (l_country.getCountryID() == l_from_country_obj.getCountryID()) {
                continue;
            }

            String l_country_player = l_country.getPlayerName();
            if (l_country_player != null && l_country_player.equals(l_from_country_obj.getPlayerName())) {
                continue;
            }

            l_countries_opp.add(l_country);
        }

        if (l_countries_opp.isEmpty()) {
            return;
        }

        l_to_country_obj = l_countries_opp.get(l_random.nextInt(l_countries_opp.size()));
        if (l_to_country_obj == null) {
            return;
        }

        // number of armies moved
        l_armies_number = l_random.nextInt(l_from_country_obj.getArmyCount());
        if (l_armies_number <= 0 && l_armies_number > l_from_country_obj.getArmyCount()) {
            return;
        }

        Order l_current_order = new AirliftOrder(l_from_country_obj, l_to_country_obj, l_armies_number);
        p_player_orders.add(l_current_order);
        p_current_player.removeAvailableCard(Card.AIRLIFT);
        System.out.println(GameMessageConstants.D_AIRLIFT + " " + GameMessageConstants.D_ORDER_ISSUED);
        d_logger.addLogger(GameMessageConstants.D_AIRLIFT + " " + GameMessageConstants.D_ORDER_ISSUED);
    }

    /**
     * Executes the Advance Order.
     * @param p_current_player The current player
     * @param p_player_orders The player's orders
     * @throws Exception If the method fails.
     */
    public void executeAdvanceOrder(Player p_current_player, List<Order> p_player_orders) throws Exception {
        Random l_random = new Random();
        Country l_from_country_obj;
        Country l_to_country_obj;
        int l_armies_number;

        GameMap l_game_map = d_current_game_info.getGameMap();

        if (p_current_player.d_conquered_countries.isEmpty()) {
            return;
        }

        //select from country
        int l_country_index = l_random.nextInt(p_current_player.d_conquered_countries.size());
        l_from_country_obj = p_current_player.d_conquered_countries.get(l_country_index);
        if (l_from_country_obj == null || l_from_country_obj.getArmyCount() <= 0) {
            return;
        }

        if (!l_game_map.getBorders().containsKey(l_from_country_obj.getCountryID())) {
            return;
        }

        List<Integer> l_from_country_borders = l_game_map.getBorders().get(l_from_country_obj.getCountryID());
        if (l_from_country_borders.isEmpty()) {
            return;
        }

        //select to country
        List<Country> l_countries_opp_adj = new ArrayList<Country>();

        for (Integer l_from_country_border : l_from_country_borders) {
            if (l_from_country_border == l_from_country_obj.getCountryID()) {
                continue;
            }

            Country l_border_country = l_game_map.getCountryById(l_from_country_border);
            if (l_border_country == null) {
                continue;
            }

            String l_border_player = l_border_country.getPlayerName();
            if (l_border_player != null && l_border_player.equals(l_from_country_obj.getPlayerName())) {
                continue;
            }

            l_countries_opp_adj.add(l_border_country);
        }

        if (l_countries_opp_adj.isEmpty()) {
            return;
        }

        l_country_index = l_random.nextInt(l_countries_opp_adj.size());
        l_to_country_obj = l_countries_opp_adj.get(l_country_index);
        if (l_to_country_obj == null) {
            return;
        }

        //generate army count
        l_armies_number = l_random.nextInt(l_from_country_obj.getArmyCount());

        if (l_armies_number <= 0 && l_armies_number > l_from_country_obj.getArmyCount()) {
            return;
        }

        Order l_current_order = new AdvanceOrder(l_from_country_obj, l_to_country_obj, l_armies_number);
        p_player_orders.add(l_current_order);
        System.out.println(GameMessageConstants.D_ADVANCE + " " + GameMessageConstants.D_ORDER_ISSUED);
        d_logger.addLogger(GameMessageConstants.D_ADVANCE + " " + GameMessageConstants.D_ORDER_ISSUED);
    }

    /**
     * Executes the Attack Order
     * @param p_current_player The current Player
     * @param p_player_orders The list of player's orders
     * @throws Exception Throws Exception if the order fails
     */
    public void executeAttackOrder(Player p_current_player, List<Order> p_player_orders) throws Exception {

        Random l_random = new Random();
        int l_max_turns = -1;

        do {

            l_max_turns = l_max_turns + 1;

            if (l_max_turns % 2 == 0) {
                executeAdvanceOrder(p_current_player, p_player_orders);
            } else {

                List<Card> l_random_card_select = new ArrayList<Card>();
                HashMap<Card, Integer> l_cards = p_current_player.getAvailableCards();
                System.out.println(l_cards.size());
                if (l_cards.isEmpty()) {
                    continue;
                }

                for (Map.Entry<Card, Integer> entry : l_cards.entrySet()) {
                    Card l_key = entry.getKey();
                    Integer l_value = entry.getValue();

                    if (l_value >= 1) {
                        l_random_card_select.add(l_key);
                    }
                }

                if (l_random_card_select.isEmpty()) {
                    continue;
                }

                Card l_select_card = l_random_card_select.get(l_random.nextInt(l_random_card_select.size()));
                if (l_select_card == null) {
                    continue;
                }

                switch (l_select_card) {
                    case AIRLIFT:
                        executeAirlift(p_current_player, p_player_orders);
                        break;
                    case BOMB:
                        executeBomb(p_current_player, p_player_orders);
                        break;
                    case BLOCKADE:
                        executeBlockade(p_current_player, p_player_orders);
                        break;
                    default:
                        break;
                }
            }

        } while (l_max_turns < 5);

    }

    /**
     * Executes the Deploy Order
     * @param p_current_player The current player
     * @param p_player_orders The list of player's orders
     * @throws Exception Throws Exception if the method fails
     */
    public void executeDeployOrder(Player p_current_player, List<Order> p_player_orders) throws Exception {

        int l_army_deploy = p_current_player.getCurrentArmies();
        if (l_army_deploy <= 0) {
            return;
        }
        int l_random_army;
        Random l_random = new Random();

        do {
            //choose a random country to deploy armies in
            List<Country> l_countries = p_current_player.d_conquered_countries;
            if (l_countries.isEmpty()) {
                return;
            }

            int l_country_index = l_random.nextInt(l_countries.size());
            Country l_deploy_country = l_countries.get(l_country_index);
            if (l_deploy_country == null) {
                continue;
            }

            l_random_army = l_random.nextInt(l_army_deploy) + 1;
            if (l_random_army > l_army_deploy) {
                continue;
            }

            Order l_current_order = new DeployOrder(l_deploy_country.getCountryName(), l_random_army);
            p_player_orders.add(l_current_order);

            l_army_deploy = l_army_deploy - l_random_army;
            p_current_player.setCurrentArmies(l_army_deploy);

            System.out.println(GameMessageConstants.D_DEPLOY + " " + GameMessageConstants.D_ORDER_ISSUED);
            d_logger.addLogger(GameMessageConstants.D_DEPLOY + " " + GameMessageConstants.D_ORDER_ISSUED);

        } while (l_army_deploy > 0 && p_current_player.getCurrentArmies() > 0);

    }

    public List<Order> createOrders(Player p_player_obj) throws Exception {

        System.out.println("Player: "+ p_player_obj.getPlayerName() + " turn");

        d_current_game_info = GameInformation.getInstance();
        List<Order> l_player_orders = new ArrayList<>();

        if (p_player_obj.getConqueredCountries().isEmpty()) {
            return l_player_orders;
        }

        // executing deploy order on random countries
        System.out.println("Issuing deploy order...");
        executeDeployOrder(p_player_obj, l_player_orders);

        // executing attack on random opponent countries
        System.out.println("Issuing attack order...");
        executeAttackOrder(p_player_obj, l_player_orders);

        // executing move armies on random
        System.out.println("Issuing move armies between country...");
        executeMoveArmies(p_player_obj, l_player_orders);

        return l_player_orders;
    }

}
