package mapparser;

import constants.GameConstants;
import constants.GameMessageConstants;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import static java.lang.Integer.parseInt;

import java.util.*;

/**
 *
 * @author USER
 */
public class MapEditor {

    public void initialiseMapEditingPhase() {

        //get user input
        //create new map object
        try {

            BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));

            // validate user input map commands commands
            System.out.printf("Map Editor Menu!!!");

            String l_map_command = l_reader.readLine();
            do {

                switch (l_map_command.split(" ")[0]) {
                    case "editmap":
                        String l_map_path = l_map_command.split(" ")[1];
                        //check if this path exists, if not, create a new map file and ask user to enter map data
                        try {
                            File l_file = new File(l_map_path);
                            if (l_file.exists()) {
                                // load the map (create a map object)
                                Map l_map = new Map(l_map_path);
                            } else {
                                l_file.createNewFile();
                                Map l_map = this.initialiseMapFile(l_file);
                                this.editMap(l_map,l_file);
                            }
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        
                    case "":
                        break;

                    default:
                        System.out.println("Enter Valid Input!! Type 1 -  6 - Exit");
                }

                System.out.printf("Type 1 - - 6 - Exit");
                l_map_command = l_reader.readLine();

            } while (l_map_command.compareTo("exit") != 0);

            //} catch (GameException e) {
            // System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(GameMessageConstants.D_INTERNAL_ERROR);
        }
    }
    
    
    public Map initialiseMapFile(File p_file){
        Map l_map = new Map(p_file.getAbsolutePath());
        
        try {
            FileOutputStream l_fos = new FileOutputStream(p_file);
            BufferedWriter l_bw = new BufferedWriter(new OutputStreamWriter(l_fos));

            l_bw.write("[continents]");
            l_bw.newLine();
            l_bw.write("[countries]");
            l_bw.newLine();
            l_bw.write("[borders]");
            l_bw.newLine();
            
        } catch(Exception e){
            System.out.println(e);
        }
        
        
        return l_map;
    }

    public void editMap(Map p_map,File p_file) {
        try {

            BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));

            // validate user input map commands commands
            System.out.printf("Map Editor Menu!!!");

            String l_map_command = l_reader.readLine();
            do {

                switch (l_map_command.split(" ")[0]) {
                    case "editcontinent":
                        p_map = this.editContinent(p_map, l_map_command);
                        break;
                    case "editcountry":
                        p_map = this.editCountry(p_map, l_map_command);
                        break;
                    case "editneighbour":
                        p_map = this.editBorders(p_map, l_map_command);
                        break;
                    case "showmap":
                        p_map.showMap();
                        break;
                    case "savemap":
                        String l_map_file = l_map_command.split(" ")[1];
                        MapParser l_map_parser_obj = new MapParser(l_map_file);
                        p_map = l_map_parser_obj.loadMap();
                        break;

                    default:
                        System.out.println("Enter Valid Input!! Type 1 - Edit Continent - 2 - Edit Country - 3 - Edit Borders - 4 - Show Map  - 5 - Upload a map file - 6 - Exit");
                }

                System.out.printf("Type 1 - Edit Continent - 2 - Edit Country - 3 - Edit Borders - 4 - Show Map  - 5 - Upload a map file - 6 - Exit");
                l_map_command = l_reader.readLine();

            } while (l_map_command.compareTo("exit") != 0);

            //} catch (GameException e) {
            // System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(GameMessageConstants.D_INTERNAL_ERROR);
        }
    }

    public Map editCountry(Map p_map, String p_map_command) {

        //editcountry -add countryID continentID -remove countryID
        List<Map.Country> l_countries = p_map.getCountryObjects();
        List<Map.Continent> l_continents = p_map.getContinentObjects();
        List<List<Integer>> l_borders = p_map.getBorders();

        List<String> l_command_arr = Arrays.asList(p_map_command.split(" "));

        String l_country_name = "";
        String l_continent_name = "";

        if (p_map_command.contains("add")) {
            l_country_name = l_command_arr.get(l_command_arr.indexOf("-add") + 1);
            l_continent_name = l_command_arr.get(l_command_arr.indexOf("-add") + 2);

            //creating country object
            int l_country_id = l_countries.get(l_countries.size() - 1).d_country_id + 1;

            Map.Country l_country_obj = p_map.new Country(l_country_id, l_country_name,
                    GameConstants.D_DEFAULT_IS_CONQUERED, GameConstants.D_DEFAULT_ARMY_COUNT,
                    l_continent_name);

            //updating map.countries list
            p_map.d_countries.add(l_country_obj);

            //updating continent object's country list
            for (int l_i = 0; l_i < l_continents.size(); l_i++) {
                if (l_continents.get(l_i).d_continent_name.compareTo(l_country_name) == 0) {
                    p_map.d_continents.get(l_i).d_country_list.add(l_country_id);
                    break;
                }
            }
        }

        if (p_map_command.contains("remove")) {

            l_country_name = l_command_arr.get(l_command_arr.indexOf("-remove") + 1);
            int l_country_id = -1;

            //removing country from map.countries list
            for (int l_i = 0; l_i < l_countries.size(); l_i++) {
                if (l_countries.get(l_i).d_country_name.compareTo(l_country_name) == 0) {
                    l_country_id = l_countries.get(l_i).d_country_id;
                    p_map.d_countries.remove(l_i);
                    break;
                }
            }
            //removing country from continent's country list

            for (int l_i = 0; l_i < l_continents.size(); l_i++) {
                if (l_continents.get(l_i).d_country_list.contains(l_country_id)) {
                    p_map.d_continents.get(l_i).d_country_list.remove(Integer.valueOf(l_country_id));
                    break;
                }
            }

            //removing country in borders list
            for (int l_i = 0; l_i < l_borders.size(); l_i++) {
                if (l_borders.get(l_i).contains(l_country_id)) {
                    p_map.d_borders.get(l_i).remove(Integer.valueOf(l_country_id));
                }
            }

        }

        this.modifyMapFile(p_map);
        
        return p_map;
    }

    public Map editContinent(Map p_map, String p_map_command) {
        // editcontinent -add continentID continentvalue -remove continentID

        List<Map.Country> l_countries = p_map.getCountryObjects();
        List<Map.Continent> l_continents = p_map.getContinentObjects();

        List<String> l_command_arr = Arrays.asList(p_map_command.split(" "));

        String l_continent_name = "";
        int l_continent_value = -1;

        if (p_map_command.contains("-add")) {

            l_continent_name = l_command_arr.get(l_command_arr.indexOf("-add") + 1);
            l_continent_value = parseInt(l_command_arr.get(l_command_arr.indexOf("-add") + 2));

            //creating continent object
            Map.Continent l_continent_obj = p_map.new Continent(l_continent_name, GameConstants.D_DEFAULT_IS_CONQUERED, l_continent_value, null);

            p_map.d_continents.add(l_continent_obj);
        }
        if (p_map_command.contains("remove")) {
            l_continent_name = l_command_arr.get(l_command_arr.indexOf("-remove") + 1);

            //removing object from  map.continents list
            List<Integer> l_country_list = new ArrayList<Integer>();

            for (int l_i = 0; l_i < l_continents.size(); l_i++) {
                if (l_continents.get(l_i).d_continent_name.compareTo(l_continent_name) == 0) {
                    l_country_list = l_continents.get(l_i).d_country_list;
                    p_map.d_continents.remove(l_i);
                    break;
                }
            }

            //remove countries that belong to the continent
            for (int l_i = 0; l_i < l_countries.size(); l_i++) {
                if (l_country_list.contains(l_countries.get(l_i).d_country_id)) {
                    p_map.d_countries.remove(l_i);
                }
            }

        }
        this.modifyMapFile(p_map);
        return p_map;
    }

    public Map editBorders(Map p_map, String p_map_command) {

        //editneighbor -add countryID neighborcountryID -remove countryID neighborcountryID
        List<List<Integer>> l_borders = p_map.getBorders();
        List<String> l_command_arr = Arrays.asList(p_map_command.split(" "));

        int l_country_id = -1;
        int l_neighbor_country_id = -1;

        if (p_map_command.contains("-add")) {
            l_country_id = parseInt(l_command_arr.get(l_command_arr.indexOf("-add") + 1));
            l_neighbor_country_id = parseInt(l_command_arr.get(l_command_arr.indexOf("-add") + 2));

            p_map.d_borders.get(l_country_id - 1).add(l_neighbor_country_id);

        }
        if (p_map_command.contains("-remove")) {
            l_country_id = parseInt(l_command_arr.get(l_command_arr.indexOf("-remove") + 1));
            l_neighbor_country_id = parseInt(l_command_arr.get(l_command_arr.indexOf("-remove") + 2));

            p_map.d_borders.get(l_country_id - 1).remove(Integer.valueOf(l_neighbor_country_id));
        }
        this.modifyMapFile(p_map);
        return p_map;
    }

    public void modifyMapFile( Map p_map) {

        List<Map.Country> l_countries = p_map.getCountryObjects();
        List<Map.Continent> l_continents = p_map.getContinentObjects();
        List<List<Integer>> l_borders = p_map.getBorders();

       
        //make a new map file
        //add new content
        //overwrite map file
        String l_map_path = "";
        
        try {
            File l_fout = new File(l_map_path);
            FileOutputStream l_fos = new FileOutputStream(l_fout);

            BufferedWriter l_bw = new BufferedWriter(new OutputStreamWriter(l_fos));

            l_bw.write("[continents]");
            l_bw.newLine();

            for (int l_i = 0; l_i < l_continents.size(); l_i++) {
                Map.Continent l_continent_obj = l_continents.get(l_i);
                l_bw.write(l_continent_obj.d_continent_name + " " + l_continent_obj.d_special_number);
                l_bw.newLine();
            }

            l_bw.write("[countries]");
            l_bw.newLine();

            for (int l_i = 0; l_i < l_countries.size(); l_i++) {
                Map.Country l_country_obj = l_countries.get(l_i);
                int l_continent_id = p_map.getContinentIDfromName(l_country_obj.d_continent_name);
                l_bw.write(l_country_obj.d_country_id + " " + l_country_obj.d_country_name + " " + l_continent_id);
                l_bw.newLine();
            }

            l_bw.write("[borders]");
            l_bw.newLine();

            for (int l_i = 0; l_i < l_borders.size(); l_i++) {
                for (int l_j = 0; l_j < l_borders.get(l_i).size(); l_j++) {
                    l_bw.write(l_borders.get(l_i).get(l_j) + " ");
                }
                l_bw.newLine();
            }

            l_bw.close();

        } catch (IOException e) {
            System.out.println(e);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    

    public void saveMap(Map p_map) {

    }

}
