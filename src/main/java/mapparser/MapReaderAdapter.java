package mapparser;

import gameutils.MapCommonUtils;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import mapparser.GameMap.Continent;
import mapparser.GameMap.Country;

public class MapReaderAdapter extends DominationGameMapReader {

    private ConquestGameMapReader d_conquest_map;

    // load functions that will be overridden 
    public MapReaderAdapter(String p_map_path, ConquestGameMapReader p_conquest_map, GameMap p_game_map) {
        super(p_map_path, p_game_map);
        this.d_game_map = p_game_map;
        this.d_conquest_map = p_conquest_map;
    }

    @Override
    public void loadBorders() {
        LinkedHashMap<Integer, List<Integer>> l_borders =  d_conquest_map.readBorders();
        this.d_game_map.d_borders = l_borders;
    }

    @Override
    public void loadContinents() {
        List<Continent> l_continents = d_conquest_map.readContinents();
        this.d_game_map.d_continents = l_continents;
    }

    @Override
    public void loadCountries() {
        List<Country> l_countries =  d_conquest_map.readCountries();
        this.d_game_map.d_countries = l_countries;
    }

    public ConquestGameMapReader getConquestMap() {
        return this.d_conquest_map;
    }

//    public GameMap readConquestMap(String p_file_path) {
//        return this.translate(this.d_conquest_map.readConquestGameMap(p_file_path), p_file_path);
//    }


}
