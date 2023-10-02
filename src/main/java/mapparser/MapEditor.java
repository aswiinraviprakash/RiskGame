package mapparser;

import constants.GameConstants;
import constants.GameMessageConstants;
import gameutils.GameException;
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
            System.out.printf("Map Menu!!!  Type 1 - edit Map (editmap -mapfilename) -  2 - Exit");

            String l_map_command = l_reader.readLine();
            do {
                System.out.printf("Type 1 - editmap (editmap -mapfilename) -  2 - Exit");

                switch (l_map_command.split(" ")[0]) {
                    case "editmap":
                        String l_map_path = l_map_command.split(" ")[1];
                        File l_file;
                        File l_file_dir = new File("").getCanonicalFile();
                        l_map_path = l_file_dir.getParent() + "\\RiskGame\\src\\main\\java\\mapfiles\\" + l_map_path;
                        //check if this path exists, if not, create a new map file and ask user to enter map data
                        try {
                            l_file = new File(l_map_path);
                            if (l_file.exists()) {
                                // load the map (create a map object)
                                Map l_map = new Map(l_map_path);
                                this.editMap(l_map, l_map_path);
                            } else {
                                Map l_map = this.initialiseMapFile(l_map_path);
                                this.editMap(l_map, l_map_path);
                            }
                        } catch (Exception e) {
                            System.out.println(e);
                        }

                        break;

                    default:
                        System.out.println("Enter Valid Input!! Type 1 - Edit Map -  2 - Exit");
                }

                l_map_command = l_reader.readLine();

            } while (l_map_command.compareTo("exit") != 0);
        } catch (Exception e) {
            System.out.println(GameMessageConstants.D_INTERNAL_ERROR);
        }
    }

    public void editMap(Map p_map, String p_map_path) {
        try {

            BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));

            // validate user input map commands commands
            System.out.printf("Map Editor Menu!!! ");
            System.out.printf("Type 1 - Edit Continent - 2 - Edit Country - 3 - Edit Borders - 4 - Show Map  - 5 - Upload a map file - 6 - Exit");
            String l_map_command = l_reader.readLine();
            do {
                System.out.printf("Type 1 - Edit Continent - 2 - Edit Country - 3 - Edit Borders - 4 - Show Map  - 5 - Upload a map file - 6 - Exit");
                switch (l_map_command.split(" ")[0]) {
                    case "editcontinent":
                        p_map = this.editContinent(p_map, l_map_command, p_map_path);
                        break;
                    case "editcountry":
                        p_map = this.editCountry(p_map, l_map_command, p_map_path);
                        break;
                    case "editneighbor":
                        p_map = this.editBorders(p_map, l_map_command, p_map_path);
                        break;
                    case "showmap":
                        p_map.showMap();
                        break;
                    case "savemap":
                        // String l_map_file = l_map_command.split(" ")[1];
                        // MapParser l_map_parser_obj = new MapParser(l_map_file);
                        // p_map = l_map_parser_obj.loadMap();
                        break;
                    default:
                        System.out.println("Enter Valid Input!! Type 1 - Edit Continent - 2 - Edit Country - 3 - Edit Borders - 4 - Show Map  - 5 - Upload a map file - 6 - Exit");
                }
                l_map_command = l_reader.readLine();

            } while (l_map_command.compareTo("exit") != 0);
        } catch (Exception e) {
            System.out.println(GameMessageConstants.D_INTERNAL_ERROR);
        }
    }

    public Map initialiseMapFile(String p_map_path) {

        try {
            File l_file_obj = new File(p_map_path);
            FileOutputStream l_fos = new FileOutputStream(l_file_obj);

            BufferedWriter l_bw = new BufferedWriter(new OutputStreamWriter(l_fos));

            l_bw.write("[continents]");
            l_bw.newLine();
            l_bw.newLine();
            l_bw.write("[countries]");
            l_bw.newLine();
            l_bw.newLine();
            l_bw.write("[borders]");
            l_bw.newLine();

            l_bw.close();
            System.out.println("A new map created!");

        } catch (Exception e) {
            System.out.println(e);
        }

        Map l_map = new Map(p_map_path);

        return l_map;
    }

    public Map editCountry(Map p_map, String p_map_command, String p_file_path) {

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

            //check if the continent exists
            boolean l_continent_exists = false;

            for (int l_i = 0; l_i < l_continents.size(); l_i++) {
                if (l_continents.get(l_i).d_continent_name.compareTo(l_continent_name) == 0) {
                    l_continent_exists = true;
                }
            }

            if (l_continent_exists) {
                //creating country object
                int l_country_id = 1;
                if (l_countries.size() != 0) {
                    l_country_id = l_countries.get(l_countries.size() - 1).d_country_id + 1;
                }

                // System.out.println(l_country_id);
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

                //adding new country to borders
                ArrayList<Integer> l_new_border = new ArrayList<Integer>();
                l_new_border.add(l_country_id);
                p_map.d_borders.add(l_new_border);

            } else {
                System.out.println("continent does not exist");
            }
            p_map = this.modifyMapFile(p_map, p_file_path);
        }

        if (p_map_command.contains("remove")) {

            l_countries = p_map.getCountryObjects();
            l_continents = p_map.getContinentObjects();
            l_borders = p_map.getBorders();

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
            p_map.d_borders.remove(l_country_id - 1);

            for (int l_i = 0; l_i < l_borders.size(); l_i++) {
                if (l_borders.get(l_i).contains(l_country_id)) {
                    p_map.d_borders.get(l_i).remove(Integer.valueOf(l_country_id));
                }
            }

            p_map = this.modifyMapFile(p_map, p_file_path);

        }

        return p_map;
    }

    public Map editContinent(Map p_map, String p_map_command, String p_file_path) {
        // editcontinent -add continentID continentvalue -remove continentID

        List<Map.Country> l_countries = p_map.getCountryObjects();
        List<Map.Continent> l_continents = p_map.getContinentObjects();
        List<List<Integer>> l_borders = p_map.getBorders();

        List<String> l_command_arr = Arrays.asList(p_map_command.split(" "));

        String l_continent_name = "";
        int l_continent_value = -1;

        if (p_map_command.contains("-add")) {

            l_continent_name = l_command_arr.get(l_command_arr.indexOf("-add") + 1);
            l_continent_value = parseInt(l_command_arr.get(l_command_arr.indexOf("-add") + 2));

            //creating continent object
            Map.Continent l_continent_obj = p_map.new Continent(l_continent_name, GameConstants.D_DEFAULT_IS_CONQUERED, l_continent_value, null);

            p_map.d_continents.add(l_continent_obj);

            p_map = this.modifyMapFile(p_map, p_file_path);

        }
        if (p_map_command.contains("-remove")) {
            l_countries = p_map.getCountryObjects();
            l_continents = p_map.getContinentObjects();
            l_borders = p_map.getBorders();

            l_continent_name = l_command_arr.get(l_command_arr.indexOf("-remove") + 1);

            //removing object from  map.continents list
            String l_continent_name_delete = "";

            for (int l_i = 0; l_i < l_continents.size(); l_i++) {
                if (l_continents.get(l_i).d_continent_name.compareTo(l_continent_name) == 0) {
                    l_continent_name_delete = l_continents.get(l_i).d_continent_name;
                    p_map.d_continents.remove(l_i);
                    break;
                }
            }

            l_continents = p_map.getContinentObjects();

            List<Map.Country> l_new_country_list = new ArrayList<Map.Country>();
            List<List<Integer>> l_new_border_list = new ArrayList<List<Integer>>();

            for (int l_i = 0; l_i < l_countries.size(); l_i++) {

                if (l_countries.get(l_i).d_continent_name.compareTo(l_continent_name_delete) != 0) {

                    l_new_country_list.add(l_countries.get(l_i));
                    System.out.println(l_borders.get(l_i));
                    l_new_border_list.add(l_borders.get(l_i));
                }
            }
            p_map.d_countries = l_new_country_list;
            p_map.d_borders = l_new_border_list;

            p_map = this.modifyMapFile(p_map, p_file_path);

        }

        return p_map;
    }

    public Map editBorders(Map p_map, String p_map_command, String p_file_path) {

        //editneighbor -add countryID neighborcountryID -remove countryID neighborcountryID
        List<List<Integer>> l_borders;
        List<Map.Country> l_countries;
        List<String> l_command_arr = Arrays.asList(p_map_command.split(" "));

        String l_country_name = "";
        String l_neighbor_country_name = "";

        int l_country_id = -1;
        int l_neighbor_country_id = -1;

        int l_country_index = -1;
        int l_country_neighbor_index = -1;

        if (p_map_command.contains("-add")) {

            l_borders = p_map.getBorders();
            l_countries = p_map.getCountryObjects();

            l_country_name = l_command_arr.get(l_command_arr.indexOf("-add") + 1);
            l_neighbor_country_name = l_command_arr.get(l_command_arr.indexOf("-add") + 2);

            for (int l_i = 0; l_i < l_countries.size(); l_i++) {
                if (l_countries.get(l_i).d_country_name.compareTo(l_country_name) == 0) {
                    l_country_id = l_countries.get(l_i).d_country_id;
                    l_country_index = l_i;
                }
                if (l_countries.get(l_i).d_country_name.compareTo(l_neighbor_country_name) == 0) {
                    l_neighbor_country_id = l_countries.get(l_i).d_country_id;
                    l_country_neighbor_index = l_i;
                }
            }

            p_map.d_borders.get(l_country_index).add(l_neighbor_country_id);

            p_map = this.modifyMapFile(p_map, p_file_path);

        }
        if (p_map_command.contains("-remove")) {

            l_borders = p_map.getBorders();
            l_countries = p_map.getCountryObjects();

            l_country_name = l_command_arr.get(l_command_arr.indexOf("-remove") + 1);
            l_neighbor_country_name = l_command_arr.get(l_command_arr.indexOf("-remove") + 2);

            for (int l_i = 0; l_i < l_countries.size(); l_i++) {
                if (l_countries.get(l_i).d_country_name.compareTo(l_country_name) == 0) {
                    l_country_id = l_countries.get(l_i).d_country_id;
                    l_country_index = l_i;
                }
                if (l_countries.get(l_i).d_country_name.compareTo(l_neighbor_country_name) == 0) {
                    l_neighbor_country_id = l_countries.get(l_i).d_country_id;
                    l_country_neighbor_index = l_i;
                }
            }

            p_map.d_borders.get(l_country_index).remove(Integer.valueOf(l_neighbor_country_id));
            p_map = this.modifyMapFile(p_map, p_file_path);
        }

        return p_map;
    }

    public Map modifyMapFile(Map p_map, String p_file_path) {

        List<Map.Country> l_countries = p_map.getCountryObjects();
        List<Map.Continent> l_continents = p_map.getContinentObjects();
        List<List<Integer>> l_borders = p_map.getBorders();

        Map l_new_map = null;

        //make a new map file
        //add new content
        //overwrite map file
        try {
            File l_fout = new File(p_file_path);
            FileOutputStream l_fos = new FileOutputStream(l_fout);

            BufferedWriter l_bw = new BufferedWriter(new OutputStreamWriter(l_fos));

            l_bw.write("[continents]");
            l_bw.newLine();

            for (int l_i = 0; l_i < l_continents.size(); l_i++) {
                Map.Continent l_continent_obj = l_continents.get(l_i);
                l_bw.write(l_continent_obj.d_continent_name + " " + l_continent_obj.d_special_number);
                l_bw.newLine();
            }
            l_bw.newLine();
            l_bw.write("[countries]");
            l_bw.newLine();

            for (int l_i = 0; l_i < l_countries.size(); l_i++) {
                Map.Country l_country_obj = l_countries.get(l_i);
                int l_continent_id = p_map.getContinentIDfromName(l_country_obj.d_continent_name);
                l_bw.write(l_country_obj.d_country_id + " " + l_country_obj.d_country_name + " " + l_continent_id);
                l_bw.newLine();
            }
            l_bw.newLine();
            l_bw.write("[borders]");
            l_bw.newLine();

            for (int l_i = 0; l_i < l_borders.size(); l_i++) {
                for (int l_j = 0; l_j < l_borders.get(l_i).size(); l_j++) {
                    l_bw.write(l_borders.get(l_i).get(l_j) + " ");
                }
                l_bw.newLine();
            }

            l_bw.close();

            l_new_map = new Map(p_file_path);

        } catch (IOException e) {
            System.out.println(e);
        } catch (Exception e) {
            System.out.println(e);
        }

        return l_new_map;
    }

    

    public void saveMap(Map p_map) {

    }

}
