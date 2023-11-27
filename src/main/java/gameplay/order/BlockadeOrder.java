package gameplay.order;

import common.LogEntryBuffer;
import gameplay.Player;
import mapparser.GameMap;

/**
 * The class is responsible for the blockade.
 */
public class BlockadeOrder extends Order {

    /**
     * member to store logger instance
     */
    private static LogEntryBuffer d_logger = LogEntryBuffer.getInstance();

    /**
     * member to store destination country
     */
    private GameMap.Country d_destination_country;

    /**
     * Constructor for initialising member variables.
     * @param p_destination_country destination country.
     */
    public BlockadeOrder(GameMap.Country p_destination_country){
        this.d_destination_country = p_destination_country;
    }

    /**
     * Method executes the Blockade phase.
     * @param p_player_obj The player object for whom the order is executed.
     * @throws Exception Game Exception.
     */
    @Override
    public void execute(Player p_player_obj) throws Exception {
        d_logger.addLogger("Blockade Order Initiated");
        if (!p_player_obj.getConqueredCountries().contains(d_destination_country)) return;

        int l_destination_armies = d_destination_country.getArmyCount();
        d_destination_country.setArmyCount(l_destination_armies * 3);
        p_player_obj.getConqueredCountries().remove(d_destination_country);
        d_destination_country.setPlayerName(null);
    }
    
}
