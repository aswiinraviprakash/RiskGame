package mapparser;

import common.LogEntryBuffer;
import common.Phase;
import constants.GameConstants;
import constants.GameMessageConstants;
import gameutils.GameException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 * The class deals with the loading of map into the game.
 */
public class LoadMapPhase extends Phase {

    /**
     * member to store logger instance
     */
    private static LogEntryBuffer d_logger = LogEntryBuffer.getInstance();

    /**
     * member to store map path
     */
    private String d_map_path;

    /**
     * member to store loaded gamemap instance
     */
    private GameMap d_loaded_game_map;

    /**
     * member to check need for new map
     */
    private boolean d_need_newmap;

    /**
     * member to store map directory
     */
    private String d_map_directory;

    /**
     * Constructor for initialising member variables.
     * @param p_map_path map file location.
     * @param p_need_newmap indicative of requiring a new map or not.
     */
    public LoadMapPhase(String p_map_path, boolean p_need_newmap) {
        this.d_map_path = p_map_path;
        this.d_need_newmap = p_need_newmap;
        this.d_map_directory = GameConstants.D_MAP_DIRECTORY;
    }

    /**
     * Method sets map path.
     * @param p_map_directory map file location.
     */
    public void setMapDirectory(String p_map_directory) {
        this.d_map_directory = p_map_directory;
    }

    /**
     * Method returns loaded map.
     * @return loaded map.
     */
    public GameMap getLoadedMap() {
        return this.d_loaded_game_map;
    }

    /**
     * Method makes the game proceed to the next phase.
     * @return null
     * @throws Exception If there is an error in the execution or validation.
     */
    @Override
    public Phase nextPhase() throws Exception {
        return null;
    }

    /**
     * Method loads the complete game map.
     * @param p_game_map map object.
     * @throws Exception If there is an error in the execution or validation.
     */
    public void loadGameMap(GameMap p_game_map) throws Exception {

        d_logger.addLogger("Game Map Loaded");
        // loading map objects
        p_game_map.loadBorders();
        p_game_map.loadContinents();
        p_game_map.loadCountries();

        List<GameMap.Continent> l_continents = p_game_map.getContinentObjects();

        //pass continent obj to add countries to country list
        for (int l_index = 0; l_index < l_continents.size(); l_index++) {
            p_game_map.addCountryToContinentObj(l_continents.get(l_index));
        }
    }

    /**
     * Function creates a new map file.
     * @return map object.
     */
    private void initialiseMapFile() throws Exception {
        File l_file_obj = new File(d_map_path);
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
        System.out.println(GameMessageConstants.D_NEW_MAP);
        d_logger.addLogger("Map Created");
    }

    /**
     * Method executes the edit map phase.
     * @throws Exception If there is an error in the execution or validation.
     */
    @Override
    public void executePhase() throws Exception {
        try {
            File l_file;
            File l_file_dir = new File("").getCanonicalFile();
            d_map_path = l_file_dir.getParent() + this.d_map_directory + d_map_path;

            //check if this path exists, if not, create a new map file and ask user to enter map data
            l_file = new File(d_map_path);
            GameMap l_game_map;

            if (this.d_need_newmap) {
                if (l_file.exists()) {

                    l_game_map = new GameMap(d_map_path);
                    this.loadGameMap(l_game_map);
                    boolean l_map_valid = l_game_map.validateGameMap();
                    if (l_map_valid) this.d_loaded_game_map = l_game_map;
                    else throw new GameException(GameMessageConstants.D_MAP_LOAD_FAILED);


                } else {

                    this.initialiseMapFile();
                    l_game_map = new GameMap(d_map_path);
                    this.loadGameMap(l_game_map);
                    this.d_loaded_game_map = l_game_map;

                }

            } else {
                if (!l_file.exists()) throw new GameException(GameMessageConstants.D_MAP_LOAD_FAILED);


                l_game_map = new GameMap(d_map_path);
                this.loadGameMap(l_game_map);
                boolean l_map_valid = l_game_map.validateGameMap();
                if (l_map_valid) this.d_loaded_game_map = l_game_map;
                else throw new GameException(GameMessageConstants.D_MAP_LOAD_FAILED);
            }
        } catch (GameException e) {
            d_logger.addLogger(e.getMessage());
            throw e;
        } catch (Exception e) {
            throw new GameException(GameMessageConstants.D_MAP_LOAD_FAILED);
        }
    }
}
