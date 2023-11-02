package gameplay;

import gameutils.GameException;
import java.util.List;
import mapparser.GameMap.Country;

/**
 *
 * @author USER
 */
public class AirliftOrder extends Order {

    private String d_source_country;

    private String d_target_country;

    private int d_armies;

    private GameInformation d_current_game_info;

    public AirliftOrder(String p_source_country, String p_target_country, int p_armies) {
        this.d_source_country = p_source_country;
        this.d_target_country = p_target_country;
        this.d_armies = p_armies;

    }

    @Override
    public void execute(Player p_player) throws GameException {
        //move any number of army units from one of your territories to another territory, even if they are not adjacent

        if (p_player.checkIfCountryConquered(d_source_country) && p_player.checkIfCountryConquered(d_target_country)) {

            //check if army count exceeds
            int l_source_index = -1;
            int l_target_index = -1;

            int l_source_army = -1;
            int l_target_army = -1;

            List<Country> l_countries_conquered = p_player.getConqueredCountries();

            for (int l_index = 0; l_index < l_countries_conquered.size(); l_index++) {
                if (l_countries_conquered.get(l_index).getCountryName().compareTo(d_source_country) == 0) {
                    l_source_index = l_index;
                    l_source_army = l_countries_conquered.get(l_index).getArmyCount();
                }

                if (l_countries_conquered.get(l_index).getCountryName().compareTo(d_target_country) == 0) {
                    l_target_index = l_index;
                    l_target_army = l_countries_conquered.get(l_index).getArmyCount();
                }
            }

            if (l_source_index == -1 || l_target_index == -1) {
                throw new GameException("Error");
            }

            if (l_source_army > this.d_armies) {

                //update army count in both countries
                
                //check if it will be updated
                int l_new_source_army = p_player.d_conquered_countries.get(l_source_index).getArmyCount() - this.d_armies;
                int l_new_target_army = p_player.d_conquered_countries.get(l_target_index).getArmyCount() + this.d_armies;
                p_player.d_conquered_countries.get(l_source_index).setArmyCount(l_new_source_army);
                p_player.d_conquered_countries.get(l_source_index).setArmyCount(l_new_target_army);

                //update in map file
                
                for(int l_index = 0; l_index < this.d_current_game_info.getGameMap().getCountryObjects().size(); l_index++){
                    if(this.d_current_game_info.getGameMap().getCountryObjects().get(l_index).getCountryName().compareTo(this.d_source_country) == 0){
                        this.d_current_game_info.getGameMap().d_countries.get(l_index).setArmyCount(l_new_source_army);
                    }
                    
                    if(this.d_current_game_info.getGameMap().getCountryObjects().get(l_index).getCountryName().compareTo(this.d_target_country) == 0){
                         this.d_current_game_info.getGameMap().d_countries.get(l_index).setArmyCount(l_new_target_army);
                    }
                }
            } else {
                throw new GameException("Error");
            }

        } else {
            System.out.println("Invalid country selected");
            return;
        }

    }

}
