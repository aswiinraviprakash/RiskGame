package mapparser;

import common.LogEntryBuffer;
import common.Phase;
import constants.GameConstants;
import constants.GameMessageConstants;
import gameutils.GameCommandParser;
import gameutils.GameException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * The class deals with the map editing.
 */
public class EditMapPhase extends Phase {

    @Override
    public String toString() {
        return "EditMapPhase{" +
                "d_game_map=" + d_game_map +
                '}';
    }

    private static LogEntryBuffer d_logger = LogEntryBuffer.getInstance();

    private GameMap d_game_map;

    private String d_map_file_name;

    /**
     * Constructor initialises the member variables.
     * @param l_game_map loads game map.
     * @param l_map_file_name contains the map file name.
     */
    public EditMapPhase(GameMap l_game_map, String l_map_file_name) {
        this.d_game_map = l_game_map;
        this.d_map_file_name = l_map_file_name;
    }

    /**
     * Method deals with processing to the next phase.
     * @return object of execute order phase.
     * @throws Exception If there is an error in the execution or validation.
     */
    @Override
    public Phase nextPhase() throws Exception {
        return null;
    }

    /**
     * Function is used for adding, removing continents.
     * @param p_map map object.
     * @param p_map_option indicates the operation needed to be performed.
     * @param p_parameter_list contains continent name and the bonus value.
     * @return updated map object.
     * @throws Exception If there is an error in the execution or validation.
     */
    public GameMap editContinent(GameMap p_map, String p_map_option, List<String> p_parameter_list) throws Exception {
        // editcontinent -add continentID continentvalue -remove continentID

        List<GameMap.Country> l_countries = p_map.getCountryObjects();
        List<GameMap.Continent> l_continents = p_map.getContinentObjects();
        LinkedHashMap<Integer, List<Integer>> l_borders = p_map.getBorders();

        String l_continent_name = "";
        int l_continent_value = -1;
        String l_continent_value_str = "";

        if (p_map_option.compareTo("add") == 0) {

            if (p_parameter_list.size() != 2) {
                throw new GameException(GameMessageConstants.D_EDIT_CONTINENT_ERROR);
            } else {
                l_continent_name = p_parameter_list.get(0);
                l_continent_value_str = p_parameter_list.get(1);

                for (int i = 0; i < l_continent_value_str.length(); i++) {
                    if (Character.isDigit(l_continent_value_str.charAt(i)) == false) {
                        throw new GameException(GameMessageConstants.D_EDIT_CONTINENT_VALID_BONUS);
                    }
                }

                l_continent_value = Integer.parseInt(l_continent_value_str);

                //checking if continent already exists
                boolean l_continent_exists = false;

                for (int l_index = 0; l_index < l_continents.size(); l_index++) {
                    if (l_continents.get(l_index).getContinentName().compareTo(l_continent_name) == 0) {
                        l_continent_exists = true;
                        break;
                    }
                }

                if (l_continent_exists) {
                    throw new GameException(GameMessageConstants.D_MAP_DUPLICATE_COUNTRY);
                }

                //creating continent object
                GameMap.Continent l_continent_obj = p_map.new Continent(l_continent_name, GameConstants.D_DEFAULT_IS_CONQUERED, l_continent_value, new ArrayList<Integer>());

                p_map.d_continents.add(l_continent_obj);

                System.out.println(GameMessageConstants.D_CONTINENT_ADDED + l_continent_name);
                d_logger.addLogger("Continent Added");
            }

        } else if (p_map_option.compareTo("remove") == 0) {

            if (p_parameter_list.size() != 1) {
                throw new GameException(GameMessageConstants.D_MISSING_CONTINENT_NAME);
            } else {
                l_countries = p_map.getCountryObjects();
                l_continents = p_map.getContinentObjects();
                l_borders = p_map.getBorders();

                l_continent_name = p_parameter_list.get(0);

                //removing object from  map.continents list
                String l_continent_name_delete = "";
                int l_continent_delete_index = -1;
                for (int l_index = 0; l_index < l_continents.size(); l_index++) {
                    if (l_continents.get(l_index).getContinentName().compareTo(l_continent_name) == 0) {
                        l_continent_name_delete = l_continents.get(l_index).getContinentName();
                        l_continent_delete_index = l_index;
                        break;
                    }
                }

                if (l_continent_name_delete.compareTo("") == 0) {
                    throw new GameException(GameMessageConstants.D_MAP_NO_CONTINENT);
                }

                p_map.d_continents.remove(l_continent_delete_index);
                l_continents = p_map.getContinentObjects();

                //removing countries that belong to the continent (in country list and border list)
                List<GameMap.Country> l_new_country_list = new ArrayList<GameMap.Country>();
                LinkedHashMap<Integer, List<Integer>> l_new_border_list = new LinkedHashMap<Integer, List<Integer>>();

                List<Integer> l_country_id_delete = new ArrayList<Integer>();

                //refactored
                for (int l_index = 0; l_index < l_countries.size(); l_index++) {

                    if (l_countries.get(l_index).getContinentName().compareTo(l_continent_name_delete) != 0) {

                        l_new_country_list.add(l_countries.get(l_index));
                        l_new_border_list.put(l_countries.get(l_index).getCountryID(), l_borders.get(l_countries.get(l_index).getCountryID()));

                    } else {
                        l_country_id_delete.add(l_countries.get(l_index).getCountryID());
                    }
                }
                p_map.d_countries = l_new_country_list;
                p_map.d_borders = l_new_border_list;

                //removing the country border from other countries
                l_borders = p_map.getBorders();

                for (int l_index = 0; l_index < l_country_id_delete.size(); l_index++) {
                    final int l_country_id_del = l_country_id_delete.get(l_index);
                    p_map.d_borders.forEach((key, value) -> {
                        if (value.contains(l_country_id_del)) {
                            value.remove(Integer.valueOf(l_country_id_del));
                            p_map.d_borders.replace(key, value);
                        }
                    });
                }

                System.out.println(GameMessageConstants.D_CONTINENT_REMOVED + l_continent_name);
                d_logger.addLogger("Continent Removed");
            }

        } else {
            throw new GameException(GameMessageConstants.D_COMMAND_OPTION_INVALID + "\nExample Format : " + GameMessageConstants.D_EDITCONTINENT);
        }

        return p_map;

    }

