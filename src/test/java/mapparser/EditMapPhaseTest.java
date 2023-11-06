package mapparser;

import constants.GameConstants;
import constants.GameMessageConstants;
import gameutils.GameException;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author USER
 */
public class EditMapPhaseTest {

    /**
     * @param args the command line arguments
     */
    public EditMapPhase d_map_edit;
    public GameMap p_map;

    @Before
    public void loadMap() {
        try {

            LoadMapPhase l_loadmap_phase = new LoadMapPhase("valid-testmap.map", true);
            l_loadmap_phase.setMapDirectory(GameConstants.D_MAP_TEST_DIRECTORY);
            l_loadmap_phase.executePhase();
            mapparser.GameMap l_gamemap_obj = l_loadmap_phase.getLoadedMap();
            EditMapPhase l_edit_map = new EditMapPhase(l_gamemap_obj, "valid-testmap.map");
            this.d_map_edit = l_edit_map;
            this.p_map = l_gamemap_obj;

        } catch (Exception e) {
        }

    }

    @After
    public void revokeMapFile() {
        try {
            this.d_map_edit.modifyMapFile(this.p_map, "valid-testmap.map");
        } catch (Exception e) {

        }

    }

    @Test
    public void addContinentTest() {

        GameMap l_edited_map = null;

        try {
            List<String> l_parameter_list = new ArrayList<String>();

            l_parameter_list.add("newcontinent");
            l_parameter_list.add("4");

            l_edited_map = this.d_map_edit.editContinent(this.p_map, "add", l_parameter_list);
        } catch (Exception e) {
        }

        Assert.assertEquals(3, l_edited_map.getContinentObjects().size());
        Assert.assertEquals("newcontinent", l_edited_map.getContinentObjects().get(l_edited_map.getContinentObjects().size() - 1).getContinentName());
        Assert.assertEquals(4, l_edited_map.getContinentObjects().get(l_edited_map.getContinentObjects().size() - 1).getSpecialNumber());
    }

    @Test
    public void removeContinentTest() {
        GameMap l_edited_map = null;

        try {
            List<String> l_parameter_list = new ArrayList<String>();

            l_parameter_list.add("Continent-Second");

            l_edited_map = this.d_map_edit.editContinent(this.p_map, "remove", l_parameter_list);
        } catch (Exception e) {
        }

        Assert.assertEquals(1, l_edited_map.getContinentObjects().size());
        Assert.assertEquals("Continent-First", l_edited_map.getContinentObjects().get(l_edited_map.getContinentObjects().size() - 1).getContinentName());

    }

    @Test
    public void addCountryTest() {

        GameMap l_edited_map = null;

        try {
            List<String> l_parameter_list = new ArrayList<String>();

            l_parameter_list.add("newcountry");
            l_parameter_list.add("Continent-Second");

            l_edited_map = this.d_map_edit.editCountry(this.p_map, "add", l_parameter_list);
        } catch (Exception e) {
        }

        Assert.assertEquals(7, l_edited_map.getCountryObjects().size());
        Assert.assertEquals("newcountry", l_edited_map.getCountryObjects().get(l_edited_map.getCountryObjects().size() - 1).getCountryName());
        Assert.assertEquals("Continent-Second", l_edited_map.getCountryObjects().get(l_edited_map.getCountryObjects().size() - 1).getContinentName());

    }

    @Test
    public void removeCountryTest() {

        GameMap l_edited_map = null;

        try {
            List<String> l_parameter_list = new ArrayList<String>();

            l_parameter_list.add("Country-Sixth");

            l_edited_map = this.d_map_edit.editCountry(this.p_map, "remove", l_parameter_list);
        } catch (Exception e) {
        }

        Assert.assertEquals(5, l_edited_map.getCountryObjects().size());
        Assert.assertEquals("Country-Fifth", l_edited_map.getCountryObjects().get(l_edited_map.getCountryObjects().size() - 1).getCountryName());

    }

