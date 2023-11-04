package gameplay;

import constants.GameMessageConstants;
import gameutils.GameException;
import java.util.List;
import mapparser.GameMap;

/**
 *
 * @author USER
 */
public class BlockadeOrder extends Order{
    
    private String d_country_name;
    
    private GameInformation d_current_game_info;
    
    public BlockadeOrder(String p_country_name){
        this.d_country_name = p_country_name;
        
    }
    
    @Override
    public void execute(Player p_player) throws Exception{
        //triple the number of armies on one of the current player’s territories and make it a neutral territory

        int l_armies_count = -1;
        //check if the country is player's and remove from player's conquered countries
        List<GameMap.Country> l_countries_conquered = p_player.d_conquered_countries;
        if (p_player.checkIfCountryConquered(d_country_name)) {
            for (int l_index = 0; l_index < l_countries_conquered.size(); l_index++) {
                if (l_countries_conquered.get(l_index).getCountryName().compareTo(d_country_name) == 0) {
                    d_current_game_info.getPlayerList().get(p_player.getPlayerName()).d_conquered_countries.remove(l_index);
                    l_armies_count = l_countries_conquered.get(l_index).getArmyCount();
                }
            }
        } else {
            //can't execute the function because its not his country
            throw new GameException(GameMessageConstants.D_INVALID_COUNTRY);
        }

        //if yes triple the number of armies in that country update in map class
        if (l_armies_count == -1) {
            throw new GameException(GameMessageConstants.D_INVALID_COUNTRY);
        } else {

            for (int l_index = 0; l_index < d_current_game_info.getGameMap().getCountryObjects().size(); l_index++) {
                if (d_current_game_info.getGameMap().getCountryObjects().get(l_index).getCountryName().compareTo(d_country_name) == 0) {
                    d_current_game_info.getGameMap().getCountryObjects().get(l_index).setArmyCount(l_armies_count * 3);
                }
            }
        }

    }
    
}
