package mapparser;

import constants.GameConstants;
import constants.GameMessageConstants;
import gameutils.GameException;
import gameutils.MapCommonUtils;
import java.util.ArrayList;
import java.util.List;
import static java.lang.Integer.parseInt;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The class loads map files and displays them.
 */
public class GameMap {

    public LinkedHashMap<Integer, List<Integer>> d_borders = new LinkedHashMap<Integer, List<Integer>>();
    public List<Country> d_countries = new ArrayList<Country>();
    public List<Continent> d_continents = new ArrayList<Continent>();

    private String d_file_path;

    /**
     * Constructor used to load the map
     *
     * @param p_file_path map file location
     */
    public GameMap(String p_file_path) {
        this.d_file_path = p_file_path;
    }

    /**
     * The class is used for creation of continents.
     */
    public class Continent {

        private String d_continent_name;
        private boolean d_is_continent_conquered;
        private int d_special_number;
        private List<Integer> d_country_list;

        /**
         * COnstructor is used for initializing a continent.
         *
         * @param p_continent_name continent name
         * @param p_is_continent_conquered continent conquered or not.
         * @param p_special_number continent bonus value
         * @param p_country_list country list in a continent.
         */
        public Continent(String p_continent_name, boolean p_is_continent_conquered, int p_special_number, List<Integer> p_country_list) {

            this.d_continent_name = p_continent_name;
            this.d_is_continent_conquered = p_is_continent_conquered;
            this.d_special_number = p_special_number;
            this.d_country_list = p_country_list;
        }

        /**
         * Function returns continent name.
         *
         * @return continent name.
         */
        public String getContinentName() {
            return this.d_continent_name;
        }

        /**
         * Function returns whether a continent is conquered or not.
         *
         * @return returns a boolean value showing the continent is conquered or
         * not.
         */
        public boolean getIsContinentConquered() {
            return this.d_is_continent_conquered;
        }

        /**
         * Function returns the bonus value.
         *
         * @return bonus value of the continent.
         */
        public int getSpecialNumber() {
            return this.d_special_number;
        }

        /**
         * Function returns list of country IDs.
         *
         * @return list of country IDs.
         */
        public List<Integer> getCountryIDList() {
            return this.d_country_list;
        }

        /**
         * Function sets a boolean value to a continent based on its conquered
         * status.
         *
         * @param p_value boolean value indicating whether a continent is
         * conquered or not.
         */
        public void setIsContinentConqered(boolean p_value) {
            this.d_is_continent_conquered = p_value;
        }

        public void setCountries(List<Integer> p_country_list) {
            this.d_country_list = p_country_list;
        }

    }

    /**
     * The class is used for creation of countries.
     */
    public class Country {

        private int d_country_id;
        private String d_country_name;
        private int d_army_count;
        private boolean d_is_country_conquered;
        private String d_continent_name;
        private String d_player_name;

        /**
         * Constructor creates a country
         *
         * @param p_country_id country ID.
         * @param p_country_name country name.
         * @param p_is_country_conquered whether country is conquered or not.
         * @param p_army_count army count.
         * @param p_continent_name continent name.
         */
        public Country(int p_country_id, String p_country_name, boolean p_is_country_conquered, int p_army_count, String p_continent_name, String p_player_name) {
            this.d_country_id = p_country_id;
            this.d_country_name = p_country_name;
            this.d_is_country_conquered = p_is_country_conquered;
            this.d_army_count = p_army_count;
            this.d_continent_name = p_continent_name;
            this.d_player_name = p_player_name;
        }

        /**
         * Function returns a country ID.
         *
         * @return country ID.
         */
        public int getCountryID() {
            return this.d_country_id;
        }

        /**
         * Function returns country name.
         *
         * @return country name.
         */
        public String getCountryName() {
            return this.d_country_name;
        }

        /**
         * Function returns army count.
         *
         * @return army count.
         */
        public int getArmyCount() {
            return this.d_army_count;
        }

        /**
         * Function returns whether a country is conquered or not.
         *
         * @return a boolean value based on the country's conquered status.
         */
        public boolean getIsCountryConquered() {
            return this.d_is_country_conquered;
        }

