package mapparser;

import java.util.LinkedHashMap;
import java.util.List;
import mapparser.GameMap.Continent;
import mapparser.GameMap.Country;

public class MapReaderAdapter extends DominationGameMapReader {

    private ConquestGameMapReader d_conquest_map;

    // load functions that will be overridden 
    public MapReaderAdapter(ConquestGameMapReader p_conquest_map) {
        super(p_conquest_map.d_file_path, p_conquest_map.d_game_map);
        this.d_conquest_map = p_conquest_map;
    }

    @Override
    public void loadBorders() throws Exception {
        LinkedHashMap<Integer, List<Integer>> l_borders =  d_conquest_map.readBorders();
        this.d_game_map.d_borders = l_borders;
    }

    @Override
    public void loadContinents() throws Exception {
        List<Continent> l_continents = d_conquest_map.readContinents();
        this.d_game_map.d_continents = l_continents;
    }

    @Override
    public void loadCountries() throws Exception {
        List<Country> l_countries =  d_conquest_map.readCountries();
        this.d_game_map.d_countries = l_countries;
    }

    public ConquestGameMapReader getConquestMap() {
        return this.d_conquest_map;
    }
}
