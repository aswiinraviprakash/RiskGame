package gameplay.order;

import gameplay.Player;

import java.io.Serializable;

/**
 *Executes the order for a player.
 */
public abstract class Order implements Serializable {

    /**
     * Execute a player's action or order.
     * @param p_player_obj The player for whom the order is executed.
     * @throws Exception Exception If there is an error while executing the order.
     */
    public abstract void execute(Player p_player_obj) throws Exception;

}