    @Test
    public void addBorderTest() {

        GameMap l_edited_map = null;

        try {
            List<String> l_parameter_list = new ArrayList<String>();

            l_parameter_list.add("Country-First");
            l_parameter_list.add("Country-Sixth");

            l_edited_map = this.d_map_edit.editBorders(this.p_map, "add", l_parameter_list);
        } catch (Exception e) {
        }

        Assert.assertTrue(l_edited_map.getBorders().get(1).contains(6));
        Assert.assertEquals(4, l_edited_map.getBorders().get(1).size());

    }

    @Test
    public void removeBorderTest() {
        GameMap l_edited_map = null;

        try {
            List<String> l_parameter_list = new ArrayList<String>();

            l_parameter_list.add("Country-First");
            l_parameter_list.add("Country-Third");

            l_edited_map = this.d_map_edit.editBorders(this.p_map, "remove", l_parameter_list);
        } catch (Exception e) {
        }

        Assert.assertFalse(l_edited_map.getBorders().get(1).contains(3));
        Assert.assertEquals(2, l_edited_map.getBorders().get(1).size());
    }

    @Test
    public void saveMapTest() {

        try {
            GameMap l_edited_map = null;
            List<String> l_parameter_list = new ArrayList<String>();

            l_parameter_list.add("newcontinent1");
            l_parameter_list.add("2");

            l_edited_map = this.d_map_edit.editContinent(this.p_map, "add", l_parameter_list);

            l_parameter_list = new ArrayList<String>();

            l_parameter_list.add("newcountry1");
            l_parameter_list.add("continent1");

            l_edited_map = this.d_map_edit.editCountry(l_edited_map, "add", l_parameter_list);

            l_parameter_list = new ArrayList<String>();

            l_parameter_list.add("newcountry2");
            l_parameter_list.add("continent1");

            l_edited_map = this.d_map_edit.editCountry(l_edited_map, "add", l_parameter_list);

            l_parameter_list = new ArrayList<String>();

            l_parameter_list.add("newcountry1");
            l_parameter_list.add("newcountry2");

            l_edited_map = this.d_map_edit.editBorders(l_edited_map, "add", l_parameter_list);

            l_parameter_list = new ArrayList<String>();

            l_parameter_list.add("newcountry2");
            l_parameter_list.add("newcountry1");

            l_edited_map = this.d_map_edit.editBorders(l_edited_map, "add", l_parameter_list);

            //saving map 
            boolean l_is_map_valid = l_edited_map.validateGameMap();
            if (l_is_map_valid) {
                l_edited_map = this.d_map_edit.modifyMapFile(l_edited_map, "valid-testmap.map");

            }

            GameMap l_new_map = new GameMap("valid-testmap.map");
            LoadMapPhase l_loadmap_phase = new LoadMapPhase("valid-testmap.map", true);
            l_loadmap_phase.setMapDirectory(GameConstants.D_MAP_TEST_DIRECTORY);
            l_loadmap_phase.loadGameMap(l_new_map);
            l_new_map = l_loadmap_phase.getLoadedMap();

            Assert.assertEquals(3, l_new_map.getContinentObjects().size());
            Assert.assertEquals(8, l_new_map.getCountryObjects().size());
            Assert.assertEquals(8, l_new_map.getBorders().size());
            Assert.assertEquals(2, l_new_map.getBorders().get(6).size());
            Assert.assertEquals(2, l_new_map.getBorders().get(7).size());
            Assert.assertEquals("newcontinent1", l_new_map.getContinentObjects().get(2).getContinentName());
            Assert.assertEquals("newcountry1", l_new_map.getCountryObjects().get(6).getCountryName());
            Assert.assertEquals("newcountry2", l_new_map.getCountryObjects().get(7).getCountryName());
            Assert.assertTrue(l_new_map.getBorders().get(6).contains(7));
            Assert.assertTrue(l_new_map.getBorders().get(6).contains(7));
            
        } catch (Exception e) {

        }

    }

}
