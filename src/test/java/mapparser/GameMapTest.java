package mapparser;

import constants.GameConstants;
import constants.GameMessageConstants;
import gameplay.GameInformation;
import gameplay.Player;
import gameutils.GameException;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 * The class is used for testing GameMap class.
 */
public class GameMapTest {

    /**
     * Function checks for the continents, countries and its corresponding neighbour countries.
     */
    @Test
    public void testLoadValidMap() {
        try {

            LoadMapPhase l_loadmap_phase = new LoadMapPhase("valid-testmap.map", true);
            l_loadmap_phase.setMapDirectory(GameConstants.D_MAP_TEST_DIRECTORY);
            l_loadmap_phase.executePhase();
            mapparser.GameMap l_gamemap_obj = l_loadmap_phase.getLoadedMap();

            // checking if continents loaded
            Assert.assertEquals(2, l_gamemap_obj.getContinentObjects().size());

            // checking if countries loaded
            Assert.assertEquals(6, l_gamemap_obj.getCountryObjects().size());

            // checking if borders loaded
            Assert.assertEquals(6, l_gamemap_obj.getBorders().size());

        } catch (Exception e) { }
    }

    /**
     * Function checks for the map.
     */
    @Test
    public void testValidateMap() {
        try {
            LoadMapPhase l_loadmap_phase = new LoadMapPhase("valid-testmap.map", true);
            l_loadmap_phase.setMapDirectory(GameConstants.D_MAP_TEST_DIRECTORY);
            l_loadmap_phase.executePhase();
            mapparser.GameMap l_gamemap_obj = l_loadmap_phase.getLoadedMap();

            // checking valid map case
            try {
                boolean l_ismap_valid = l_gamemap_obj.validateGameMap();
                Assert.assertEquals(true, l_ismap_valid);
            } catch (Exception e) { }

        } catch (Exception e) { }
    }

    /**
     * Function checks whether
     */
    @Test
    public void testValidateMapEmpty() {
        try {
            LoadMapPhase l_loadmap_phase = new LoadMapPhase("invalid-testmap-empty.map", true);
            l_loadmap_phase.setMapDirectory(GameConstants.D_MAP_TEST_DIRECTORY);
            l_loadmap_phase.executePhase();
            mapparser.GameMap l_gamemap_obj = l_loadmap_phase.getLoadedMap();

            // checking valid map case
            try {
                boolean l_ismap_valid = l_gamemap_obj.validateGameMap();
            } catch (GameException e) {
                Assert.assertEquals(GameMessageConstants.D_MAP_EMPTY_CONTINENTS, e.getMessage());
            } catch (Exception e) { }

        } catch (Exception e) { }
    }

    /**
     * Function checks for the countries with no other neighbouring country.
     */
    @Test
    public void testValidateMapBorderConnectivity() {
        try {
            LoadMapPhase l_loadmap_phase = new LoadMapPhase("invalid-testmap-connection-1.map", true);
            l_loadmap_phase.setMapDirectory(GameConstants.D_MAP_TEST_DIRECTORY);
            l_loadmap_phase.executePhase();
            mapparser.GameMap l_gamemap_obj = l_loadmap_phase.getLoadedMap();

            // checking valid map case
            try {
                boolean l_ismap_valid = l_gamemap_obj.validateGameMap();
            } catch (GameException e) {
                Assert.assertEquals("Country: country3" + " " + GameMessageConstants.D_MAP_COUNTRY_EMPTY_BORDERS, e.getMessage());
            } catch (Exception e) { }

        } catch (Exception e) { }
    }

    /**
     * Functions checks for the countries and its neighbouring countries to be matched.
     */
    @Test
    public void testValidateMapCountryConnectivity() {
        try {
            LoadMapPhase l_loadmap_phase = new LoadMapPhase("invalid-testmap-connection-2.map", true);
            l_loadmap_phase.setMapDirectory(GameConstants.D_MAP_TEST_DIRECTORY);
            l_loadmap_phase.executePhase();
            mapparser.GameMap l_gamemap_obj = l_loadmap_phase.getLoadedMap();

            // checking valid map case
            try {
                boolean l_ismap_valid = l_gamemap_obj.validateGameMap();
            } catch (GameException e) {
                Assert.assertEquals("Country: country3" + " " + GameMessageConstants.D_MAP_COUNTRY_INVALID_BORDERS, e.getMessage());
            } catch (Exception e) { }

        } catch (Exception e) { }
    }

    /**
     * Test for show map without players
     */
    @Test
    public void showMapTestWithoutPlayers() {
        ByteArrayOutputStream l_out_content = null;

        try {
            l_out_content = new ByteArrayOutputStream();
            System.setOut(new PrintStream(l_out_content));
            l_out_content.reset();

            LoadMapPhase l_loadmap_phase = new LoadMapPhase("showmap-test.map", true);
            l_loadmap_phase.setMapDirectory(GameConstants.D_MAP_TEST_DIRECTORY);
            l_loadmap_phase.executePhase();
            mapparser.GameMap l_gamemap_obj = l_loadmap_phase.getLoadedMap();
            l_gamemap_obj.showMap(false);

        } catch (Exception e) {

        }

        Assert.assertTrue(l_out_content.toString().length() > 0);

    }

    /**
     * Test for show map with players
     */
    @Test
    public void showMapTestWithPlayers() {

        ByteArrayOutputStream l_out_content_player = null, l_out_content_no_player = null;
        GameInformation d_current_game_info = null;
        int l_no_player_length = -1;
        int l_player_length = -1;

        try {
            d_current_game_info = GameInformation.getInstance();

            LoadMapPhase l_loadmap_phase = new LoadMapPhase("showmap-test.map", true);
            l_loadmap_phase.setMapDirectory(GameConstants.D_MAP_TEST_DIRECTORY);
            l_loadmap_phase.executePhase();
            mapparser.GameMap l_gamemap_obj = l_loadmap_phase.getLoadedMap();

            l_out_content_no_player = new ByteArrayOutputStream();
            System.setOut(new PrintStream(l_out_content_no_player));
            l_out_content_no_player.reset();

            l_gamemap_obj.showMap(false);
            
            l_no_player_length = l_out_content_no_player.toString().length();
        
            l_out_content_no_player.close();
            
            d_current_game_info.setCurrenGameMap(l_gamemap_obj);

            LinkedHashMap<String, Player> l_player_list = d_current_game_info.getPlayerList();
            Player l_player_obj = new Player("playerfirst");
            l_player_list.put("playerfirst", l_player_obj);

            List<GameMap.Country> l_countries = d_current_game_info.getGameMap().getCountryObjects();

            l_countries.get(0).setPlayerName("playerfirst");
            l_countries.get(3).setPlayerName("playerfirst");
            l_countries.get(4).setPlayerName("playerfirst");

            Player l_player_first_obj = l_player_list.get("playerfirst");
            l_player_first_obj.setConqueredCountries(Arrays.asList(new GameMap.Country[]{l_countries.get(0), l_countries.get(3), l_countries.get(4)}));

            l_out_content_player = new ByteArrayOutputStream();
            System.setOut(new PrintStream(l_out_content_player));
            l_out_content_player.reset();

            l_gamemap_obj.showMap(true);
            
            l_player_length = l_out_content_player.toString().length();
            
            l_out_content_player.close();
            
        } catch (Exception e) {
            
        }
        
       Assert.assertTrue((l_player_length +l_no_player_length) > 0 );
    }
    
}
