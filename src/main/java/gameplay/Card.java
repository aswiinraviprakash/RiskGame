package gameplay;

/**
 * Distributes random cards to the players when the conditions are met.
 */
public enum Card {
    /**
     * The various cards available are {@link #BOMB}, {@link #BLOCKADE}, {@link #AIRLIFT} and {@link #DIPLOMACY}
     */

    BOMB, BLOCKADE, AIRLIFT, DIPLOMACY;

    /**
     * Method selects cards at random.
     * @return cards.
     */
    public static Card generateRandomCard() {
        int l_rand_index = -1;
        //calculate the type of card to be assigned
        l_rand_index = 0 + (int) (Math.random() * (3 + 1));
        return Card.values()[l_rand_index];
    }

}
