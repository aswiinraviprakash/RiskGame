package gameplay;

import java.util.HashMap;

/**
 *
 * @author USER
 */
public class Card {

    private static enum d_cards {
        bomb, blockade, airlift, diplomacy
    }

    public void assignCard(Player p_player) {

        //calculate the type of card to be assigned
        int l_card_num = 0 + (int) (Math.random() * (3 + 1));
        d_cards l_cards_arr[] = d_cards.values();
        String l_card_name = l_cards_arr[l_card_num].toString();

        //assign the card to player
        HashMap<String, Integer> l_cards = p_player.getPlayerCards();
        if (l_cards.containsKey(l_card_name)) {
            l_cards.put(l_card_name, 1);
        } else {
            l_cards.replace(l_card_name, l_cards.get(l_card_name) + 1);
        }
    }

}
