package gameplay;

import common.LogEntryBuffer;

import java.util.Iterator;
import java.util.List;

/**
 * The class deals with the negotiation phase between the chosen players.
 */
public class DiplomacyOrder extends Order {

    /**
     * member to store logger instance
     */
    private static LogEntryBuffer d_logger = LogEntryBuffer.getInstance();

    /**
     * member to store target player
     */
    private Player d_target_player;

    /**
     * Constructor initialises the target player objects.
     * @param p_target_player target player object.
     */
    public DiplomacyOrder(Player p_target_player) {
        this.d_target_player = p_target_player;
    }

    /**
     * Method returns the target player.
     * @return target player object.
     */
    public Player getTargetPlayer() {
        return this.d_target_player;
    }

    /**
     * Method executes the Diplomacy phase.
     * @param p_player_obj The player object for whom the order is executed.
     * @throws Exception If there is an error in the execution or validation.
     */
    @Override
    public void execute(Player p_player_obj) throws Exception {

        d_logger.addLogger("Diplomacy Order Initiated");
        List<Order> l_player_orders = p_player_obj.getOrders();
        String l_target_player_name = d_target_player.getPlayerName();

        Iterator<Order> l_order_iterator = l_player_orders.iterator();
        while (l_order_iterator.hasNext()) {
            Order l_order = l_order_iterator.next();
            String l_destination_player = "";
            if (l_order instanceof AdvanceOrder) {

                l_destination_player = ((AdvanceOrder) l_order).getDestinationCountry().getPlayerName();
                if (l_target_player_name.equals(l_destination_player)) l_order_iterator.remove();

            } else if (l_order instanceof BombOrder) {

                l_destination_player = ((BombOrder) l_order).getDestinationCountry().getPlayerName();
                if (l_target_player_name.equals(l_destination_player)) l_order_iterator.remove();

            } else if (l_order instanceof AirliftOrder) {

                l_destination_player = ((AirliftOrder) l_order).getDestinationCountry().getPlayerName();
                if (l_target_player_name.equals(l_destination_player)) l_order_iterator.remove();
            }
        }

        l_player_orders = d_target_player.getOrders();
        l_target_player_name = p_player_obj.getPlayerName();
        l_order_iterator = l_player_orders.iterator();
        while (l_order_iterator.hasNext()) {
            Order l_order = l_order_iterator.next();
            String l_destination_player = "";
            if (l_order instanceof AdvanceOrder) {

                l_destination_player = ((AdvanceOrder) l_order).getDestinationCountry().getPlayerName();
                if (l_target_player_name.equals(l_destination_player)) l_order_iterator.remove();

            } else if (l_order instanceof BombOrder) {

                l_destination_player = ((BombOrder) l_order).getDestinationCountry().getPlayerName();
                if (l_target_player_name.equals(l_destination_player)) l_order_iterator.remove();

            } else if (l_order instanceof AirliftOrder) {

                l_destination_player = ((AirliftOrder) l_order).getDestinationCountry().getPlayerName();
                if (l_target_player_name.equals(l_destination_player)) l_order_iterator.remove();
            }
        }
    }
}
