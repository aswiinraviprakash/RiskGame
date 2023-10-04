package gameutils;

import java.util.ArrayList;
import java.util.List;
import mapparser.GameMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author USER
 */
public class GameMapTest {
    
    public List<List<Integer>> d_borders = new ArrayList<List<Integer>>();
    public List<GameMap.Country> d_countries = new ArrayList<GameMap.Country>();
    public List<GameMap.Continent> d_continents = new ArrayList<GameMap.Continent>();
    
    @Before public static void loadMap(){
        //GameMap
    }
    
    @Test public void testLoadContinents(){
        
    }
}
