package gameplay.strategy;

import gameplay.Order;
import gameplay.Player;

import java.util.List;

public interface PlayerStrategy {
    public List<Order> createOrders(Player p_player_obj) throws Exception;
}
