package gameutils;

import java.util.List;
import org.junit.Test;
import constants.GameConstants;
import java.io.File;
import java.util.ArrayList;
import org.junit.Assert;

/**
 * Test class for validating map utilities and functions.
 */
public class MapCommonUtilTest {

    /**
     * Test for verifying continents.
     */
    @Test
    public void mapParsingContinentTest() {

        List<String> l_map_data = new ArrayList<String>();

        String l_from_keyword = "continents";
        String l_to_keyword = "countries";

        try {
            File l_file;
            File l_file_dir = new File("").getCanonicalFile();
            String l_file_path = l_file_dir.getParent() + GameConstants.D_MAP_TEST_DIRECTORY + "valid-testmap.map";
            l_map_data = MapCommonUtils.getMapDetails(l_file_path, l_from_keyword, l_to_keyword);
        } catch (Exception e) {
        }

        Assert.assertEquals(2, l_map_data.size());
    }

    /**
     * Test for verifying countries.
     */
    @Test
    public void mapParsingCountryTest() {

        List<String> l_map_data = new ArrayList<String>();

        String l_from_keyword = "countries";
        String l_to_keyword = "borders";

        try {
            File l_file;
            File l_file_dir = new File("").getCanonicalFile();
            String l_file_path = l_file_dir.getParent() + GameConstants.D_MAP_TEST_DIRECTORY + "valid-testmap.map";
            l_map_data = MapCommonUtils.getMapDetails(l_file_path, l_from_keyword, l_to_keyword);
        } catch (Exception e) {
        }

        Assert.assertEquals(6, l_map_data.size());
    }

    /**
     * Test for verifying the borders.
     */
    @Test
    public void mapParsingBorderTest() {

        List<String> l_map_data = new ArrayList<String>();

        String l_from_keyword = "borders";
        String l_to_keyword = "end";

        try {
            File l_file;
            File l_file_dir = new File("").getCanonicalFile();
            String l_file_path = l_file_dir.getParent() + GameConstants.D_MAP_TEST_DIRECTORY + "valid-testmap.map";
            l_map_data = MapCommonUtils.getMapDetails(l_file_path, l_from_keyword, l_to_keyword);
        } catch (Exception e) {
        }

        Assert.assertEquals(6, l_map_data.size());
    }
}
