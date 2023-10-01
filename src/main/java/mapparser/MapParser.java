package mapparser;

import constants.GameMessageConstants;
import gameutils.MapException;

/**
 *
 * @author USER
 */
public class MapParser {
    
    public String d_map_file_name; 
    
    public MapParser(){
        this.d_map_file_name = "D:\\Concordia\\Fall2023\\APP\\Project\\canada.map";
        //if this does not exist throw error;
    }
    
    public MapParser(String p_map_file_name){
        this.d_map_file_name = p_map_file_name;
    }

    public Map loadMap() {

        //create map object
        Map l_map = new Map(this.d_map_file_name);
        
        try{
            if(l_map.d_borders == null || l_map.d_countries == null || l_map.d_continents == null){
            throw new MapException(GameMessageConstants.D_MAP_LOAD_FAILED);
        }
        } catch(MapException e){
            System.out.println(e.getMessage());
        }catch (Exception e) {
            System.out.println(GameMessageConstants.D_INTERNAL_ERROR);
        }
       
        
        return l_map;
    }

}
