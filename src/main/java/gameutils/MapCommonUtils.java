/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gameutils;

import constants.GameMessageConstants;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author USER
 */
public class MapCommonUtils {

    /**
     *
     * @param p_file_path The path to  the map file.
     * @param p_from_keyword Starting keyword to search for in the file.
     * @param p_to_keyword Ending keyword to search for in the file.
     * @return A list of map details.
     * @throws MapException If there is an issue while reading or parsing the map.
     */
    public static List<String> getMapDetails(String p_file_path, String p_from_keyword, String p_to_keyword) {
        List<String> l_all_lines = null;
        List<String> l_map_details = new ArrayList<String>();
        try {
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
            
            if(l_index_end == -1){
                l_index_end = l_line_count - 1;
            }
            for (int l_i = l_index_start; l_i < l_index_end; l_i++) {
                l_map_details.add(l_all_lines.get(l_i));
            }
            
            if(l_index_start == -1 || l_index_end == -1){
                throw new MapException(GameMessageConstants.D_MAP_LOAD_FAILED);
            }

        } catch (MapException e) {
            System.out.println(GameMessageConstants.D_MAP_LOAD_FAILED);
        } catch(Exception e){
            System.out.println(GameMessageConstants.D_INTERNAL_ERROR);
        }

        return l_map_details;
    }
}
