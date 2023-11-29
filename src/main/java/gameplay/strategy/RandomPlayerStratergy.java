package gameplay.strategy;

import common.LogEntryBuffer;
import constants.GameMessageConstants;
import gameplay.order.AdvanceOrder;
import gameplay.order.AirliftOrder;
import gameplay.order.BlockadeOrder;
import gameplay.order.BombOrder;
import gameplay.Card;
import gameplay.order.DeployOrder;
import gameplay.order.DiplomacyOrder;
import gameplay.GameInformation;
import gameplay.order.Order;
import gameplay.Player;
import gameutils.GameException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import mapparser.GameMap.Country;

/**
 *
 * @author USER
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

    public void executeDeployOrder(Player p_current_player, List<Order> p_player_orders) throws Exception {

        int l_army_deploy = p_current_player.getCurrentArmies();
        int l_random_army;
        Random l_random = new Random();

        do {
            //choose a random country to deploy armies in

            List<Country> l_countries = p_current_player.d_conquered_countries;

            int l_country_index = l_random.nextInt(l_countries.size());
            Country l_deploy_country = l_countries.get(l_country_index);
            if (l_deploy_country == null) {
                throw new GameException(GameMessageConstants.D_INTERNAL_ERROR);
            }
            l_random_army = l_random.nextInt(l_army_deploy) + 1;

            Order l_current_order = new DeployOrder(l_deploy_country.getCountryName(), l_random_army);
            p_player_orders.add(l_current_order);
            p_current_player.setCurrentArmies(0);
            System.out.println(GameMessageConstants.D_DEPLOY + " " + GameMessageConstants.D_ORDER_ISSUED);
            d_logger.addLogger(GameMessageConstants.D_DEPLOY + " " + GameMessageConstants.D_ORDER_ISSUED);

            l_army_deploy = l_army_deploy - l_random_army;
        } while (l_army_deploy != 0);

    }

    public void executeAttackOrder(Player p_current_player, List<Order> p_player_orders) throws Exception {
        double l_random_card = Math.random();
        double l_random_attack = Math.random();
        Random l_random = new Random();

        List<Country> l_countries = this.d_current_game_info.getGameMap().d_countries;
        LinkedHashMap<Integer, List<Integer>> l_borders = this.d_current_game_info.getGameMap().d_borders;

        while (l_random_attack >= 0.5) {
            if (l_random_card >= 0.5) {
                //advance order
                executeAdvanceOrder(p_current_player, p_player_orders);

            } else {
                //use a card if exists
                //check if the player has a card
                List<Card> l_random_card_select = new ArrayList<Card>();
                HashMap<Card, Integer> l_cards = p_current_player.getAvailableCards();
                for (Map.Entry<Card, Integer> entry : l_cards.entrySet()) {
                    Card l_key = entry.getKey();
                    Integer l_value = entry.getValue();

                    if (l_value > 1) {
                        l_random_card_select.add(l_key);
                    }
                }

                Card l_select_card = l_random_card_select.get(l_random.nextInt(l_random_card_select.size()));

                //select a random card 
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
                    case DIPLOMACY:
                        executeDiplomacy(p_current_player, p_player_orders);
                        break;
                    default:
                        throw new GameException(GameMessageConstants.D_INTERNAL_ERROR);
                }

            }
            l_random_card = Math.random();
            l_random_attack = Math.random();
        }

    }
    
    public void executeMoveArmies(Player p_current_player, List<Order> p_player_orders) throws Exception {
        Random l_random = new Random();
        Country l_from_country_obj = null;
        Country l_to_country_obj = null;
        int l_armies_number = -1;
        
        LinkedHashMap<Integer, List<Integer>> l_borders = this.d_current_game_info.getGameMap().d_borders;
        
        //select a from country
        int l_country_index = l_random.nextInt(p_current_player.d_conquered_countries.size());
        l_from_country_obj = p_current_player.d_conquered_countries.get(l_country_index);
        
        //select a to country 
        
        List<Country> l_countries_adj = new ArrayList<Country>();
        
        List<Integer> l_countries_adj_id = l_borders.get(l_from_country_obj.getCountryID());
        
        l_country_index = l_random.nextInt(l_countries_adj_id.size());
        l_to_country_obj = this.d_current_game_info.getGameMap().getCountryById(l_countries_adj_id.get(l_country_index));
        
        //army count
        l_armies_number = l_random.nextInt(l_from_country_obj.getArmyCount() + 1);
        
        if (l_from_country_obj == null || l_to_country_obj == null) {
            throw new GameException(GameMessageConstants.D_INTERNAL_ERROR);
        }

        if (l_armies_number == -1) {
            throw new GameException(GameMessageConstants.D_INTERNAL_ERROR);
        }
        
        Order l_current_order_adv = new AdvanceOrder(l_from_country_obj, l_to_country_obj, l_armies_number);
        p_player_orders.add(l_current_order_adv);
        System.out.println(GameMessageConstants.D_ADVANCE + " " + GameMessageConstants.D_ORDER_ISSUED);
        d_logger.addLogger(GameMessageConstants.D_ADVANCE + " " + GameMessageConstants.D_ORDER_ISSUED);
        
    }

    public void executeAdvanceOrder(Player p_current_player, List<Order> p_player_orders) throws Exception {
        Random l_random = new Random();
        Country l_from_country_obj = null;
        Country l_to_country_obj = null;
        int l_armies_number = -1;

        List<Country> l_countries = this.d_current_game_info.getGameMap().d_countries;
        LinkedHashMap<Integer, List<Integer>> l_borders = this.d_current_game_info.getGameMap().d_borders;
        
        //select from country
        int l_country_index = l_random.nextInt(p_current_player.d_conquered_countries.size());
        l_from_country_obj = p_current_player.d_conquered_countries.get(l_country_index);

        //select to country
        List<Country> l_countries_opp_adj = new ArrayList<Country>();

        for (int l_index = 0; l_index < l_countries.size(); l_index++) {
            if (l_countries.get(l_index).getPlayerName().compareTo(l_from_country_obj.getPlayerName()) != 0
                    && l_countries.get(l_index).isCountryAdjacent(l_from_country_obj.getCountryName())) {
                l_countries_opp_adj.add(l_countries.get(l_index));
            }
        }

        l_country_index = l_random.nextInt(l_countries_opp_adj.size());
        l_to_country_obj = l_countries_opp_adj.get(l_country_index);

        //generate army count
        l_armies_number = l_random.nextInt(l_from_country_obj.getArmyCount() + 1);

        if (l_from_country_obj == null || l_to_country_obj == null) {
            throw new GameException(GameMessageConstants.D_INTERNAL_ERROR);
        }

        if (l_armies_number == -1) {
            throw new GameException(GameMessageConstants.D_INTERNAL_ERROR);
        }

        Order l_current_order_adv = new AdvanceOrder(l_from_country_obj, l_to_country_obj, l_armies_number);
        p_player_orders.add(l_current_order_adv);
        System.out.println(GameMessageConstants.D_ADVANCE + " " + GameMessageConstants.D_ORDER_ISSUED);
        d_logger.addLogger(GameMessageConstants.D_ADVANCE + " " + GameMessageConstants.D_ORDER_ISSUED);
    }

    public void executeBlockade(Player p_current_player, List<Order> p_player_orders) throws Exception {
        Random l_random = new Random();
        Country l_country_blockade = null;

        List<Country> l_conq_countries = p_current_player.d_conquered_countries;
        l_country_blockade = l_conq_countries.get(l_random.nextInt(l_conq_countries.size()));

        if (l_country_blockade == null) {
            throw new GameException(GameMessageConstants.D_INTERNAL_ERROR);
        }

        Order l_current_order_blockade = new BlockadeOrder(l_country_blockade);
        p_player_orders.add(l_current_order_blockade);
        p_current_player.removeAvailableCard(Card.BLOCKADE);
        System.out.println(GameMessageConstants.D_BLOCKADE + " " + GameMessageConstants.D_ORDER_ISSUED);
        d_logger.addLogger(GameMessageConstants.D_BLOCKADE + " " + GameMessageConstants.D_ORDER_ISSUED);
    }

    public void executeAirlift(Player p_current_player, List<Order> p_player_orders) throws Exception {
        Random l_random = new Random();
        Country l_from_country_obj = null;
        Country l_to_country_obj = null;
        int l_armies_number = -1;

        List<Country> l_countries = this.d_current_game_info.getGameMap().d_countries;
        LinkedHashMap<Integer, List<Integer>> l_borders = this.d_current_game_info.getGameMap().d_borders;

        //from country
        int l_country_index = l_random.nextInt(p_current_player.d_conquered_countries.size());
        l_from_country_obj = p_current_player.d_conquered_countries.get(l_country_index);

        // to country
        l_countries = this.d_current_game_info.getGameMap().d_countries;
        List<Country> l_countries_opp = new ArrayList<Country>();

        for (int l_index = 0; l_index < l_countries.size(); l_index++) {
            if (l_countries.get(l_index).getPlayerName().compareTo(l_from_country_obj.getPlayerName()) != 0) {
                l_countries_opp.add(l_countries.get(l_index));
            }
        }

        l_to_country_obj = l_countries_opp.get(l_random.nextInt(l_countries_opp.size()));

        //number of armies moved
        l_armies_number = l_random.nextInt(l_from_country_obj.getArmyCount() + 1);

        if (l_from_country_obj == null || l_to_country_obj == null) {
            throw new GameException(GameMessageConstants.D_INTERNAL_ERROR);
        }

        if (l_armies_number == -1) {
            throw new GameException(GameMessageConstants.D_INTERNAL_ERROR);
        }

        Order l_current_order_airlift = new AirliftOrder(l_from_country_obj, l_to_country_obj, l_armies_number);
        p_player_orders.add(l_current_order_airlift);
        p_current_player.removeAvailableCard(Card.AIRLIFT);
        System.out.println(GameMessageConstants.D_AIRLIFT + " " + GameMessageConstants.D_ORDER_ISSUED);
        d_logger.addLogger(GameMessageConstants.D_AIRLIFT + " " + GameMessageConstants.D_ORDER_ISSUED);
    }

    public void executeBomb(Player p_current_player, List<Order> p_player_orders) throws Exception {
        Random l_random = new Random();
        Country l_country_bomb = null;

        List<Country> l_countries = this.d_current_game_info.getGameMap().d_countries;
        LinkedHashMap<Integer, List<Integer>> l_borders = this.d_current_game_info.getGameMap().d_borders;

        //find all the adjacent countries   -- make sure he cant bomb his own country
        l_countries = this.d_current_game_info.getGameMap().d_countries;
        HashSet<Integer> l_neighbour_countries = new HashSet<Integer>();

        for (int l_index = 0; l_index < p_current_player.d_conquered_countries.size(); l_index++) {
            for (int l_j_index = 1; l_j_index < l_borders.get(p_current_player.d_conquered_countries.get(l_index)
                    .getCountryID()).size(); l_j_index++) {
                l_neighbour_countries.add(l_borders.get(p_current_player.d_conquered_countries.get(l_index)
                        .getCountryID()).get(l_j_index));
            }
        }

        Integer l_neighbour_arr[] = (Integer[]) l_neighbour_countries.toArray();
        l_country_bomb = this.d_current_game_info.getGameMap().getCountryById(l_neighbour_arr[l_random.nextInt(l_neighbour_countries.size())]);

        if (l_country_bomb == null) {
            throw new GameException(GameMessageConstants.D_INTERNAL_ERROR);
        }

        Order l_current_order_bomb = new BombOrder(l_country_bomb);
        p_player_orders.add(l_current_order_bomb);
        p_current_player.removeAvailableCard(Card.BOMB);
        System.out.println(GameMessageConstants.D_BOMB + " " + GameMessageConstants.D_ORDER_ISSUED);
        d_logger.addLogger(GameMessageConstants.D_BOMB + " " + GameMessageConstants.D_ORDER_ISSUED);
    }

    public void executeDiplomacy(Player p_current_player, List<Order> p_player_orders) throws Exception {
        Random l_random = new Random();
        Player l_target_player_obj = null;

        HashMap<String, Player> l_players = this.d_current_game_info.getPlayerList();
        List<Player> l_player_list = new ArrayList<Player>();
        
        for (Map.Entry<String, Player> entry : l_players.entrySet()) {
            String l_key = entry.getKey();
            Player l_value = entry.getValue();

            if (l_key.compareTo(p_current_player.getPlayerName()) != 0) {
                l_player_list.add(l_value);
            }
        }

        l_target_player_obj = l_player_list.get(l_random.nextInt(l_player_list.size()));

        if (l_target_player_obj == null) {
            throw new GameException(GameMessageConstants.D_INTERNAL_ERROR);
        }

        Order l_current_order_diplomacy = new DiplomacyOrder(l_target_player_obj);
        p_player_orders.add(l_current_order_diplomacy);
        p_current_player.removeAvailableCard(Card.DIPLOMACY);
        System.out.println(GameMessageConstants.D_DIPLOMACY + " " + GameMessageConstants.D_ORDER_ISSUED);
        d_logger.addLogger(GameMessageConstants.D_DIPLOMACY + " " + GameMessageConstants.D_ORDER_ISSUED);
    }

}
