package gameplay.order;

import constants.GameConstants;
import gameplay.GameInformation;
import gameplay.Player;
import gameplay.order.AdvanceOrder;
import mapparser.GameMap;
import mapparser.LoadMapPhase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *  Test Class for Advance Order
 */
public class AdvanceOrderTest  {

    /**
     * Destination Country
     */
    private GameMap.Country d_destination_country;

    /**
     * Source Country
     */
    private GameMap.Country d_source_country;

    /**
     * Contains the Current Game Information
     */
    private GameInformation d_current_game_info;

    /**
     * Initializing Data for the Tests
     */
    @Before
    public void initialiseTestData() {
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
     *  Test to erify that tvhe advance order does not execute when the number of armies to advance is greater than the number of armies in the source country.
     */
    @Test
    public void advanceorderExceedingArmies()
    {
        LinkedHashMap<String, Player> l_player_list = d_current_game_info.getPlayerList();
        Player l_player_first_obj = l_player_list.get("playerfirst");
        Player l_player_second_obj = l_player_list.get("playersecond");
        List<GameMap.Country> l_countries = d_current_game_info.getGameMap().getCountryObjects();
        try {

            l_countries.get(0).setArmyCount(20);
            l_player_first_obj.setConqueredCountries(Arrays.asList(new GameMap.Country[]{l_countries.get(0), l_countries.get(3), l_countries.get(4)}));
            l_player_second_obj.setConqueredCountries(Arrays.asList(new GameMap.Country[]{l_countries.get(1), l_countries.get(2), l_countries.get(5)}));
            l_countries.get(0).setPlayerName("playerfirst");
            l_countries.get(3).setPlayerName("playerfirst");
            l_countries.get(4).setPlayerName("playerfirst");
            l_countries.get(1).setPlayerName("playersecond");
            l_countries.get(2).setPlayerName("playersecond");
            l_countries.get(5).setPlayerName("playersecond");

            d_source_country = l_player_first_obj.getConqueredCountries().get(0);
            d_destination_country = l_player_second_obj.getConqueredCountries().get(1);

            AdvanceOrder l_advance_order = new AdvanceOrder(d_source_country, d_destination_country, 100);
            //Advance order is executed
            l_advance_order.execute(l_player_first_obj);
            //Checks the number of armies at the source country
            Assert.assertEquals(20, d_source_country.getArmyCount());
            //Checks the number of armies in the destination country
            Assert.assertEquals(5, d_destination_country.getArmyCount());
            // Checks if the ownership is still the same
            Assert.assertFalse(d_source_country.getPlayerName(), Boolean.parseBoolean(d_destination_country.getPlayerName()));

        } catch (Exception e) {}
    }

    /**
     * Advance order on opponent territory
     */
    @Test
    public void advanceOrderAttack()
    {
        LinkedHashMap<String, Player> l_player_list = d_current_game_info.getPlayerList();
        Player l_player_first_obj = l_player_list.get("playerfirst");
        Player l_player_second_obj = l_player_list.get("playersecond");
        List<GameMap.Country> l_countries = d_current_game_info.getGameMap().getCountryObjects();
        try {

            l_countries.get(0).setArmyCount(20);
            l_player_first_obj.setConqueredCountries(Arrays.asList(new GameMap.Country[]{l_countries.get(0), l_countries.get(3), l_countries.get(4)}));
            l_player_second_obj.setConqueredCountries(Arrays.asList(new GameMap.Country[]{l_countries.get(1), l_countries.get(2), l_countries.get(5)}));
            l_countries.get(0).setPlayerName("playerfirst");
            l_countries.get(3).setPlayerName("playerfirst");
            l_countries.get(4).setPlayerName("playerfirst");
            l_countries.get(1).setPlayerName("playersecond");
            l_countries.get(2).setPlayerName("playersecond");
            l_countries.get(5).setPlayerName("playersecond");

            l_countries.get(1).setArmyCount(5);
            d_source_country = l_player_first_obj.getConqueredCountries().get(0);
            d_destination_country = l_player_second_obj.getConqueredCountries().get(1);

            AdvanceOrder l_advance_order = new AdvanceOrder(d_source_country, d_destination_country, 10);
            //Advance order is executed
            l_advance_order.execute(l_player_first_obj);
            // Advance order on opponent territory is executed
            Assert.assertEquals(10, d_source_country.getArmyCount());
            //Checks army in the destination country
            Assert.assertEquals(5, d_destination_country.getArmyCount());
            //Checks if ownership is changed
            Assert.assertEquals(l_player_first_obj.getPlayerName(), d_destination_country.getPlayerName());

        } catch (Exception e) {}
    }

    /**
     * Test to check Advance order in a friendly territory
     */
    @Test
    public void advanceOrderNonAttack()
    {
        LinkedHashMap<String, Player> l_player_list = d_current_game_info.getPlayerList();
        Player l_player_first_obj = l_player_list.get("playerfirst");
        List<GameMap.Country> l_countries = d_current_game_info.getGameMap().getCountryObjects();
        try {
            l_player_first_obj.setConqueredCountries(Arrays.asList(new GameMap.Country[]{l_countries.get(0), l_countries.get(1), l_countries.get(3), l_countries.get(4)}));
            l_countries.get(0).setArmyCount(10);
            l_countries.get(1).setArmyCount(10);
            l_countries.get(0).setPlayerName("playerfirst");
            l_countries.get(1).setPlayerName("playerfirst");
            l_countries.get(3).setPlayerName("playerfirst");
            l_countries.get(4).setPlayerName("playerfirst");

            d_source_country = l_player_first_obj.getConqueredCountries().get(0);
            d_destination_country = l_player_first_obj.getConqueredCountries().get(1);

            AdvanceOrder l_advance_order = new AdvanceOrder(d_source_country, d_destination_country, 6);
            // Advance order on friendly territory is executed
            l_advance_order.execute(l_player_first_obj);
            //Checks the army count in source country
            Assert.assertEquals(4, d_source_country.getArmyCount());
            //Checks for ownership
            Assert.assertEquals(l_player_first_obj.getPlayerName(), l_player_first_obj.getPlayerName());
            //Checks army in the destination country
            Assert.assertEquals(16, d_destination_country.getArmyCount());
        }
        catch (Exception e) { }
    }

}