package gameplay;

public enum Card {

    BOMB, BLOCKADE, AIRLIFT, DIPLOMACY;

    public static Card generateRandomCard() {
        int l_rand_index = -1;
        //calculate the type of card to be assigned
        l_rand_index = 0 + (int) (Math.random() * (3 + 1));
        return Card.values()[l_rand_index];
    }

}
