package mapparser;

import constants.GameConstants;
import gameutils.MapCommonUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 * @author USER
 */
public class DominationGameMapReader {
    //call load operations in constructor
    //load functions here
    String d_file_path;
    GameMap d_game_map;
    
    public DominationGameMapReader(String p_file_path, GameMap p_game_map) {
        this.d_file_path = p_file_path;
        this.d_game_map = p_game_map;
    }
    
    public void loadBorders() throws Exception {

        //load border array from map file
        //refactored
        List<String> l_borders_list = MapCommonUtils.getMapDetails(d_file_path, "[borders]", "end");

        LinkedHashMap<Integer, List<Integer>> l_borders = new LinkedHashMap<Integer, List<Integer>>();

        ArrayList<Integer> l_borders_sub;
        List<String> l_nested_array;
        int l_country_id;

        for (int l_index = 0; l_index < l_borders_list.size(); l_index++) {
            l_country_id = -1;
            l_nested_array = Arrays.asList(l_borders_list.get(l_index).split(" "));
            l_borders_sub = new ArrayList<Integer>();
            for (int l_j_index = 0; l_j_index < l_nested_array.size(); l_j_index++) {
                if (l_j_index == 0) {
                    l_country_id = Integer.parseInt(l_nested_array.get(l_j_index));
                }
                l_borders_sub.add(Integer.parseInt(l_nested_array.get(l_j_index)));
            }
            l_borders.put(l_country_id, l_borders_sub);
        }

       this.d_game_map.d_borders = l_borders;
    }

    /**
     * Function loads countries in a continent.
     * @throws Exception If there is an error in the execution or validation.
     */
    public void loadCountries() throws Exception {

        List<GameMap.Continent> l_continents = d_game_map.getContinentObjects();

        List<GameMap.Country> l_countries = new ArrayList<GameMap.Country>();
        List<String> l_countries_list = MapCommonUtils.getMapDetails(d_file_path, "[countries]", "[borders]");

        for (int l_index = 0; l_index < l_countries_list.size(); l_index++) {
            int l_country_id = Integer.parseInt(l_countries_list.get(l_index).split(" ")[0]);
            String l_country_name = l_countries_list.get(l_index).split(" ")[1];
            int l_continent_id = Integer.parseInt(l_countries_list.get(l_index).split(" ")[2]) - 1;
            GameMap.Continent l_continent_obj = l_continents.get(l_continent_id);
            String l_continent_name = l_continent_obj.getContinentName();
            GameMap.Country l_country_obj = d_game_map.new Country(l_country_id, l_country_name, GameConstants.D_DEFAULT_IS_CONQUERED, GameConstants.D_DEFAULT_ARMY_COUNT, l_continent_name, null);
            l_countries.add(l_country_obj);
        }

        this.d_game_map.d_countries = l_countries;
    }

    /**
     * Function loads continents in a map.
     * @throws Exception If there is an error in the execution or validation.
     */
    public void loadContinents() throws Exception {
        //create continent objects
        List<GameMap.Continent> l_continents = new ArrayList<GameMap.Continent>();
        List<String> l_continents_list = MapCommonUtils.getMapDetails(d_file_path, "[continents]", "[countries]");
        GameMap.Continent l_continent_obj;
        for (int l_index = 0; l_index < l_continents_list.size(); l_index++) {
            int l_special_num = Integer.parseInt(l_continents_list.get(l_index).split(" ")[1]);
            String l_continent_name = l_continents_list.get(l_index).split(" ")[0];
            l_continent_obj = this.d_game_map.new Continent(l_continent_name, GameConstants.D_DEFAULT_IS_CONQUERED, l_special_num, null);
            l_continents.add(l_continent_obj);
        }

        this.d_game_map.d_continents = l_continents;
    }
}
