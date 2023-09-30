/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package mapparser;

import java.util.ArrayList;
import java.util.List;
import gameplay.*;
import java.io.File;
import java.util.Scanner;
import constants.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import gameutils.*;
import static java.lang.Integer.parseInt;
import constants.*;
import java.util.Arrays;

/**
 *
 * @author USER
 */
public class Map {

    public List<List<Integer>> d_borders = new ArrayList<List<Integer>>();
    public List<Country> d_countries = new ArrayList<Country>();
    public List<Continent> d_continents = new ArrayList<Continent>();

    public Map(String p_file_path) {

        this.loadBorders(p_file_path);
        this.loadContinents(p_file_path);
        this.loadCountries(p_file_path);
    }

    class Continent {

        public String d_continent_name;
        public boolean d_is_continent_conquered;
        public int d_special_number;

        public Continent(String p_continent_name, boolean p_is_continent_conquered, int p_special_number) {
            this.d_continent_name = p_continent_name;
            this.d_is_continent_conquered = p_is_continent_conquered;
            this.d_special_number = p_special_number;
        }

        public void setIsContinentConqered(boolean p_value) {
            this.d_is_continent_conquered = p_value;

        }

        public int getSpecialNumber() {
            return this.d_special_number;
        }

    }

    class Country {

        public int d_country_id;
        public String d_country_name;
        public int d_army_count;
        public boolean d_is_country_conquered;
        public Continent d_continent_obj;

        public Country(int p_country_id, String p_country_name, boolean p_is_country_conquered, int p_army_count, Continent p_continent_obj) {
            this.d_country_id = p_country_id;
            this.d_country_name = p_country_name;
            this.d_is_country_conquered = p_is_country_conquered;
            this.d_army_count = p_army_count;
            this.d_continent_obj = p_continent_obj;
        }

        public void setIsCountryConqered(boolean p_value) {
            this.d_is_country_conquered = p_value;

        }

        public String getContinentNameFromCountry() {
            return this.d_continent_obj.d_continent_name;
        }

    }

    public void loadBorders(String p_file_path) {

        //load border array from map file
        List<String> l_borders_list = MapCommonUtils.getMapDetails(p_file_path, "borders", "end");

        List<List<Integer>> l_borders = new ArrayList<List<Integer>>();

        ArrayList<Integer> l_borders_sub;
        List<String> l_nested_array;

        for (int l_i = 0; l_i < l_borders_list.size(); l_i++) {
            l_nested_array = Arrays.asList(l_borders_list.get(l_i).split(" "));
            l_borders_sub = new ArrayList<Integer>();
            for (int l_j = 0; l_j < l_nested_array.size(); l_j++) {
                l_borders_sub.add(parseInt(l_nested_array.get(l_j)));
            }
            l_borders.add(l_borders_sub);
        }

        this.d_borders = l_borders;
    }

    public void loadCountries(String p_file_path) {
        //create country objects and link it with continent objects

        List<Continent> l_continents = getContinents();

        List<Country> l_countries = new ArrayList<Country>();
        List<String> l_countries_list = MapCommonUtils.getMapDetails(p_file_path, "countries", "borders");

        for (int l_i = 0; l_i < l_countries_list.size(); l_i++) {
            int l_country_id = parseInt(l_countries_list.get(l_i).split(" ")[0]);
            String l_country_name = l_countries_list.get(l_i).split(" ")[1];
            int l_continent_id = parseInt(l_countries_list.get(l_i).split(" ")[2]) - 1;
            Continent l_continent_obj = l_continents.get(l_continent_id);
            Country l_country_obj = new Country(l_country_id, l_country_name, GameConstants.D_DEFAULT_IS_CONQUERED, GameConstants.D_DEFAULT_ARMY_COUNT, l_continent_obj);
            l_countries.add(l_country_obj);
        }

        this.d_countries = l_countries;
    }

    public void loadContinents(String p_file_path) {
        //create continent objects

        List<Continent> l_continents = new ArrayList<Continent>();
        List<String> l_continents_list = MapCommonUtils.getMapDetails(p_file_path, "continents", "countries");

        for (int l_i = 0; l_i < l_continents_list.size(); l_i++) {
            int l_special_num = parseInt(l_continents_list.get(l_i).split(" ")[1]);
            String l_continent_name = l_continents_list.get(l_i).split(" ")[0];
            Continent l_continent_obj = new Continent(l_continent_name, GameConstants.D_DEFAULT_IS_CONQUERED, l_special_num);
            l_continents.add(l_continent_obj);
        }

        this.d_continents = l_continents;

    }

    public List<Country> getCountries() {
        return this.d_countries;
    }

    public List<Continent> getContinents() {
        return this.d_continents;
    }

    public List<List<Integer>> getBorders() {
        return this.d_borders;
    }
    
    public void showMap(){
        List<Country> l_countries = this.getCountries();
        List<Continent> l_continents = this.getContinents();
    }

}
