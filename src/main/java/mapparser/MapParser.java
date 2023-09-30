/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package mapparser;

import constants.GameMessageConstants;
import gameplay.GameEngine;
import gameutils.GameException;
import java.util.List;
import mapparser.Map.Continent;
import mapparser.Map.Country;

/**
 *
 * @author USER
 */
public class MapParser {

    public static void loadMap(String p_file_path) {

        //create map object
        Map l_map = new Map(null, null, null);

        //to inialise map data members
        l_map.loadBorders(p_file_path);
        l_map.loadContinents(p_file_path);
        l_map.loadCountries(p_file_path);

        List<Country> l_countries = l_map.getCountries();
        List<Continent> l_continents = l_map.getContinents();
    }

}
