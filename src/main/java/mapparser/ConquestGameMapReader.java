package mapparser;

import constants.GameConstants;
import constants.GameMessageConstants;
import gameutils.GameException;
import gameutils.MapCommonUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import mapparser.GameMap.Continent;
import mapparser.GameMap.Country;


/**
 * Map reader class for conquest mode
 */
public class ConquestGameMapReader {

    /**
     * Contains File path
     */
    String d_file_path;

    /**
     * The game map for mode
     */
    GameMap d_game_map;

    /**
     * Constructor that sets file path and game map
     * @param p_file_path file path
     * @param p_game_map game map
     */
    public ConquestGameMapReader(String p_file_path, GameMap p_game_map) {
        this.d_file_path = p_file_path;
        this.d_game_map = p_game_map;
    }

    /**
     * Linked Hashmap to read borders
     * @return the borders list
     * @throws Exception There seems to be an issue loading your map.
     */
    public LinkedHashMap<Integer, List<Integer>> readBorders() throws Exception {
        List<String> l_map_details = new ArrayList<String>();
        List<String> l_countries = new ArrayList<String>();
        List<Country> l_country_obj = new ArrayList<Country>();
        
        try {
            l_map_details = MapCommonUtils.getMapDetails(this.d_file_path, "[Territories]", "end");
        } catch (Exception e) {
            throw new GameException(GameMessageConstants.D_MAP_LOAD_FAILED);
        }

        HashMap<Integer, List<String>> l_border_str = new HashMap<Integer, List<String>>();
        HashMap<String, Integer> l_country_id_map = new HashMap<String, Integer>();

        int l_id = 1;
        int starting_index = 4;
        for (int l_index = 0; l_index < l_map_details.size(); l_index++) {
            int l_country_id = l_id++;
            String l_country_name = l_map_details.get(l_index).split(",")[0];
            if (l_map_details.get(l_index).split(",")[1].matches("[0-9]+")) {
                String l_continent_name = l_map_details.get(l_index).split(",")[3];

            } else {
                String l_continent_name = l_map_details.get(l_index).split(",")[1];
                starting_index = 2;
            }

            List<String> l_borders = new ArrayList<String>();
            for (int l_j_index = starting_index; l_j_index < l_map_details.get(l_index).split(",").length; l_j_index++) {
                l_borders.add(l_map_details.get(l_index).split(",")[l_j_index]);
            }

            l_border_str.put(l_country_id, l_borders);
            l_country_id_map.put(l_country_name, l_country_id);
        }
        
        LinkedHashMap<Integer, List<Integer>> l_borders = new LinkedHashMap<Integer, List<Integer>>();

        for (Map.Entry<Integer, List<String>> entry : l_border_str.entrySet()) {
            Integer key = entry.getKey();
            List<String> value = entry.getValue();
            List<Integer> l_border_list_country = new ArrayList<Integer>();
            l_border_list_country.add(key);
            for (int l_index = 0; l_index < value.size(); l_index++) {
                l_border_list_country.add(l_country_id_map.get(value.get(l_index)));
            }
            l_borders.put(key, l_border_list_country);
        }
       
        return l_borders;
    }

    /**
     * List to read continents
     * @return continent object
     * @throws Exception There seems to be an issue loading your map.
     */
    public List<Continent> readContinents() throws Exception {
        
        List<String> l_map_details = new ArrayList<String>();
        List<String> l_continents = new ArrayList<String>();
        List<Continent> l_continent_obj = new ArrayList<Continent>();

        //get continents
        try {
            l_map_details = MapCommonUtils.getMapDetails(this.d_file_path, "[Continents]", "[Territories]");
        } catch (Exception e) {
            throw new GameException(GameMessageConstants.D_MAP_LOAD_FAILED);
        }

        for (int l_index = 0; l_index < l_map_details.size(); l_index++) {
            String l_continent_name = l_map_details.get(l_index).split("=")[0];
            int l_special_number = Integer.parseInt(l_map_details.get(l_index).split("=")[1]);
            GameMap.Continent l_continent = this.d_game_map. new Continent(l_continent_name, GameConstants.D_DEFAULT_IS_CONQUERED, l_special_number, null);
            l_continent_obj.add(l_continent);
        }
       
        return l_continent_obj;
    }

    /**
     * List to read countries
     * @return country object
     * @throws Exception There seems to be an issue loading your map.
     */
    public List<Country> readCountries() throws Exception {
        
        List<String> l_map_details = new ArrayList<String>();
        List<String> l_countries = new ArrayList<String>();
        List<Country> l_country_obj = new ArrayList<Country>();

        try {
            l_map_details = MapCommonUtils.getMapDetails(this.d_file_path, "[Territories]", "end");
        } catch (Exception e) {
            throw new GameException(GameMessageConstants.D_MAP_LOAD_FAILED);
        }

        for (int l_index = 0; l_index < l_map_details.size(); l_index++){
            if (l_map_details.get(l_index).compareTo("") == 0) {
                l_map_details.remove(l_index);
            }
        }

        int l_country_id = 1;
        for (int l_index = 0; l_index < l_map_details.size(); l_index++) {
            String l_country_name = l_map_details.get(l_index).split(",")[0];
            String l_continent_name = "";
            if (l_map_details.get(l_index).split(",")[1].matches("[0-9]+")) {
                l_continent_name = l_map_details.get(l_index).split(",")[3];

            } else {
                l_continent_name = l_map_details.get(l_index).split(",")[1];
            }
            
            GameMap.Country l_country = this.d_game_map. new Country(l_country_id++, l_country_name, GameConstants.D_DEFAULT_IS_CONQUERED, GameConstants.D_DEFAULT_ARMY_COUNT, l_continent_name, null);
            l_country_obj.add(l_country);
           
        }

        return l_country_obj;
    }
    
}
