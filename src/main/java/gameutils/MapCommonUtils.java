package gameutils;

import constants.GameMessageConstants;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * This class reads a map file and extracts a specific section of the file based
 * on starting and ending keywords.
 */
public class MapCommonUtils {

    /**
     * Method returns the complete map details.
     * @param p_file_path The path to  the map file.
     * @param p_from_keyword Starting keyword to search for in the file.
     * @param p_to_keyword Ending keyword to search for in the file.
     * @return A list of map details.
     * @throws Exception If there is an error in the execution or validation.
     */
    public static List<String> getMapDetails(String p_file_path, String p_from_keyword, String p_to_keyword) throws Exception {
        List<String> l_all_lines = null;
        List<String> l_map_details = new ArrayList<String>();
        l_all_lines = Files.readAllLines(Paths.get(p_file_path));
        int l_line_count = Files.readAllLines(Paths.get(p_file_path)).size();
        int l_index_start = -1;
        int l_index_end = -1;

        for (int l_i = 0; l_i < l_all_lines.size(); l_i++) {
            if (l_all_lines.get(l_i).contains(p_from_keyword)) {
                l_index_start = l_i + 1;
            }
            if (l_all_lines.get(l_i).contains(p_to_keyword)) {
                l_index_end = l_i - 1;
                break;
            }
        }

        if (l_index_end == -1) {
            l_index_end = l_line_count;
        }
        for (int l_i = l_index_start; l_i < l_index_end; l_i++) {
            l_map_details.add(l_all_lines.get(l_i));
        }

        if (l_index_start == -1 || l_index_end == -1) {
            throw new GameException(GameMessageConstants.D_MAP_LOAD_FAILED);
        }

        return l_map_details;
    }

}
