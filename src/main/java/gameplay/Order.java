package gameplay;

/**
 *Exectues the order for a player.
 */
public abstract class Order {

    /**
     *
     * @param p_player_obj The player for whom the order is executed.
     * @throws Exception Exception If there is an error while executing the order.
     */
    public abstract void execute(Player p_player_obj) throws Exception;

}
