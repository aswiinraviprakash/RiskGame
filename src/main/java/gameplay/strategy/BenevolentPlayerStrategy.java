package gameplay.strategy;

import common.LogEntryBuffer;
import constants.GameMessageConstants;
import gameplay.DeployOrder;
import java.util.ArrayList;
import java.util.List;
import gameplay.Player;
import gameplay.Order;
import gameplay.GameInformation;
import gameutils.GameException;
import mapparser.GameMap.Country;


public class BenevolentPlayerStrategy implements PlayerStrategy {
    
    
    /**
     * member to store logger instance
     */
    private static LogEntryBuffer d_logger = LogEntryBuffer.getInstance();

    /**
     * Contains current game information.
     */
    private GameInformation d_current_game_info;
    
    
    @Override
    public List<Order> createOrders(Player p_player_obj) throws Exception{
        
        List<Order> l_player_orders = new ArrayList<>();
        
        d_current_game_info = GameInformation.getInstance();

        //deploy order
        System.out.println("Issuing deploy order");

        executeDeployOrder(p_player_obj, l_player_orders);


        //issue order - moving armies
        System.out.println("Moving armies");
        
        executeMoveArmies(p_player_obj, l_player_orders);

        return l_player_orders;
    }
    
    public void executeDeployOrder(Player p_current_player, List<Order> p_player_orders) throws Exception{
        String l_country_name = null;
        int l_armies_number = -1;
        
        //find the weakest country
        
        List<Country> l_countries = p_current_player.d_conquered_countries;
        Country l_weak_country = null;
        
        int l_min_count = Integer.MAX_VALUE;
        
        for(int l_index = 0; l_index < l_countries.size(); l_index++){
            if(l_countries.get(l_index).getArmyCount() < l_min_count){
                l_min_count = l_countries.get(l_index).getArmyCount();
                l_weak_country = l_countries.get(l_index);
            }
        }
        
        l_country_name = l_weak_country.getCountryName();
        l_armies_number = p_current_player.getCurrentArmies();
        
        if(l_weak_country == null || l_armies_number == -1) throw new GameException(GameMessageConstants.D_INTERNAL_ERROR);
        
        Order l_current_order = new DeployOrder(l_country_name, l_armies_number);
        p_player_orders.add(l_current_order);
        p_current_player.setCurrentArmies(0);
        System.out.println(GameMessageConstants.D_DEPLOY + " " + GameMessageConstants.D_ORDER_ISSUED);
        d_logger.addLogger(GameMessageConstants.D_DEPLOY + " " + GameMessageConstants.D_ORDER_ISSUED);
        
    }
    
    public void executeMoveArmies(Player p_current_player, List<Order> p_player_orders) throws Exception{
        //find the weakest country
        
        List<Country> l_countries = p_current_player.d_conquered_countries;
        Country l_weak_country = null;
        
        int l_min_count = Integer.MAX_VALUE;
        
        for(int l_index = 0; l_index < l_countries.size(); l_index++){
            if(l_countries.get(l_index).getArmyCount() < l_min_count){
                l_min_count = l_countries.get(l_index).getArmyCount();
                l_weak_country = l_countries.get(l_index);
            }
        }
             
        
    }
}