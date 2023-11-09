package gameplay;

import common.LogEntryBuffer;

/**
 * Distributes random cards to the players when the conditions are met.
 */
public enum Card {
    
    /**
     * BOMB card
     */
    BOMB,

    /**
     * The various cards available are  
     */
    BLOCKADE,

    /**
     * The various cards available are  
     */
    AIRLIFT,

    /**
     * The various cards available are  
     */
    DIPLOMACY;

    /**
     * member to store logger instance
     */
    private static LogEntryBuffer d_logger = LogEntryBuffer.getInstance();

    /**
     * Method selects cards at random.
     * @return cards.
     */
    public static Card generateRandomCard() {
        d_logger.addLogger("Card is Generated");
        int l_rand_index = -1;
        //calculate the type of card to be assigned
        l_rand_index = 0 + (int) (Math.random() * (3 + 1));
        return Card.values()[l_rand_index];
    }

}
