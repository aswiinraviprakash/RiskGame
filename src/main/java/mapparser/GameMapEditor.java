package mapparser;

import constants.GameConstants;
import constants.GameMessageConstants;
import gameutils.GameCommandParser;
import gameutils.GameException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author USER
 */
public class GameMapEditor {

    public void initialiseMapEditingPhase() {

        //get user input
        //create new map object
        try {

            BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));

            // validate user input map commands commands
            System.out.println("Map Menu - Type editmap filename or exit to Exit");

            String l_map_command = l_reader.readLine();
            do {
                GameCommandParser l_command_parser = new GameCommandParser(l_map_command);
                String l_primary_command = l_command_parser.getPrimaryCommand();
                List<GameCommandParser.CommandDetails> l_command_details = l_command_parser.getParsedCommandDetails();

                switch (l_command_parser.getPrimaryCommand()) {
                    case "editmap":
                        String l_map_path = l_command_details.get(0).getCommandParameters().get(0);
                        File l_file;
                        File l_file_dir = new File("").getCanonicalFile();
                        l_map_path = l_file_dir.getParent() + GameConstants.D_MAP_DIRECTORY + l_map_path;
                        //check if this path exists, if not, create a new map file and ask user to enter map data
                        try {
                            l_file = new File(l_map_path);
                            if (l_file.exists()) {
                                // load the map (create a map object)
                                GameMap l_map = new GameMap(l_map_path);
                                this.editMap(l_map, l_map_path);
                            } else {
                                GameMap l_map = this.initialiseMapFile(l_map_path);
                                this.editMap(l_map, l_map_path);
                            }
                        } catch (Exception e) {
                            System.out.println(e);
                        }

                        break;

                    default:
                        System.out.println("Enter Valid Input!! Type editmap filename or exit to Exit");
                }

                System.out.println("Map Menu - Type editmap filename or exit to Exit");
                l_map_command = l_reader.readLine();

            } while (l_map_command.compareTo("exit") != 0);
        } catch (Exception e) {
            System.out.println(GameMessageConstants.D_INTERNAL_ERROR);
        }
    }

    public void editMap(GameMap p_map, String p_map_path) {
        try {

            BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));

            // validate user input map commands commands
            System.out.printf("Map Editor Menu!!! %n");
            System.out.printf("1 - Edit Continent (editcontinent -add -remove) - 2 - Edit Country (editcountry -add -remove) - 3 - Edit Borders (editneighbor -add -remove) - 4 - Show Map (showmap) - 5 - Save map (savemap -file) - 6 - Validate map (validatemap) - 7 - Exit (exit)");
            String l_map_command = l_reader.readLine();

            do {
                try {
                    GameCommandParser l_command_parser = new GameCommandParser(l_map_command);
                    String l_primary_command = l_command_parser.getPrimaryCommand();
                    List<GameCommandParser.CommandDetails> l_command_details = l_command_parser.getParsedCommandDetails();

                    int l_command_count = l_command_details.size();

                    switch (l_command_parser.getPrimaryCommand()) {
                        case "editcontinent":
                            for (int l_index = 0; l_index < l_command_count; l_index++) {
                                if (l_command_details.get(l_index).getHasCommandOption()) {
                                    p_map = this.editContinent(p_map, l_command_details.get(l_index).getCommandOption(), l_command_details.get(l_index).getCommandParameters());
                                } else {
                                    throw new GameException(GameMessageConstants.D_COMMAND_NO_OPTION_SUPPORT);
                                }

                            }
                            break;
                        case "editcountry":
                            for (int l_index = 0; l_index < l_command_count; l_index++) {
                                if (l_command_details.get(l_index).getHasCommandOption()) {
                                    p_map = this.editCountry(p_map, l_command_details.get(l_index).getCommandOption(), l_command_details.get(l_index).getCommandParameters());
                                } else {
                                    throw new GameException(GameMessageConstants.D_COMMAND_NO_OPTION_SUPPORT);
                                }
                            }
                            break;
                        case "editneighbor":
                            for (int l_index = 0; l_index < l_command_count; l_index++) {
                                if (l_command_details.get(l_index).getHasCommandOption()) {
                                    p_map = this.editBorders(p_map, l_command_details.get(l_index).getCommandOption(), l_command_details.get(l_index).getCommandParameters());
                                } else {
                                    throw new GameException(GameMessageConstants.D_COMMAND_NO_OPTION_SUPPORT);
                                }
                            }
                            break;
                        case "showmap":
                            p_map.showMap();
                            break;
                        case "savemap":
                            File l_file_dir = new File("").getCanonicalFile();
                            String l_map_path_input = l_file_dir.getParent() + GameConstants.D_MAP_DIRECTORY + l_command_details.get(0).getCommandParameters().get(0);
                            if (l_command_details.get(0).getCommandParameters().size() != 1) {
                                if (l_map_path_input.compareTo(p_map_path) == 0) {
                                    boolean l_is_map_valid = p_map.validateGameMap();
                                    if (l_is_map_valid) {
                                        p_map = this.modifyMapFile(p_map, p_map_path);
                                        System.out.println("Map file saved");
                                    } else {
                                        System.out.println("Something is wrong with the map, type 'exit' to leave map editor menu  and choose another map file using editmap command");
                                    }
                                } else {
                                    System.out.println("Please enter the correct map file name");
                                }
                            } else {
                                System.out.println("Please enter the file name");
                            }

                            break;
                        case "validatemap":
                            boolean l_is_map_valid = p_map.validateGameMap();
                              if (l_is_map_valid) {
                                 System.out.println("Something is wrong with the map, type 'exit' to leave map editor menu and choose another map file using editmap command");
                              }else{
                                  System.out.println("Map is incorrect");
                              }

                            break;
                        default:
                            System.out.printf("Enter Valid Input!!! - 1 - Edit Continent (editcontinent -add -remove) - 2 - Edit Country (editcountry -add -remove) - 3 - Edit Borders (editneighbor -add -remove) - 4 - Show Map (showmap) - 5 - Save map (savemap -file) - 6 - Validate map (validatemap) - 7 - Exit (exit)");
                    }

                } catch (GameException e) {
                    System.out.println(e.getMessage());
                } catch (Exception e) {
                    System.out.println(GameMessageConstants.D_INTERNAL_ERROR);
                }

                System.out.printf("%nProceed valid commands editcontinent / editcountry / editneighbor / validatemap / savemap / showmap %n%n");
                l_map_command = l_reader.readLine();

            } while (l_map_command.compareTo("exit") != 0);

        } catch (Exception e) {
            System.out.println(GameMessageConstants.D_INTERNAL_ERROR);
        }
    }

    public GameMap initialiseMapFile(String p_map_path) {

        try {
            File l_file_obj = new File(p_map_path);
            FileOutputStream l_output_stream = new FileOutputStream(l_file_obj);

            BufferedWriter l_buff_writer = new BufferedWriter(new OutputStreamWriter(l_output_stream));

            l_buff_writer.write("[continents]");
            l_buff_writer.newLine();
            l_buff_writer.newLine();
            l_buff_writer.write("[countries]");
            l_buff_writer.newLine();
            l_buff_writer.newLine();
            l_buff_writer.write("[borders]");
            l_buff_writer.newLine();

            l_buff_writer.close();
            System.out.println("A new map created!");

        } catch (Exception e) {
            System.out.println(e);
        }

        GameMap l_map = new GameMap(p_map_path);

        return l_map;
    }

    public GameMap editCountry(GameMap p_map, String p_map_option, List<String> p_parameter_list) throws Exception {

        //editcountry -add countryID continentID -remove countryID
        List<GameMap.Country> l_countries = p_map.getCountryObjects();
        List<GameMap.Continent> l_continents = p_map.getContinentObjects();
        List<List<Integer>> l_borders = p_map.getBorders();

        String l_country_name = "";
        String l_continent_name = "";

        if (p_map_option.compareTo("add") == 0) {

            if (p_parameter_list.size() != 2) {
                System.out.println("Please enter the country name and continent name");
                return p_map;
            }

            l_country_name = p_parameter_list.get(0);
            l_continent_name = p_parameter_list.get(1);

            //check if the continent exists
            boolean l_continent_exists = false;

            for (int l_index = 0; l_index < l_continents.size(); l_index++) {
                if (l_continents.get(l_index).getContinentName().compareTo(l_continent_name) == 0) {
                    l_continent_exists = true;
                }
            }

            if (l_continent_exists) {
                //creating country object
                int l_country_id = 1;
                if (l_countries.size() != 0) {
                    l_country_id = l_countries.get(l_countries.size() - 1).getCountryID() + 1;
                }

                GameMap.Country l_country_obj = p_map.new Country(l_country_id, l_country_name,
                        GameConstants.D_DEFAULT_IS_CONQUERED, GameConstants.D_DEFAULT_ARMY_COUNT,
                        l_continent_name);

                //updating map.countries list
                p_map.d_countries.add(l_country_obj);

                //updating continent object's country list
                for (int l_index = 0; l_index < l_continents.size(); l_index++) {
                    if (l_continents.get(l_index).getContinentName().compareTo(l_country_name) == 0) {
                        p_map.d_continents.get(l_index).getCountryIDList().add(l_country_id);
                        break;
                    }
                }

                //adding new country to borders
                ArrayList<Integer> l_new_border = new ArrayList<Integer>();
                l_new_border.add(l_country_id);
                p_map.d_borders.add(l_new_border);
                
                System.out.println("Added Country");

            } else {
                System.out.println(GameMessageConstants.D_MAP_NO_CONTINENT);
            }
        }

        if (p_map_option.compareTo("remove") == 0) {

            if (p_parameter_list.size() != 1) {
                System.out.println("Please enter the country name");
                return p_map;
            }

            l_countries = p_map.getCountryObjects();
            l_continents = p_map.getContinentObjects();
            l_borders = p_map.getBorders();

            l_country_name = p_parameter_list.get(0);

            int l_country_id = -1;
            int l_country_index = -1;

            //removing country from map.countries list
            for (int l_index = 0; l_index < l_countries.size(); l_index++) {
                if (l_countries.get(l_index).getCountryName().compareTo(l_country_name) == 0) {
                    l_country_id = l_countries.get(l_index).getCountryID();
                    l_country_index = l_index;
                    p_map.d_countries.remove(l_index);
                    break;
                }
            }
            //removing country from continent's country list
            if (l_country_index != -1 || l_country_id != -1) {
                for (int l_index = 0; l_index < l_continents.size(); l_index++) {
                    if (l_continents.get(l_index).getCountryIDList().contains(l_country_id)) {
                        p_map.d_continents.get(l_index).getCountryIDList().remove(Integer.valueOf(l_country_id));
                        break;
                    }
                }

                //removing country in borders list
                p_map.d_borders.remove(l_country_index);

                for (int l_index = 0; l_index < l_borders.size(); l_index++) {
                    if (l_borders.get(l_index).contains(l_country_id)) {
                        p_map.d_borders.get(l_index).remove(Integer.valueOf(l_country_id));
                    }
                }
                System.out.println("Removed Country");

            } else {
                System.out.println(GameMessageConstants.D_MAP_NO_COUNTRY);
            }
        }

        return p_map;
    }

    public GameMap editContinent(GameMap p_map, String p_map_option, List<String> p_parameter_list) throws Exception {
        // editcontinent -add continentID continentvalue -remove continentID

        List<GameMap.Country> l_countries = p_map.getCountryObjects();
        List<GameMap.Continent> l_continents = p_map.getContinentObjects();
        List<List<Integer>> l_borders = p_map.getBorders();

        String l_continent_name = "";
        int l_continent_value = -1;

        if (p_map_option.compareTo("add") == 0) {

            if (p_parameter_list.size() != 2) {
                System.out.println("Please enter the continent name and its bonus value");
                return p_map;
            }

            l_continent_name = p_parameter_list.get(0);
            l_continent_value = Integer.parseInt(p_parameter_list.get(1));

            //creating continent object
            GameMap.Continent l_continent_obj = p_map.new Continent(l_continent_name, GameConstants.D_DEFAULT_IS_CONQUERED, l_continent_value, null);

            p_map.d_continents.add(l_continent_obj);
            
            System.out.println("Added Continent "+ l_continent_name);

        }
        if (p_map_option.compareTo("remove") == 0) {

            if (p_parameter_list.size() != 1) {
                System.out.println("Please enter the continent name");
                return p_map;
            }
            l_countries = p_map.getCountryObjects();
            l_continents = p_map.getContinentObjects();
            l_borders = p_map.getBorders();

            l_continent_name = p_parameter_list.get(0);

            //removing object from  map.continents list
            String l_continent_name_delete = "";

            for (int l_index = 0; l_index < l_continents.size(); l_index++) {
                if (l_continents.get(l_index).getContinentName().compareTo(l_continent_name) == 0) {
                    l_continent_name_delete = l_continents.get(l_index).getContinentName();
                    p_map.d_continents.remove(l_index);
                    break;
                }
            }

            if (l_continent_name_delete.compareTo("") == 0) {
                System.out.println(GameMessageConstants.D_MAP_NO_CONTINENT);
                return p_map;
            }

            l_continents = p_map.getContinentObjects();

            //removing countries that belong to the continent (in country list and border list)
            List<GameMap.Country> l_new_country_list = new ArrayList<GameMap.Country>();
            List<List<Integer>> l_new_border_list = new ArrayList<List<Integer>>();

            List<Integer> l_country_id_delete = new ArrayList<Integer>();

            for (int l_index = 0; l_index < l_countries.size(); l_index++) {

                if (l_countries.get(l_index).getContinentName().compareTo(l_continent_name_delete) != 0) {

                    l_new_country_list.add(l_countries.get(l_index));
                    l_new_border_list.add(l_borders.get(l_index));
                } else {
                    l_country_id_delete.add(l_countries.get(l_index).getCountryID());
                }
            }
            p_map.d_countries = l_new_country_list;
            p_map.d_borders = l_new_border_list;

            //removing the country border from other countries
            l_borders = p_map.getBorders();

            for (int l_index = 0; l_index < l_country_id_delete.size(); l_index++) {
                for (int l_j_index = 0; l_j_index < l_borders.size(); l_j_index++) {
                    if (l_borders.get(l_j_index).contains(l_country_id_delete.get(l_index))) {
                        p_map.d_borders.get(l_j_index).remove(Integer.valueOf(l_country_id_delete.get(l_index)));
                    }
                }
            }
            
            System.out.println("Removed Continent "+ l_continent_name);

        }

        return p_map;

    }

    public GameMap editBorders(GameMap p_map, String p_map_option, List<String> p_parameter_list) throws Exception {

        //editneighbor -add countryID neighborcountryID -remove countryID neighborcountryID
        List<List<Integer>> l_borders;
        List<GameMap.Country> l_countries;

        String l_country_name = "";
        String l_neighbor_country_name = "";

        int l_country_id = -1;
        int l_neighbor_country_id = -1;

        int l_country_index = -1;
        int l_country_neighbor_index = -1;

        if (p_map_option.compareTo("add") == 0) {

            if (p_parameter_list.size() != 2) {
                System.out.println("Please enter the country name and the neighbor country name");
                return p_map;
            }

            l_borders = p_map.getBorders();
            l_countries = p_map.getCountryObjects();

            l_country_name = p_parameter_list.get(0);
            l_neighbor_country_name = p_parameter_list.get(1);

            if (l_country_name.compareTo(l_neighbor_country_name) == 0) {
                System.out.println("Country and neighboring country can not be the same");
            }

            for (int l_index = 0; l_index < l_countries.size(); l_index++) {
                if (l_countries.get(l_index).getCountryName().compareTo(l_country_name) == 0) {
                    l_country_id = l_countries.get(l_index).getCountryID();
                    l_country_index = l_index;
                }
                if (l_countries.get(l_index).getCountryName().compareTo(l_neighbor_country_name) == 0) {
                    l_neighbor_country_id = l_countries.get(l_index).getCountryID();
                    l_country_neighbor_index = l_index;
                }
            }

            if (p_map.d_borders.get(l_country_index).contains(l_neighbor_country_id)) {
                System.out.println("Neighbor already added");
            } else {
                p_map.d_borders.get(l_country_index).add(l_neighbor_country_id);
                System.out.println("Added neighbor relation between " + l_country_name + " and " +l_neighbor_country_name );
            }

        }
        if (p_map_option.compareTo("remove") == 0) {

            if (p_parameter_list.size() != 2) {
                System.out.println("Please enter the country name and the neighbor country name");
                return p_map;
            }
            l_borders = p_map.getBorders();
            l_countries = p_map.getCountryObjects();

            l_country_name = p_parameter_list.get(0);
            l_neighbor_country_name = p_parameter_list.get(1);

            if (l_country_name.compareTo(l_neighbor_country_name) == 0) {
                System.out.println("Country and neighboring country can not be the same");
            }

            for (int l_index = 0; l_index < l_countries.size(); l_index++) {
                if (l_countries.get(l_index).getCountryName().compareTo(l_country_name) == 0) {
                    l_country_id = l_countries.get(l_index).getCountryID();
                    l_country_index = l_index;
                }
                if (l_countries.get(l_index).getCountryName().compareTo(l_neighbor_country_name) == 0) {
                    l_neighbor_country_id = l_countries.get(l_index).getCountryID();
                    l_country_neighbor_index = l_index;
                }
            }

            if (!p_map.d_borders.get(l_country_index).contains(l_neighbor_country_id)) {
                System.out.println("Neighbor does not exist");
            } else {
                p_map.d_borders.get(l_country_index).remove(Integer.valueOf(l_neighbor_country_id));
                System.out.println("Removed neighbor relation between " + l_country_name + " and " +l_neighbor_country_name );
            }
        }

        return p_map;
    }

    public GameMap modifyMapFile(GameMap p_map, String p_file_path) {

        List<GameMap.Country> l_countries = p_map.getCountryObjects();
        List<GameMap.Continent> l_continents = p_map.getContinentObjects();
        List<List<Integer>> l_borders = p_map.getBorders();

        GameMap l_new_map = null;

        //make a new map file
        //add new content
        //overwrite map file
        try {
            File l_fout = new File(p_file_path);
            FileOutputStream l_fos = new FileOutputStream(l_fout);

            BufferedWriter l_bw = new BufferedWriter(new OutputStreamWriter(l_fos));

            l_bw.write("[continents]");
            l_bw.newLine();

            for (int l_index = 0; l_index < l_continents.size(); l_index++) {
                GameMap.Continent l_continent_obj = l_continents.get(l_index);
                l_bw.write(l_continent_obj.getContinentName() + " " + l_continent_obj.getSpecialNumber());
                l_bw.newLine();
            }
            l_bw.newLine();
            l_bw.write("[countries]");
            l_bw.newLine();

            for (int l_index = 0; l_index < l_countries.size(); l_index++) {
                GameMap.Country l_country_obj = l_countries.get(l_index);
                int l_continent_id = p_map.getContinentIDfromName(l_country_obj.getContinentName());
                l_bw.write(l_country_obj.getCountryID() + " " + l_country_obj.getCountryName() + " " + l_continent_id);
                l_bw.newLine();
            }
            l_bw.newLine();
            l_bw.write("[borders]");
            l_bw.newLine();

            for (int l_index = 0; l_index < l_borders.size(); l_index++) {
                for (int l_j = 0; l_j < l_borders.get(l_index).size(); l_j++) {
                    l_bw.write(l_borders.get(l_index).get(l_j) + " ");
                }
                l_bw.newLine();
            }

            l_bw.close();

            l_new_map = new GameMap(p_file_path);

        } catch (IOException e) {
            System.out.println(e);
        } catch (Exception e) {
            System.out.println(e);
        }

        return l_new_map;
    }

}
