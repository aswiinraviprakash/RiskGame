package mapparser;

import constants.GameConstants;
import constants.GameMessageConstants;
import gameutils.GameException;
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
    
}
