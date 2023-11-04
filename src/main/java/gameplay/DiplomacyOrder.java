package gameplay;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author USER
 */
public class DiplomacyOrder extends Order {

    private String d_player_name;

    private GameInformation d_current_game_info;

    public DiplomacyOrder(String p_player_name) {
        this.d_player_name = p_player_name;

    }

    @Override
    public void execute(Player p_player) {
        //prevent attacks between the current player and another player until the end of the turn

        List<Order> l_orders_curr_player = p_player.getOrdersList();

        List<Order> l_orders_opponent = this.d_current_game_info.getPlayerList().get(this.d_player_name).getOrdersList();

        Player l_opponent_player = this.d_current_game_info.getPlayerList().get(this.d_player_name);

        for (int l_index = 0; l_index < l_orders_opponent.size(); l_index++) {
            if (l_orders_opponent.get(l_index) instanceof AdvanceOrder) {
                //check if the order is in attack mode
                AdvanceOrder l_order = (AdvanceOrder) l_orders_opponent.get(l_index);

                if (p_player.checkIfCountryConquered(l_order.getCountryTo())) {
                    //remove Order from order List
                    this.d_current_game_info.getPlayerList().remove(this.d_player_name);
                }

            } else if (l_orders_opponent.get(l_index) instanceof AirliftOrder) {

                AirliftOrder l_order = (AirliftOrder) l_orders_opponent.get(l_index);

                if (p_player.checkIfCountryConquered(l_order.getCountryTo())) {
                    //remove Order from order List
                    this.d_current_game_info.getPlayerList().remove(this.d_player_name);
                }

            }
        }

    }
}