        /**
         * Function returns continent name.
         *
         * @return continent name.
         */
        public String getContinentName() {
            return this.d_continent_name;
        }

        public String getPlayerName() {
            return this.d_player_name;
        }
        
        public boolean isCountryAdjacent(String p_country_name){
            
            List<Country> l_countries = GameMap.this.d_countries;
            LinkedHashMap<Integer, List<Integer>> l_borders = GameMap.this.d_borders;
            
            int l_country_from_index = -1;
            int l_country_to_index = -1;

            for (int l_index = 0; l_index < l_countries.size(); l_index++) {
                if (l_countries.get(l_index).getCountryName().compareTo(this.d_country_name) == 0) {
                    l_country_from_index = l_index;
                }

                if (l_countries.get(l_index).getCountryName().compareTo(p_country_name) == 0) {
                    l_country_to_index = l_index;
                }
            }

            if (l_country_from_index == -1 || l_country_to_index == -1) {
                System.out.println("something went wrong");
                return false;
            }

            boolean l_countries_adjacent = l_borders.get(l_country_from_index).contains(l_country_to_index) && l_borders.get(l_country_to_index).contains(l_country_from_index);

            return l_countries_adjacent;
        }

        /**
         * Function sets a boolean value based on the country's conquered
         * status.
         *
         * @param p_value boolean value indicating a country's conquered
         * status.
         */
        public void setIsCountryConqered(boolean p_value) {
            this.d_is_country_conquered = p_value;

        }

        /**
         * Function sets army count.
         *
         * @param p_army_count army count.
         */
        public void setArmyCount(int p_army_count) {
            this.d_army_count = p_army_count;
        }

        public void setPlayerName(String p_player_name) {
            this.d_player_name = p_player_name;
        }

    }

    /**
     * Functions adds countries to its respective continents.
     *
     * @param p_continent continent object
     * @return list of country IDs.
     */
    public void addCountryToContinentObj(Continent p_continent) {
        List<Country> l_countries = this.getCountryObjects();

        ArrayList<Integer> l_country_id_list = new ArrayList<Integer>();
        for (int l_index = 0; l_index < l_countries.size(); l_index++) {
            if (l_countries.get(l_index).getContinentName().compareTo(p_continent.getContinentName()) == 0) {
                l_country_id_list.add(l_countries.get(l_index).getCountryID());
            }
        }

        p_continent.setCountries(l_country_id_list);
    }

