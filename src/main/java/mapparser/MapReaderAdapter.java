package mapparser;

import java.util.LinkedHashMap;
import java.util.List;
import mapparser.GameMap.Continent;
import mapparser.GameMap.Country;

/**
 * Class for map reader adapter
 */
public class MapReaderAdapter extends DominationGameMapReader {

    /**
     * Contains conquest map
     */
    private ConquestGameMapReader d_conquest_map;

    /**
     * Map reader adapter
     * @param p_conquest_map Conquestmapreader object
     */
    // load functions that will be overridden 
    public MapReaderAdapter(ConquestGameMapReader p_conquest_map) {
        super(p_conquest_map.d_file_path, p_conquest_map.d_game_map);
        this.d_conquest_map = p_conquest_map;
    }

    /**
     * Method to load borders
     * @throws Exception if any exception rises in the code block
     */
    @Override
    public void loadBorders() throws Exception {
        LinkedHashMap<Integer, List<Integer>> l_borders =  d_conquest_map.readBorders();
        this.d_game_map.d_borders = l_borders;
    }

    /**
     * Load continents
     * @throws Exception if any exception rises in the code block
     */
    @Override
    public void loadContinents() throws Exception {
        List<Continent> l_continents = d_conquest_map.readContinents();
        this.d_game_map.d_continents = l_continents;
    }

    /**
     * Method to Load countries
     * @throws Exception if any exception rises in the code block
     */
    @Override
    public void loadCountries() throws Exception {
        List<Country> l_countries =  d_conquest_map.readCountries();
        this.d_game_map.d_countries = l_countries;
    }

    /**
     * Getter for ConquestMapReader Object
     * @return ConquestMapReader Object
     */
    public ConquestGameMapReader getConquestMap() {
        return this.d_conquest_map;
    }
}
