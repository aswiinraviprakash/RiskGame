package mapparser;

import java.util.ArrayList;
import java.util.List;
import constants.*;
import gameutils.*;
import static java.lang.Integer.parseInt;
import java.util.Arrays;

/**
 *
 * @author USER
 */
public class GameMap {

    public List<List<Integer>> d_borders = new ArrayList<List<Integer>>();
    public List<Country> d_countries = new ArrayList<Country>();
    public List<Continent> d_continents = new ArrayList<Continent>();

    public GameMap(String p_file_path) {
        this.loadBorders(p_file_path);
        this.loadContinents(p_file_path);
        this.loadCountries(p_file_path);

        //pass continent obj to add countries to country list
        for (int l_index = 0; l_index < this.d_continents.size(); l_index++) {
            ArrayList<Integer> l_country_list = this.addCountryToContinentObj(this.d_continents.get(l_index));
            this.d_continents.get(l_index).d_country_list = (ArrayList) l_country_list.clone();
        }
    }

    public class Continent {

        private String d_continent_name;
        private boolean d_is_continent_conquered;
        private int d_special_number;
        private List<Integer> d_country_list;

        public Continent(String p_continent_name, boolean p_is_continent_conquered, int p_special_number, List<Integer> p_country_list) {

            this.d_continent_name = p_continent_name;
            this.d_is_continent_conquered = p_is_continent_conquered;
            this.d_special_number = p_special_number;
            this.d_country_list = p_country_list;
        }

        public String getContinentName() {
            return this.d_continent_name;
        }

        public boolean getIsContinentConquered() {
            return this.d_is_continent_conquered;
        }

        public int getSpecialNumber() {
            return this.d_special_number;
        }

        public List<Integer> getCountryIDList() {
            return this.d_country_list;
        }

        public void setIsContinentConqered(boolean p_value) {
            this.d_is_continent_conquered = p_value;

        }

    }

    public class Country {

        private int d_country_id;
        private String d_country_name;
        private int d_army_count;
        private boolean d_is_country_conquered;
        private String d_continent_name;

        public Country(int p_country_id, String p_country_name, boolean p_is_country_conquered, int p_army_count, String p_continent_name) {
            this.d_country_id = p_country_id;
            this.d_country_name = p_country_name;
            this.d_is_country_conquered = p_is_country_conquered;
            this.d_army_count = p_army_count;
            this.d_continent_name = p_continent_name;
        }

        public int getCountryID() {
            return this.d_country_id;
        }

        public String getCountryName() {
            return this.d_country_name;
        }

        public int getArmyCount() {
            return this.d_army_count;
        }

        public boolean getIsCountryConquered() {
            return this.d_is_country_conquered;
        }

        public String getContinentName() {
            return this.d_continent_name;
        }

        public void setIsCountryConqered(boolean p_value) {
            this.d_is_country_conquered = p_value;

        }

        public void setArmyCount(int p_army_count) {
            this.d_army_count = p_army_count;
        }

    }

    public ArrayList<Integer> addCountryToContinentObj(Continent p_continent) {

        List<Continent> l_continents = this.getContinentObjects();
        List<Country> l_countries = this.getCountryObjects();

        ArrayList<Integer> l_country_id_list = new ArrayList<Integer>();

        for (int l_index = 0; l_index < l_countries.size(); l_index++) {
            if (l_countries.get(l_index).d_continent_name.compareTo(p_continent.d_continent_name) == 0) {
                l_country_id_list.add(l_countries.get(l_index).d_country_id);
            }
        }

        return l_country_id_list;

    }

    public void loadBorders(String p_file_path) {

        //load border array from map file
        List<String> l_borders_list = MapCommonUtils.getMapDetails(p_file_path, "borders", "end");

        List<List<Integer>> l_borders = new ArrayList<List<Integer>>();

        ArrayList<Integer> l_borders_sub;
        List<String> l_nested_array;

        for (int l_index = 0; l_index < l_borders_list.size(); l_index++) {
            l_nested_array = Arrays.asList(l_borders_list.get(l_index).split(" "));
            l_borders_sub = new ArrayList<Integer>();
            for (int l_j_index = 0; l_j_index < l_nested_array.size(); l_j_index++) {
                l_borders_sub.add(parseInt(l_nested_array.get(l_j_index)));
            }
            l_borders.add(l_borders_sub);
        }

        this.d_borders = l_borders;
    }

    public void loadCountries(String p_file_path) {

        List<Continent> l_continents = getContinentObjects();

        List<Country> l_countries = new ArrayList<Country>();
        List<String> l_countries_list = MapCommonUtils.getMapDetails(p_file_path, "countries", "borders");

        for (int l_index = 0; l_index < l_countries_list.size(); l_index++) {
            int l_country_id = parseInt(l_countries_list.get(l_index).split(" ")[0]);
            String l_country_name = l_countries_list.get(l_index).split(" ")[1];
            int l_continent_id = parseInt(l_countries_list.get(l_index).split(" ")[2]) - 1;
            Continent l_continent_obj = l_continents.get(l_continent_id);
            String l_continent_name = l_continent_obj.d_continent_name;
            Country l_country_obj = new Country(l_country_id, l_country_name, GameConstants.D_DEFAULT_IS_CONQUERED, GameConstants.D_DEFAULT_ARMY_COUNT, l_continent_name);
            l_countries.add(l_country_obj);
        }

        this.d_countries = l_countries;
    }

    public void loadContinents(String p_file_path) {
        //create continent objects

        List<Continent> l_continents = new ArrayList<Continent>();
        List<String> l_continents_list = MapCommonUtils.getMapDetails(p_file_path, "continents", "countries");
        Continent l_continent_obj;
        for (int l_index = 0; l_index < l_continents_list.size(); l_index++) {
            int l_special_num = parseInt(l_continents_list.get(l_index).split(" ")[1]);
            String l_continent_name = l_continents_list.get(l_index).split(" ")[0];
            l_continent_obj = new Continent(l_continent_name, GameConstants.D_DEFAULT_IS_CONQUERED, l_special_num, null);
            l_continents.add(l_continent_obj);
        }

        this.d_continents = l_continents;

    }

    public int getContinentIDfromName(String p_continent_name) {

        List<GameMap.Continent> l_continents = this.getContinentObjects();
        for (int l_index = 0; l_index < l_continents.size(); l_index++) {
            if (l_continents.get(l_index).d_continent_name.compareTo(p_continent_name) == 0) {
                return l_index + 1;
            }
        }

        return -1;
    }

    public List<Country> getCountryObjects() {
        return this.d_countries;
    }

    public List<Continent> getContinentObjects() {
        return this.d_continents;
    }

    public List<List<Integer>> getBorders() {
        return this.d_borders;
    }

    public void showMap() {
        List<Country> l_countries = this.getCountryObjects();
        List<Continent> l_continents = this.getContinentObjects();
    }

}
