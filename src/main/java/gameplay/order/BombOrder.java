package gameplay.order;

import gameplay.GameInformation;
import gameplay.Player;
import gameutils.GameException;
import common.LogEntryBuffer;
import mapparser.GameMap;

/**
 * The class deals with bombing of the countries using the bomb card.
 */
public class BombOrder extends Order {

    /**
     * member to store logger instance
     */
    private static LogEntryBuffer d_logger = LogEntryBuffer.getInstance();

    /**
     * member to store destination country
     */
    private GameMap.Country d_destination_country;

    /**
     * member to store gameinformation instance
     */
    private GameInformation d_current_game_info;

    /**
     * Constructor used to initialise the member variables.
     * @param p_destination_country country chosen for attack.
     */
    public BombOrder(GameMap.Country p_destination_country) {
        this.d_destination_country = p_destination_country;
    }

    /**
     * Function returns the country of attack or destination.
     * @return country of attack.
     */
    public GameMap.Country getDestinationCountry() {
        return this.d_destination_country;
    }

    /**
     * Method executes the bomb phase.
     * @param p_player_obj The player object for whom the order is executed.
     * @throws GameException Game Exception.
     */
    @Override
    public void execute(Player p_player_obj) throws Exception {

        d_current_game_info = GameInformation.getInstance();

        if (p_player_obj.getConqueredCountries().contains(d_destination_country)) return;

        d_logger.addLogger("Bomb order Initiated");
        String l_destination_player = d_destination_country.getPlayerName();
        Player l_destination_player_obj = d_current_game_info.getPlayerList().get(l_destination_player);

        if (p_player_obj != null && l_destination_player_obj != null && (p_player_obj.checkDiplomacyRelation(l_destination_player_obj) || l_destination_player_obj.checkDiplomacyRelation(p_player_obj))) return;

        int l_destination_armies = d_destination_country.getArmyCount();
        d_destination_country.setArmyCount(l_destination_armies / 2);
    }
}