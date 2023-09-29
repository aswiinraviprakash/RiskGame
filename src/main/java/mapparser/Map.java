/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package mapparser;

import java.util.ArrayList;
import java.util.List;
import gameplay.*;

/**
 *
 * @author USER
 */
public class Map {

    public static List<List<Integer>> d_borders = new ArrayList<List<Integer>>();
    public static List<Country> d_countries = new ArrayList<Country>();
    public static List<Continent> d_continents = new ArrayList<Continent>();

    public Map(List<List<Integer>> p_borders, List<Country> p_countries, List<Continent> p_continents) {
        this.d_borders = p_borders;
        this.d_countries = p_countries;
        this.d_continents = p_continents;
    }

    class Continent {

        public int d_continent_id;
        public String d_continent_name;
        public boolean d_is_continent_conquered;
        public int d_special_number;

        public Continent(int p_continent_id, String p_continent_name, boolean p_is_continent_conquered, int p_special_number) {
            this.d_continent_id = p_continent_id;
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

        public int getContinentIDFromCountry() {
            return this.d_continent_obj.d_continent_id;
        }
        

    }

   // public Country[] getCountries() {
        

    //}

   // public Continent[] getContinents() {

   // }
   
    
}