    /**
     * Function is used for adding, removing countries.
     * @param p_map map object.
     * @param p_map_option indicates the operation needed to be performed.
     * @param p_parameter_list country and continent value.
     * @return updated map object.
     * @throws Exception If there is an error in the execution or validation.
     */
    public GameMap editCountry(GameMap p_map, String p_map_option, List<String> p_parameter_list) throws Exception {

        //editcountry -add countryID continentID -remove countryID
        List<GameMap.Country> l_countries = p_map.getCountryObjects();
        List<GameMap.Continent> l_continents = p_map.getContinentObjects();
        LinkedHashMap<Integer, List<Integer>> l_borders = p_map.getBorders();

        String l_country_name = "";
        String l_continent_name = "";

        if (p_map_option.compareTo("add") == 0) {

            if (p_parameter_list.size() != 2) {
                throw new GameException(GameMessageConstants.D_SKIPPED_COUNTRY_CONTINENT);
            } else {
                l_country_name = p_parameter_list.get(0);
                l_continent_name = p_parameter_list.get(1);

                //check if the continent exists
                boolean l_continent_exists = false, l_country_exists = false;

                for (int l_index = 0; l_index < l_continents.size(); l_index++) {
                    if (l_continents.get(l_index).getContinentName().compareTo(l_continent_name) == 0) {
                        l_continent_exists = true;
                        break;
                    }
                }
                
                for (int l_index = 0; l_index < l_countries.size(); l_index++) {
                    if (l_countries.get(l_index).getCountryName().compareTo(l_country_name) == 0) {
                        l_country_exists = true;
                        break;
                    }
                }

                if (l_continent_exists ) {
                    if(!l_country_exists){
                        //creating country object
                    int l_country_id = 1;
                    if (l_countries.size() != 0) {
                        l_country_id = l_countries.get(l_countries.size() - 1).getCountryID() + 1;
                    }

                    GameMap.Country l_country_obj = p_map.new Country(l_country_id, l_country_name,
                            GameConstants.D_DEFAULT_IS_CONQUERED, GameConstants.D_DEFAULT_ARMY_COUNT,
                            l_continent_name, null);

                    //updating map.countries list
                    p_map.d_countries.add(l_country_obj);

                    //updating continent object's country list
                    for (int l_index = 0; l_index < l_continents.size(); l_index++) {
                        if (l_continents.get(l_index).getContinentName().compareTo(l_continent_name) == 0) {
                            GameMap.Continent l_continent_obj = p_map.d_continents.get(l_index);
                            l_continent_obj.getCountryIDList().add(l_country_id);
                            break;
                        }
                    }

                    //adding new country to borders
                    ArrayList<Integer> l_new_border = new ArrayList<Integer>();
                    l_new_border.add(l_country_id);

                    //refactored
                    p_map.d_borders.put(l_country_id, l_new_border);

                    System.out.println(GameMessageConstants.D_COUNTRY_ADDED + l_country_name);
                    d_logger.addLogger("Country Added");
                    }else{
                        throw new GameException(GameMessageConstants.D_MAP_DUPLICATE_COUNTRY);
                    }
                    

                } else {
                    throw new GameException(GameMessageConstants.D_MAP_NO_CONTINENT);
                }
            }

        } else if (p_map_option.compareTo("remove") == 0) {

            if (p_parameter_list.size() != 1) {
                throw new GameException(GameMessageConstants.D_MISSING_COUNTRY_NAME);
            } else {
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
                        break;
                    }
                }
                //removing country from continent's country list
                if (l_country_index != -1 || l_country_id != -1) {
                    p_map.d_countries.remove(l_country_index);
                    for (int l_index = 0; l_index < l_continents.size(); l_index++) {
                        if (l_continents.get(l_index).getCountryIDList().contains(l_country_id)) {
                            p_map.d_continents.get(l_index).getCountryIDList().remove(Integer.valueOf(l_country_id));
                            break;
                        }
                    }

                    //removing country in borders list
                    //refactored
                    p_map.d_borders.remove(l_country_id);
                    final int l_country_final = l_country_id;

                    p_map.d_borders.forEach((key, value) -> {
                        if (value.contains(l_country_final)) {
                            value.remove(Integer.valueOf(l_country_final));
                            p_map.d_borders.replace(key, value);
                        }
                    });

                    System.out.println(GameMessageConstants.D_COUNTRY_REMOVED + l_country_name);
                    d_logger.addLogger("Country Removed");

                } else {
                    throw new GameException(GameMessageConstants.D_MAP_NO_COUNTRY);
                }
            }

        } else {
            throw new GameException(GameMessageConstants.D_COMMAND_OPTION_INVALID + "\nExample Format : " + GameMessageConstants.D_EDITCONTINENT);
        }

        return p_map;
    }

    /**
     * Function is used for adding, removing neighbouring countries.
     * @param p_map map object.
     * @param p_map_option indicates the operation needed to be performed.
     * @param p_parameter_list contains a country name and its neighbouring
     * countries.
     * @return updated map object.
     * @throws Exception If there is an error in the execution or validation.
     */
    public GameMap editBorders(GameMap p_map, String p_map_option, List<String> p_parameter_list) throws Exception {

        //editneighbor -add countryID neighborcountryID -remove countryID neighborcountryID
        LinkedHashMap<Integer, List<Integer>> l_borders;
        List<GameMap.Country> l_countries;

        String l_country_name = "";
        String l_neighbor_country_name = "";

        int l_country_id = -1;
        int l_neighbor_country_id = -1;

        int l_country_index = -1;
        int l_country_neighbor_index = -1;

        if (p_map_option.compareTo("add") == 0) {

            if (p_parameter_list.size() != 2) {
                throw new GameException(GameMessageConstants.D_MAP_MISSING_COUNTRY_AND_NEIGHBOR);
            }

            l_borders = p_map.getBorders();
            l_countries = p_map.getCountryObjects();

            l_country_name = p_parameter_list.get(0);
            l_neighbor_country_name = p_parameter_list.get(1);

            if (l_country_name.compareTo(l_neighbor_country_name) == 0) {
                throw new GameException(GameMessageConstants.D_MAP_INVALID_NEIGHBOR);
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
            if (l_country_index == -1 || l_country_neighbor_index == -1) {
                throw new GameException(GameMessageConstants.D_MAP_INVALID_COUNTRY_NEIGHBOR);
            } else {
                if (p_map.d_borders.get(l_country_id).contains(l_neighbor_country_id)) {
                    System.out.println(GameMessageConstants.D_MAP_DUPLICATE_NEIGHBOR);
                    d_logger.addLogger("Duplicate Neighbor");
                } else {

                    //refactored
                    List<Integer> l_new_border_list = p_map.d_borders.get(l_country_id);
                    l_new_border_list.add(l_neighbor_country_id);
                    p_map.d_borders.replace(l_country_id, l_new_border_list);
                    System.out.println(GameMessageConstants.D_RELATION_ADDED + l_country_name + " and " + l_neighbor_country_name);
                    d_logger.addLogger("Border Added");
                }
            }

        } else if (p_map_option.compareTo("remove") == 0) {

            if (p_parameter_list.size() != 2) {
                throw new GameException(GameMessageConstants.D_MAP_MISSING_COUNTRY_AND_NEIGHBOR);
            }
            l_borders = p_map.getBorders();
            l_countries = p_map.getCountryObjects();

            l_country_name = p_parameter_list.get(0);
            l_neighbor_country_name = p_parameter_list.get(1);

            if (l_country_name.compareTo(l_neighbor_country_name) == 0) {
                throw new GameException(GameMessageConstants.D_MAP_INVALID_NEIGHBOR);
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
            if (l_country_index == -1 || l_country_neighbor_index == -1) {
                throw new GameException(GameMessageConstants.D_MAP_INVALID_COUNTRY_NEIGHBOR);
            } else {
                if (!p_map.d_borders.get(l_country_id).contains(l_neighbor_country_id)) {
                    System.out.println(GameMessageConstants.D_MAP_NO_NEIGHBOR);
                    d_logger.addLogger("Neighbor does not exist");
                } else {
                    //refactored

                    List<Integer> l_new_border_list = p_map.d_borders.get(l_country_id);
                    l_new_border_list.remove(Integer.valueOf(l_neighbor_country_id));
                    p_map.d_borders.replace(l_country_id, l_new_border_list);
                    System.out.println(GameMessageConstants.D_RELATION_REMOVED + l_country_name + " and " + l_neighbor_country_name);
                    d_logger.addLogger("Removed neighbor relation");
                }
            }

        } else {
            throw new GameException(GameMessageConstants.D_COMMAND_OPTION_INVALID + "\nExample Format : " + GameMessageConstants.D_EDITCONTINENT);
        }

        return p_map;
    }

    /**
     * Function is used to create a new map file, to modify its contents and to
     * overwrite map files.
     * @param p_map map object.
     * @param p_file_name map file name.
     * @return updated map files.
     * @throws Exception If there is an error in the execution or validation.
     */
    public GameMap modifyMapFile(GameMap p_map, String p_file_name) throws Exception {

        List<GameMap.Country> l_countries = p_map.getCountryObjects();
        List<GameMap.Continent> l_continents = p_map.getContinentObjects();
        LinkedHashMap<Integer, List<Integer>> l_borders = p_map.getBorders();

        GameMap l_new_map = null;

        File l_file_dir = new File("").getCanonicalFile();
        String l_load_file_path = l_file_dir.getParent() + GameConstants.D_MAP_DIRECTORY + p_file_name;

        //make a new map file
        //add new content
        //overwrite map file
        try {
            File l_fout = new File(l_load_file_path);
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

            l_borders.forEach((key, value) -> {
                try {
                    for (int l_index = 0; l_index < value.size(); l_index++) {
                        l_bw.write(String.valueOf(value.get(l_index)) + " ");
                    }
                    l_bw.newLine();
                } catch (IOException e) {

                }
            });

            l_bw.close();

        } catch (IOException e) {
           throw e;
        }

        LoadMapPhase l_loadmap_phase = new LoadMapPhase(p_file_name, false);
        l_loadmap_phase.executePhase();
        l_new_map = l_loadmap_phase.getLoadedMap();

        return l_new_map;
    }

    /**
     * Method executes the edit map phase.
     * @throws Exception If there is an error in the execution or validation.
     */
    @Override
    public void executePhase() throws Exception {

        BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));

        // validate user input map commands commands
        System.out.printf("Map Editor Menu!!! %n");
        System.out.printf("1 - Edit Continent (editcontinent -add -remove) - 2 - Edit Country (editcountry -add -remove) - 3 - Edit Borders (editneighbor -add -remove) - 4 - Show Map (showmap) - 5 - Save map (savemap -file) - 6 - Validate map (validatemap) - 7 - Exit (exit)%n%n");
        String l_map_command = l_reader.readLine();

        while (!l_map_command.equals("exit")) {

            try {
                GameCommandParser l_command_parser = new GameCommandParser(l_map_command);
                String l_primary_command = l_command_parser.getPrimaryCommand();
                List<GameCommandParser.CommandDetails> l_command_details = l_command_parser.getParsedCommandDetails();

                if (l_primary_command.equals(GameMessageConstants.D_SHOWMAP)) {
                    d_game_map.showMap(false);
                } else if (l_primary_command.equals(GameMessageConstants.D_VALIDATEMAP)) {

                    boolean l_is_map_valid = d_game_map.validateGameMap();
                    if (l_is_map_valid) {
                        System.out.println(GameMessageConstants.D_VALID_MAP);
                    } else {
                        throw new GameException(GameMessageConstants. D_MAP_PROBLEM);
                    }

                } else {

                    if (l_command_details.isEmpty()) throw new GameException(GameMessageConstants.D_COMMAND_INVALID);

                    for (GameCommandParser.CommandDetails l_command_detail : l_command_details) {

                        switch (l_primary_command) {
                            case "editcontinent": {

                                List<String> l_command_parameters = l_command_detail.getCommandParameters();
                                if (l_command_parameters.isEmpty()) throw new GameException(GameMessageConstants.D_COMMAND_PARAMETER_INVALID + "\nExample Format: " + GameMessageConstants.D_EDITCONTINENT);

                                String l_command_option = l_command_detail.getCommandOption();

                                d_game_map = this.editContinent(d_game_map, l_command_option, l_command_parameters);
                                break;
                            }
                            case "editcountry": {

                                List<String> l_command_parameters = l_command_detail.getCommandParameters();
                                if (l_command_parameters.isEmpty()) throw new GameException(GameMessageConstants.D_COMMAND_PARAMETER_INVALID + "\nExample Format: " + GameMessageConstants.D_EDITCONTINENT);

                                String l_command_option = l_command_detail.getCommandOption();

                                d_game_map = this.editCountry(d_game_map, l_command_option, l_command_parameters);
                                break;
                            }
                            case "editneighbor": {

                                List<String> l_command_parameters = l_command_detail.getCommandParameters();
                                if (l_command_parameters.isEmpty()) throw new GameException(GameMessageConstants.D_COMMAND_PARAMETER_INVALID + "\nExample Format: " + GameMessageConstants.D_EDITCONTINENT);

                                String l_command_option = l_command_detail.getCommandOption();

                                d_game_map = this.editBorders(d_game_map, l_command_option, l_command_parameters);
                                break;
                            }
                            case "savemap": {
                                if (l_command_details.size() != 1) throw new GameException(GameMessageConstants.D_COMMAND_INVALID);

                                List<String> l_command_parameters = l_command_detail.getCommandParameters();
                                if (l_command_parameters.size() != 1) throw new GameException(GameMessageConstants.D_COMMAND_PARAMETER_INVALID);

                                String l_map_path_input = l_command_parameters.get(0);
                                if (!l_map_path_input.equals(d_map_file_name)) throw new GameException(GameMessageConstants.D_CRITICAL_MAP_NAME);

                                boolean l_is_map_valid = d_game_map.validateGameMap();
                                if (l_is_map_valid) {
                                    d_game_map = this.modifyMapFile(d_game_map, d_map_file_name);
                                    System.out.println(GameMessageConstants.D_SAVE_MAP);
                                    d_logger.addLogger("Map file saved");
                                } else {
                                    throw new GameException(GameMessageConstants.D_MAP_LOAD_FAILED_EXIT);
                                }

                                break;
                            }
                            default:
                                throw new GameException(GameMessageConstants.D_COMMAND_INVALID);
                        }

                    }
                }

            } catch (GameException e) {
                System.out.println(e.getMessage());
                d_logger.addLogger(e.getMessage());
            } catch (Exception e) {
                throw e;
            }

            System.out.printf("Map Editor Menu!!! %n");
            System.out.printf("1 - Edit Continent (editcontinent -add -remove) - 2 - Edit Country (editcountry -add -remove) - 3 - Edit Borders (editneighbor -add -remove) - 4 - Show Map (showmap) - 5 - Save map (savemap -file) - 6 - Validate map (validatemap) - 7 - Exit (exit)%n%n");
            l_map_command = l_reader.readLine();
        }

    }
}
