package gameplay.strategy;

import gameplay.order.Order;
import gameplay.Player;

import java.util.List;

/**
 * Interface for player strategy
 */
public interface PlayerStrategy {

    /**
     * This method creates Orders
     * @param p_player_obj The player Object
     * @return Returns The list of Orders
     * @throws Exception Throws exception
     */
    public List<Order> createOrders(Player p_player_obj) throws Exception;
}
