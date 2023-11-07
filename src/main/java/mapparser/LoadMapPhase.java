package mapparser;

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
 *
 */
public class LoadMapPhase extends Phase {

    private String d_map_path;

    private GameMap d_loaded_game_map;

    private boolean d_need_newmap;

    private String d_map_directory;

    public LoadMapPhase(String p_map_path, boolean p_need_newmap) {
        this.d_map_path = p_map_path;
        this.d_need_newmap = p_need_newmap;
        this.d_map_directory = GameConstants.D_MAP_DIRECTORY;
    }

    public void setMapDirectory(String p_map_directory) {
        this.d_map_directory = p_map_directory;
    }

    public GameMap getLoadedMap() {
        return this.d_loaded_game_map;
    }

    @Override
    public Phase nextPhase() throws Exception {
        return null;
    }

    public void loadGameMap(GameMap p_game_map) throws Exception {

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
     *
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
    }

    /**
     * Method executes the edit map phase.
     * @throws Exception
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
            throw e;
        } catch (Exception e) {
            throw new GameException(GameMessageConstants.D_MAP_LOAD_FAILED);
        }
    }
}
