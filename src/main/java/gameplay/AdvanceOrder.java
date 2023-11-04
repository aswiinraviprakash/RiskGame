package gameplay;

import gameutils.GameException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import mapparser.GameMap.Country;

/**
 *
 * @author USER
 */
public class AdvanceOrder extends Order {

    private String d_country_from;

    private String d_country_to;

    private int d_armies;

    private GameInformation d_current_game_info;

    public AdvanceOrder(String p_country_from, String p_country_to, int p_armies) {
        this.d_country_from = p_country_from;
        this.d_country_to = p_country_to;
        this.d_armies = p_armies;
               
    }
    
    public String getCountryFrom(){
        return this.d_country_from;
    }
    
    public String getCountryTo(){
        return this.d_country_to;
    }

    @Override
    public void execute(Player p_player) throws GameException {

        LinkedHashMap<Integer, List<Integer>> l_borders = d_current_game_info.getGameMap().d_borders;
        List<Country> l_countries = d_current_game_info.getGameMap().d_countries;

        boolean l_attack_mode = false;

        int l_country_from_index = -1;
        int l_country_to_index = -1;

        //check if the countries are adjacent
        for (int l_index = 0; l_index < l_countries.size(); l_index++) {
            if (l_countries.get(l_index).getCountryName().compareTo(this.d_country_from) == 0) {
                l_country_from_index = l_index;
            }

            if (l_countries.get(l_index).getCountryName().compareTo(this.d_country_to) == 0) {
                l_country_to_index = l_index;
            }
        }

        if (l_country_from_index == -1 || l_country_to_index == -1) {
            System.out.println("something went wrong");
            return;
        }

        boolean l_countries_adjacent = l_countries.get(l_country_from_index).isCountryAdjacent(this.d_country_from);

        //check if country_from is his country 
        if (p_player.checkIfCountryConquered(this.d_country_from)) {

            if (l_countries_adjacent) {

                //check if armies lesser
                if (l_countries.get(l_country_from_index).getArmyCount() >= this.d_armies) {

                    boolean l_already_conquered_country = false;
                    Iterator<HashMap.Entry<String, Player>> iterator = d_current_game_info.getPlayerList().entrySet().iterator();

                    //checking if someone already owns countryto
                    while (iterator.hasNext()) {
                        HashMap.Entry<String, Player> entry = iterator.next();
                        String l_key = entry.getKey();
                        Player l_value = entry.getValue();

                        for (int l_j_index = 0; l_j_index < l_value.d_conquered_countries.size(); l_j_index++) {
                            if (l_value.d_conquered_countries.get(l_j_index).getCountryName().compareTo(this.d_country_to) == 0) {
                                l_already_conquered_country = true;
                                break;
                            }
                        }
                    }

                    //check if its attack mode
                    if (!p_player.checkIfCountryConquered(this.d_country_to) && l_already_conquered_country) {

                        //attack mode 
                        int l_country_from_army = d_current_game_info.getGameMap().d_countries.get(l_country_from_index).getArmyCount();

                        int l_country_to_army = d_current_game_info.getGameMap().d_countries.get(l_country_to_index).getArmyCount();

                        if (this.d_armies >= l_country_to_army) {

                            //ownership change
                            //tocountryarmy = darmies - lcountrytoarmy
                            d_current_game_info.getGameMap().d_countries.get(l_country_to_index)
                                    .setArmyCount(this.d_armies - d_current_game_info.getGameMap().d_countries.get(l_country_to_index)
                                            .getArmyCount());
                            //fromcountryarmy = fromcountryarmy - darmies

                            d_current_game_info.getGameMap().d_countries.get(l_country_from_index)
                                    .setArmyCount(d_current_game_info.getGameMap().d_countries.get(l_country_from_index)
                                            .getArmyCount() - this.d_armies);

                            //change ownership -- tocountry will be player's
                            Player l_player_to_country = null;

                            l_countries = d_current_game_info.getGameMap().d_countries;

                            iterator = d_current_game_info.getPlayerList().entrySet().iterator();

                            while (iterator.hasNext()) {
                                HashMap.Entry<String, Player> entry = iterator.next();
                                String l_key = entry.getKey();
                                Player l_value = entry.getValue();

                                for (int l_j_index = 0; l_j_index < l_value.d_conquered_countries.size(); l_j_index++) {
                                    if (l_value.d_conquered_countries.get(l_j_index).getCountryName().compareTo(this.d_country_to) == 0) {
                                        l_player_to_country = l_value;
                                        break;
                                    }
                                }
                            }

                            //update in player objects (changing ownership)
                            d_current_game_info.getPlayerList().get(l_player_to_country.getPlayerName()).d_conquered_countries.remove(l_countries.get(l_country_to_index));
                            d_current_game_info.getPlayerList().get(p_player.getPlayerName()).d_conquered_countries.add(l_countries.get(l_country_to_index));

                        } else {

                            //no change in ownership
                            // tocountryarmy = lcountrytoarmy - darmies
                            d_current_game_info.getGameMap().d_countries.get(l_country_to_index)
                                    .setArmyCount(d_current_game_info.getGameMap().d_countries.get(l_country_to_index)
                                            .getArmyCount() - this.d_armies);

                            //fromcountryarmy = fromcountryarmy - darmies
                            d_current_game_info.getGameMap().d_countries.get(l_country_from_index)
                                    .setArmyCount(d_current_game_info.getGameMap().d_countries.get(l_country_from_index)
                                            .getArmyCount() - this.d_armies);

                        }

                    } else if (p_player.checkIfCountryConquered(this.d_country_to)) {
                        //non attacking 
                        //moving armies
                        d_current_game_info.getGameMap().d_countries.get(l_country_from_index)
                                .setArmyCount(d_current_game_info.getGameMap().d_countries.get(l_country_from_index).getArmyCount() - this.d_armies);

                        d_current_game_info.getGameMap().d_countries.get(l_country_to_index)
                                .setArmyCount(d_current_game_info.getGameMap().d_countries.get(l_country_to_index).getArmyCount() + this.d_armies);

                    } else {
                        //neutral country
                        int l_country_to_army = d_current_game_info.getGameMap().d_countries.get(l_country_to_index).getArmyCount();

                        if (this.d_armies >= l_country_to_army) {

                            //ownership change
                            //tocountryarmy = darmies - lcountrytoarmy
                            d_current_game_info.getGameMap().d_countries.get(l_country_to_index)
                                    .setArmyCount(this.d_armies - d_current_game_info.getGameMap().d_countries.get(l_country_to_index)
                                            .getArmyCount());
                            //fromcountryarmy = fromcountryarmy - darmies

                            d_current_game_info.getGameMap().d_countries.get(l_country_from_index)
                                    .setArmyCount(d_current_game_info.getGameMap().d_countries.get(l_country_from_index)
                                            .getArmyCount() - this.d_armies);

                            //change ownership -- tocountry will be player's
                            l_countries = d_current_game_info.getGameMap().d_countries;

                            d_current_game_info.getPlayerList().get(p_player.getPlayerName()).d_conquered_countries.add(l_countries.get(l_country_to_index));

                        } else {

                            //no change in ownership
                            // tocountryarmy = lcountrytoarmy - darmies
                            d_current_game_info.getGameMap().d_countries.get(l_country_to_index)
                                    .setArmyCount(d_current_game_info.getGameMap().d_countries.get(l_country_to_index)
                                            .getArmyCount() - this.d_armies);

                            //fromcountryarmy = fromcountryarmy - darmies
                            d_current_game_info.getGameMap().d_countries.get(l_country_from_index)
                                    .setArmyCount(d_current_game_info.getGameMap().d_countries.get(l_country_from_index)
                                            .getArmyCount() - this.d_armies);

                        }
                    }
                } else {
                    System.out.println("armies lesser");
                    return;
                }

            } else {
                System.out.println("Countries are not adjacent");
                return;
            }

        } else {
            System.out.println("Not your country");
            return;
        }

        //check if the countries are adjacent
        //move some armies from one of the current player’s territories (source) to an
        //adjacent territory (target). If the target territory belongs to the current player, the armies are moved
        //to the target territory. If the target territory belongs to another player, an attack happens between
        //the two territories.
    }

}
