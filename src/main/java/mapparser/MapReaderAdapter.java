package mapparser;

import java.util.LinkedHashMap;
import java.util.List;
import mapparser.GameMap.Continent;
import mapparser.GameMap.Country;

/**
<<<<<<< Updated upstream
 * Adpater pattern c
=======
 * Adapter for domination map reader
>>>>>>> Stashed changes
 */
public class MapReaderAdapter extends DominationGameMapReader {

    /**
<<<<<<< Updated upstream
     * Contains conquest map
     */
    private ConquestGameMapReader d_conquest_map;

    /**
     * Map reader adapter
     * @param p_conquest_map Conquestmapreader object
     */
    // load functions that will be overridden 
=======
     * Game map
     */
    private ConquestGameMapReader d_conquest_map;

    // load functions that will be overridden

    /**
     * Map reader adapter
     * @param p_conquest_map contains the map
     */
>>>>>>> Stashed changes
    public MapReaderAdapter(ConquestGameMapReader p_conquest_map) {
        super(p_conquest_map.d_file_path, p_conquest_map.d_game_map);
        this.d_conquest_map = p_conquest_map;
    }

    /**
<<<<<<< Updated upstream
     * Method to load borders
     * @throws Exception if any exception rises in the code block
=======
     * Loads borders
     * @throws Exception if any exceptions are caught
>>>>>>> Stashed changes
     */
    @Override
    public void loadBorders() throws Exception {
        LinkedHashMap<Integer, List<Integer>> l_borders =  d_conquest_map.readBorders();
        this.d_game_map.d_borders = l_borders;
    }

    /**
<<<<<<< Updated upstream
     * Load continents
     * @throws Exception if any exception rises in the code block
=======
     * Loads continents
     * @throws Exception if any exceptions are caught
>>>>>>> Stashed changes
     */
    @Override
    public void loadContinents() throws Exception {
        List<Continent> l_continents = d_conquest_map.readContinents();
        this.d_game_map.d_continents = l_continents;
    }

    /**
<<<<<<< Updated upstream
     * Method to Load countries
     * @throws Exception if any exception rises in the code block
=======
     * Loads countries
     * @throws Exception if any exceptions are caught
>>>>>>> Stashed changes
     */
    @Override
    public void loadCountries() throws Exception {
        List<Country> l_countries =  d_conquest_map.readCountries();
        this.d_game_map.d_countries = l_countries;
    }

    /**
<<<<<<< Updated upstream
     * getter for ConquestMapReader Object
     * @return ConquestMapReader Object
=======
     * Conquest game map reader
     * @return conquest map
>>>>>>> Stashed changes
     */
    public ConquestGameMapReader getConquestMap() {
        return this.d_conquest_map;
    }
}
