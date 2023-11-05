package gameplay;

import constants.GameConstants;
import mapparser.GameMap;
import mapparser.LoadMapPhase;
import org.junit.Test;
import org.junit.Before;
import org.junit.Assert;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Reinforcement Phase test.
 */
public class ReinforcementPhaseTest {

    /**
     * Contains current game info.
     */
    private GameInformation d_current_game_info;

    /**
     * Initializes test data.
     */
    @Before
    public void initializeTestData() {
        try {

            d_current_game_info = GameInformation.getInstance();

            String l_gamemap_filename = "valid-testmap.map";
            LoadMapPhase l_loadmap_phase = new LoadMapPhase(l_gamemap_filename, false);
            l_loadmap_phase.setMapDirectory(GameConstants.D_MAP_TEST_DIRECTORY);
            l_loadmap_phase.executePhase();
            mapparser.GameMap l_gamemap_obj = l_loadmap_phase.getLoadedMap();

            d_current_game_info.setCurrenGameMap(l_gamemap_obj);

            LinkedHashMap<String, Player> l_player_list = d_current_game_info.getPlayerList();
            Player l_player_obj = new Player("playerfirst");
            l_player_list.put("playerfirst", l_player_obj);

            l_player_obj = new Player("playersecond");
            l_player_list.put("playersecond", l_player_obj);
            d_current_game_info.setPlayerList(l_player_list);

        } catch (Exception e) {}
    }

    /**
     * Test to check armies assignment.
     */
    @Test
    public void testArmiesAssigment() {
        try {
            LinkedHashMap<String, Player> l_player_list = d_current_game_info.getPlayerList();
            Player l_player_first_obj = l_player_list.get("playerfirst");
            Player l_player_Second_obj = l_player_list.get("playersecond");
            List<GameMap.Country> l_countries = d_current_game_info.getGameMap().getCountryObjects();

            l_player_first_obj.setConqueredCountries(Arrays.asList(new GameMap.Country[]{ l_countries.get(0), l_countries.get(3), l_countries.get(4)}));
            l_player_Second_obj.setConqueredCountries(Arrays.asList(new GameMap.Country[]{ l_countries.get(1), l_countries.get(2), l_countries.get(5)}));

            ReinforcementPhase l_phase_obj = new ReinforcementPhase();
            l_phase_obj.executePhase();

            //checking playerfirst armies assigned
            Assert.assertEquals(8, l_player_first_obj.getCurrentArmies());

            //checking playersecond armies assigned
            Assert.assertEquals(8, l_player_Second_obj.getCurrentArmies());

        } catch (Exception e) {}
    }

    /**
     * Test to assign armies with continent bonus.
     */
    @Test
    public void testArmiesAssigmentWithContientBonus() {
        try {
            LinkedHashMap<String, Player> l_player_list = d_current_game_info.getPlayerList();
            Player l_player_first_obj = l_player_list.get("playerfirst");
            Player l_player_Second_obj = l_player_list.get("playersecond");
            List<GameMap.Country> l_countries = d_current_game_info.getGameMap().getCountryObjects();

            l_player_first_obj.setConqueredCountries(Arrays.asList(new GameMap.Country[]{ l_countries.get(0), l_countries.get(3), l_countries.get(4), l_countries.get(5)}));
            l_player_Second_obj.setConqueredCountries(Arrays.asList(new GameMap.Country[]{ l_countries.get(1), l_countries.get(2)}));

            ReinforcementPhase l_phase_obj = new ReinforcementPhase();
            l_phase_obj.executePhase();

            //checking playerfirst armies assigned with control bonus
            Assert.assertEquals(12, l_player_first_obj.getCurrentArmies());

            //checking playersecond armies assigned
            Assert.assertEquals(8, l_player_Second_obj.getCurrentArmies());

        } catch (Exception e) {}
    }

    /**
     * Test for armies assignment, default values.
     */
    @Test
    public void testArmiesAssigmentDefault() {
        try {
            LinkedHashMap<String, Player> l_player_list = d_current_game_info.getPlayerList();
            Player l_player_first_obj = l_player_list.get("playerfirst");
            Player l_player_Second_obj = l_player_list.get("playersecond");
            List<GameMap.Country> l_countries = d_current_game_info.getGameMap().getCountryObjects();

            ReinforcementPhase l_phase_obj = new ReinforcementPhase();
            l_phase_obj.executePhase();

            //checking playerfirst armies assigned without countries assigned
            Assert.assertEquals(8, l_player_first_obj.getCurrentArmies());

            //checking playersecond armies assigned without countries assigned
            Assert.assertEquals(8, l_player_Second_obj.getCurrentArmies());

        } catch (Exception e) {}
    }

}
