package mapparser;

import java.io.File;

import constants.GameConstants;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author USER
 */
public class GameMapTest {

    @Test
    public void testLoadValidMap() {
        try {
            File l_file_dir = new File("").getCanonicalFile();
            String l_gamemap_filename = l_file_dir.getParent() + GameConstants.D_MAP_TEST_DIRECTORY + "valid-testmap.map";

            File l_gamemap_file = new File(l_gamemap_filename);
            mapparser.GameMap l_gamemap_obj = new mapparser.GameMap(l_gamemap_filename);

            // checking if continents loaded
            Assert.assertEquals(2, l_gamemap_obj.getContinentObjects().size());

            // checking if countries loaded
            Assert.assertEquals(6, l_gamemap_obj.getCountryObjects().size());

            // checking if countries loaded
            Assert.assertEquals(6, l_gamemap_obj.getBorders().size());

        } catch (Exception e) { }
    }

    @Test
    public void testValidateMap() {
        try {
            File l_file_dir = new File("").getCanonicalFile();
            String l_gamemap_filename = l_file_dir.getParent() + GameConstants.D_MAP_TEST_DIRECTORY + "valid-testmap.map";

            File l_gamemap_file = new File(l_gamemap_filename);
            mapparser.GameMap l_gamemap_obj = new mapparser.GameMap(l_gamemap_filename);

            // checking valid map case
            try {
                boolean l_ismap_valid = l_gamemap_obj.validateGameMap();
                Assert.assertEquals(true, l_ismap_valid);
            } catch (Exception e) { }

        } catch (Exception e) { }
    }
    
}
