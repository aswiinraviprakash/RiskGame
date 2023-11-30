package gameplay.order;

import common.LogEntryBuffer;
import gameplay.Card;
import gameplay.GameInformation;
import gameplay.Player;
import gameutils.GameException;
import mapparser.GameMap;

/**
 * The class is advancing the armies for an attack on the desired country.
 */
public class AdvanceOrder extends Order {

    /**
     * member to store logger instance
     */
    private static LogEntryBuffer d_logger = LogEntryBuffer.getInstance();

    /**
     * member to store source country
     */
    private GameMap.Country d_source_country;

    /**
     * member to store destination country
     */
    private GameMap.Country d_destination_country;

    /**
     * member to store armies
     */
    private int d_armies;

    /**
     * member to store gameinformation instance
     */
    private GameInformation d_current_game_info;

    /**
     * Constructor initialising the member variables.
     * @param p_source_country country of origin.
     * @param p_destination_country country of attack.
     * @param p_armies army count.
     */
    public AdvanceOrder(GameMap.Country p_source_country, GameMap.Country p_destination_country, int p_armies) {
        this.d_source_country = p_source_country;
        this.d_destination_country = p_destination_country;
        this.d_armies = p_armies;
    }

    /**
     * Method returns the country of attack or destination.
     * @return country of attack.
     */
    public GameMap.Country getDestinationCountry() {
        return this.d_destination_country;
    }

    /**
     * Method attacks the destination country initiated by the current player.
     * @param p_current_player current player object.
     * @param p_destination_player attacking player object.
     */
    public void attackDestinationCountry(Player p_current_player, Player p_destination_player) {

        if (p_current_player != null && p_destination_player != null && (p_current_player.checkDiplomacyRelation(p_destination_player) || p_destination_player.checkDiplomacyRelation(p_current_player))) return;

        int l_destination_armies = d_destination_country.getArmyCount();
        int l_source_armies = d_source_country.getArmyCount();

        if (d_armies > l_source_armies) return;

        if (d_armies > l_destination_armies) {

            d_source_country.setArmyCount(l_source_armies - d_armies);
            d_destination_country.setArmyCount(d_armies - l_destination_armies);
            if (p_destination_player != null) {
                p_destination_player.getConqueredCountries().remove(d_destination_country);
            }
            d_destination_country.setPlayerName(p_current_player.getPlayerName());
            p_current_player.getConqueredCountries().add(d_destination_country);

            if (!d_current_game_info.isPlayerIssuedCard(p_current_player.getPlayerName())) {
                Card l_random_card = Card.generateRandomCard();
                p_current_player.addAvailableCard(l_random_card);
                d_current_game_info.setCardIssuedPlayer(p_current_player.getPlayerName());
            }

        } else if (d_armies <= l_destination_armies) {
            d_source_country.setArmyCount(l_source_armies - d_armies);
            d_destination_country.setArmyCount(l_destination_armies - d_armies);
        }
    }

    /**
     * Method transfers the armies to the destination country.
     */
    public void movesArmiesToDestinationCountry() {
        int l_destination_armies = d_destination_country.getArmyCount();
        int l_source_armies = d_source_country.getArmyCount();

        if (d_armies > l_source_armies) return;

        d_source_country.setArmyCount(l_source_armies - d_armies);
        d_destination_country.setArmyCount(l_destination_armies + d_armies);
    }

    /**
     * Method executes the advancing order phase.
     * @param p_player_obj The player object for whom the order is executed.
     * @throws GameException Game Exception error.
     */
    @Override
    public void execute(Player p_player_obj) throws GameException {

        d_current_game_info = GameInformation.getInstance();
        d_logger.addLogger("Advance order Initiated");
        if (!p_player_obj.getConqueredCountries().contains(d_source_country)) return;

        String l_player_name = p_player_obj.getPlayerName();
        boolean isAttackMode = false;

        Player l_destination_player = null;
        String l_destination_player_name = d_destination_country.getPlayerName();
        if (l_destination_player_name == null) {
            isAttackMode = true;
        } else if (!(l_destination_player_name.equals(l_player_name))) {
            isAttackMode = true;
            l_destination_player = d_current_game_info.getPlayerList().get(l_destination_player_name);
        }

        if (isAttackMode) {
            attackDestinationCountry(p_player_obj, l_destination_player);
            d_logger.addLogger("Advance order in attack");
        } else {
            movesArmiesToDestinationCountry();
            d_logger.addLogger("Advance order in Friendly territory");
        }
    }

}