    /**
     * Function adds border values to the country.
     */
    public void loadBorders() throws Exception {

        //load border array from map file
        //refactored
        List<String> l_borders_list = MapCommonUtils.getMapDetails(d_file_path, "borders", "end");

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
                    l_country_id = parseInt(l_nested_array.get(l_j_index));
                }
                l_borders_sub.add(parseInt(l_nested_array.get(l_j_index)));
            }
            l_borders.put(l_country_id, l_borders_sub);
        }

        this.d_borders = l_borders;

    }

    /**
     * Function loads countries in a continent.
     */
    public void loadCountries() throws Exception {

        List<Continent> l_continents = getContinentObjects();

        List<Country> l_countries = new ArrayList<Country>();
        List<String> l_countries_list = MapCommonUtils.getMapDetails(d_file_path, "countries", "borders");

        for (int l_index = 0; l_index < l_countries_list.size(); l_index++) {
            int l_country_id = parseInt(l_countries_list.get(l_index).split(" ")[0]);
            String l_country_name = l_countries_list.get(l_index).split(" ")[1];
            int l_continent_id = parseInt(l_countries_list.get(l_index).split(" ")[2]) - 1;
            Continent l_continent_obj = l_continents.get(l_continent_id);
            String l_continent_name = l_continent_obj.getContinentName();
            Country l_country_obj = new Country(l_country_id, l_country_name, GameConstants.D_DEFAULT_IS_CONQUERED, GameConstants.D_DEFAULT_ARMY_COUNT, l_continent_name, null);
            l_countries.add(l_country_obj);
        }

        this.d_countries = l_countries;

    }

    /**
     * Function loads continents in a map.
     */
    public void loadContinents() throws Exception {
        //create continent objects
        List<Continent> l_continents = new ArrayList<Continent>();
        List<String> l_continents_list = MapCommonUtils.getMapDetails(d_file_path, "continents", "countries");
        Continent l_continent_obj;
        for (int l_index = 0; l_index < l_continents_list.size(); l_index++) {
            int l_special_num = parseInt(l_continents_list.get(l_index).split(" ")[1]);
            String l_continent_name = l_continents_list.get(l_index).split(" ")[0];
            l_continent_obj = new Continent(l_continent_name, GameConstants.D_DEFAULT_IS_CONQUERED, l_special_num, null);
            l_continents.add(l_continent_obj);
        }

        this.d_continents = l_continents;

    }

    /**
     * Function returns continent ID given its name.
     *
     * @param p_continent_name continent name.
     * @return index of a country.
     */
    public int getContinentIDfromName(String p_continent_name) {

        List<GameMap.Continent> l_continents = this.getContinentObjects();
        for (int l_index = 0; l_index < l_continents.size(); l_index++) {
            if (l_continents.get(l_index).getContinentName().compareTo(p_continent_name) == 0) {
                return l_index + 1;
            }
        }

        return -1;
    }

    /**
     * Function returns country objects.
     *
     * @return list of country objects.
     */
    public List<Country> getCountryObjects() {
        return this.d_countries;
    }

    /**
     * Function returns continent objects.
     *
     * @return list of continent objects.
     */
    public List<Continent> getContinentObjects() {
        return this.d_continents;
    }

    /**
     * Function returns list of bordering countries.
     *
     * @return list of bordering countries.
     */
    public LinkedHashMap<Integer, List<Integer>> getBorders() {
        return this.d_borders;
    }
    
    public void updateCountryObject(Country p_country){
       List<Country> l_countries = this.getCountryObjects();
       
       for(int l_index = 0; l_index < l_countries.size(); l_index++){
           if(l_countries.get(l_index).getCountryName().compareTo(p_country.getCountryName())==0){
               l_countries.set(l_index, p_country);
           }
       }
    }
        

    /**
     * Function is used to display continents, countries and armies in
     * correspondence to the players.
     */
    
    public void showMap(boolean p_show_player) {
        List<Country> l_countries = this.getCountryObjects();
        List<Continent> l_continents = this.getContinentObjects();
        LinkedHashMap<Integer, List<Integer>> l_borders = this.getBorders();

        List<Integer> l_country_border_list = new ArrayList<Integer>();
        System.out.println("");
        System.out.println("!!!YOUR GAME MAP!!!!");

        for (int l_index = 0; l_index < l_countries.size(); l_index++) {
            String l_player_name = "";

            if(p_show_player){
                if (l_countries.get(l_index).getPlayerName() == null) {
                l_player_name = "Not Assigned";
            } else {
                l_player_name = l_countries.get(l_index).getPlayerName();
            }
            }
            
            System.out.println();
            
            if(p_show_player){
                System.out.format("%-35s %-35s %-35s %-5s", "Player name", "Country", "Continent", "Army Count");
            }else{
                System.out.format("%-35s %-35s %-5s", "Country", "Continent", "Army Count");
            }
            
            System.out.println();
            
            if(p_show_player){
                System.out.format("%-35s %-35s %-35s %-5s", l_player_name, l_countries.get(l_index).getCountryName(), l_countries.get(l_index).getContinentName(), l_countries.get(l_index).getArmyCount());
            }else{
                System.out.format("%-35s %-35s %-5s", l_countries.get(l_index).getCountryName(), l_countries.get(l_index).getContinentName(), l_countries.get(l_index).getArmyCount());
            }
            
            System.out.println();
            int l_continent_id = getContinentIDfromName(l_countries.get(l_index).getContinentName());

            //refactored
            l_country_border_list = l_borders.get(l_countries.get(l_index).getCountryID());

            System.out.println("Neighbors: ");
            for (int l_j_index = 0; l_j_index < l_countries.size(); l_j_index++) {
                if (l_country_border_list.subList(1, l_country_border_list.size()).contains(l_countries.get(l_j_index).getCountryID())) {
                    System.out.println(l_countries.get(l_j_index).getCountryName() + ", ");
                }
            }
        }
    }

    /**
     * Function returns country object.
     *
     * @param p_country_id country ID.
     * @return country object.
     */
    public Country getCountryById(int p_country_id) {
        for (Country l_country_obj : d_countries) {
            if (l_country_obj.getCountryID() == p_country_id) {
                return l_country_obj;
            }
        }

        return null;
    }

    /**
     * Function validates the map, ensuring the continents and countries are set
     * in place.
     *
     * @return boolean value of true or false.
     * @throws Exception
     */
    public boolean validateGameMap() throws Exception {
        if (d_continents.isEmpty()) {
            throw new GameException(GameMessageConstants.D_MAP_EMPTY_CONTINENTS);
        }

        if (d_countries.isEmpty()) {
            throw new GameException(GameMessageConstants.D_MAP_EMPTY_COUNTRIES);
        }

        if (d_borders.isEmpty()) {
            throw new GameException(GameMessageConstants.D_MAP_EMPTY_BORDERS);
        }
        for (Country l_country_obj : getCountryObjects()) {
            List<Integer> l_country_border_list = d_borders.get(l_country_obj.getCountryID());
            l_country_border_list = l_country_border_list.subList(1, l_country_border_list.size());
            if (l_country_border_list == null || l_country_border_list.isEmpty()) {
                throw new GameException("Country: " + l_country_obj.getCountryName() + " " + GameMessageConstants.D_MAP_COUNTRY_EMPTY_BORDERS);
            }
        }

        return validateContinents();
    }

    /**
     * Function validates the Continents ensuring they are set in place.
     *
     * @return boolean value of true or false.
     * @throws Exception
     */
    private boolean validateContinents() throws Exception {
        boolean isCountriesInterConnected = true;

        for (Continent l_continent_obj : d_continents) {
            if (l_continent_obj.getCountryIDList() == null || l_continent_obj.getCountryIDList().isEmpty()) {
                throw new GameException("Continent: " + l_continent_obj.getContinentName() + " list of " + GameMessageConstants.D_MAP_EMPTY_COUNTRIES);
            }

            if (!validateContinentConnectivity(l_continent_obj)) {
                isCountriesInterConnected = false;
            }
        }

        return isCountriesInterConnected;
    }

    /**
     *
     * @param p_continent_obj continent object.
     * @return boolean value based on Continent validation.
     * @throws Exception
     */
    private boolean validateContinentConnectivity(Continent p_continent_obj) throws Exception {
        LinkedHashMap<Integer, Boolean> l_country_map = new LinkedHashMap<>();

        for (int l_country_id : p_continent_obj.getCountryIDList()) {
            l_country_map.put(l_country_id, false);
        }

        validateCountryAdjacency(getCountryById(p_continent_obj.getCountryIDList().get(0)), l_country_map, p_continent_obj);

        for (Map.Entry<Integer, Boolean> l_country_entry : l_country_map.entrySet()) {
            if (!l_country_entry.getValue()) {
                Country l_country_obj = getCountryById(l_country_entry.getKey());
                throw new GameException("Country: " + l_country_obj.getCountryName() + " " + GameMessageConstants.D_MAP_COUNTRY_INVALID_BORDERS);
            }
        }

        return !l_country_map.containsValue(false);
    }

    /**
     *
     * @param l_country_obj country object.
     * @param p_country_map holds country objects to check country connectivity.
     * @param p_continent_obj continent object.
     */
    private void validateCountryAdjacency(Country l_country_obj, LinkedHashMap<Integer, Boolean> p_country_map, Continent p_continent_obj) {
        p_country_map.put(l_country_obj.getCountryID(), true);

        for (int l_country_id : p_continent_obj.getCountryIDList()) {
            List<Integer> l_adjacent_ids = getBorders().get(l_country_obj.getCountryID());
            l_adjacent_ids = l_adjacent_ids.subList(1, l_adjacent_ids.size());
            if (l_adjacent_ids.contains(l_country_id)) {
                if (!p_country_map.get(l_country_id)) {
                    validateCountryAdjacency(getCountryById(l_country_id), p_country_map, p_continent_obj);
                }
            }
        }
    }

}
