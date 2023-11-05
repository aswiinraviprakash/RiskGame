package gameplay;

import constants.GameConstants;
import constants.GameMessageConstants;
import gameutils.GameException;
import mapparser.GameMap;
import mapparser.LoadMapPhase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *  Issue order test phase.
 */
public class IssueOrderPhaseTest {
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
     *  Test for deploy order.
     */
    @Test
    public void testDeployOrder() {
        LinkedHashMap<String, Player> l_player_list = d_current_game_info.getPlayerList();
        Player l_player_first_obj = l_player_list.get("playerfirst");
        Player l_player_Second_obj = l_player_list.get("playersecond");
        List<GameMap.Country> l_countries = d_current_game_info.getGameMap().getCountryObjects();

        l_player_first_obj.setConqueredCountries(Arrays.asList(new GameMap.Country[]{ l_countries.get(0), l_countries.get(3), l_countries.get(4)}));
        l_player_first_obj.setCurrentArmies(8);
        l_player_Second_obj.setConqueredCountries(Arrays.asList(new GameMap.Country[]{ l_countries.get(1), l_countries.get(2), l_countries.get(5)}));
        l_player_first_obj.setCurrentArmies(8);

        try {
            IssueOrderPhase l_phase_obj = new IssueOrderPhase();
            l_phase_obj.validateAndExecuteCommands("deploy Country-First 8", l_player_first_obj);
            DeployOrder l_order_obj = (DeployOrder) l_player_first_obj.next_order();

            // checking successful issuing of deloy order
            Assert.assertEquals(8, l_order_obj.getArmiesNumber());
        } catch (Exception e) {}
    }

    /**
     * Test for deploy order, but with excess armies.
     */
    @Test
    public void testDeployOrderWithExcessArmies() {
        LinkedHashMap<String, Player> l_player_list = d_current_game_info.getPlayerList();
        Player l_player_first_obj = l_player_list.get("playerfirst");
        Player l_player_Second_obj = l_player_list.get("playersecond");
        List<GameMap.Country> l_countries = d_current_game_info.getGameMap().getCountryObjects();

        l_player_first_obj.setConqueredCountries(Arrays.asList(new GameMap.Country[]{ l_countries.get(0), l_countries.get(3), l_countries.get(4)}));
        l_player_first_obj.setCurrentArmies(8);
        l_player_Second_obj.setConqueredCountries(Arrays.asList(new GameMap.Country[]{ l_countries.get(1), l_countries.get(2), l_countries.get(5)}));
        l_player_first_obj.setCurrentArmies(8);

        try {
            IssueOrderPhase l_phase_obj = new IssueOrderPhase();
            l_phase_obj.validateAndExecuteCommands("deploy Country-First 10", l_player_first_obj);
        } catch (GameException e) {
            Assert.assertEquals(GameMessageConstants.D_ARMIES_EXCEEDED + "\nAvailable Armies: " + l_player_first_obj.getCurrentArmies(), e.getMessage());
        } catch (Exception e) {}
    }

    /**
     * Test for deploy order, but invalid command.
     */
    @Test
    public void testDeployOrderWithInvalidCommand() {
        LinkedHashMap<String, Player> l_player_list = d_current_game_info.getPlayerList();
        Player l_player_first_obj = l_player_list.get("playerfirst");
        Player l_player_Second_obj = l_player_list.get("playersecond");
        List<GameMap.Country> l_countries = d_current_game_info.getGameMap().getCountryObjects();

        l_player_first_obj.setConqueredCountries(Arrays.asList(new GameMap.Country[]{ l_countries.get(0), l_countries.get(3), l_countries.get(4)}));
        l_player_first_obj.setCurrentArmies(8);
        l_player_Second_obj.setConqueredCountries(Arrays.asList(new GameMap.Country[]{ l_countries.get(1), l_countries.get(2), l_countries.get(5)}));
        l_player_first_obj.setCurrentArmies(8);

        try {
            IssueOrderPhase l_phase_obj = new IssueOrderPhase();
            l_phase_obj.validateAndExecuteCommands("deloy Country-First 10", l_player_first_obj);
            DeployOrder l_order_obj = (DeployOrder) l_player_first_obj.next_order();
        } catch (GameException e) {
            Assert.assertEquals(GameMessageConstants.D_COMMAND_INVALID, e.getMessage());
        } catch (Exception e) {}
    }
}
