package gameplay;

import java.util.Arrays;
import java.util.LinkedHashMap;
import constants.GameConstants;
import java.util.List;
import mapparser.GameMap;
import mapparser.LoadMapPhase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for the airlift order.
 */
public class AirliftOrderTest {

    private GameInformation d_current_game_info;
    private GameMap.Country d_destination_country;
    private GameMap.Country d_source_country;

    /**
     * Initialises test data.
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

            List<GameMap.Country> l_countries = d_current_game_info.getGameMap().getCountryObjects();

            l_countries.get(0).setPlayerName("playerfirst");
            l_countries.get(3).setPlayerName("playerfirst");
            l_countries.get(4).setPlayerName("playerfirst");
            l_countries.get(1).setPlayerName("playersecond");
            l_countries.get(2).setPlayerName("playersecond");
            l_countries.get(5).setPlayerName("playersecond");
        } catch (Exception e) {
        }
    }

    /**
     * Test for verifying the ownership change after the airlift.
     */
    @Test
    public void airLiftAttackOwnershipChangeTest() {
        LinkedHashMap<String, Player> l_player_list = d_current_game_info.getPlayerList();
        Player l_player_first_obj = l_player_list.get("playerfirst");
        Player l_player_second_obj = l_player_list.get("playersecond");
        List<GameMap.Country> l_countries = d_current_game_info.getGameMap().getCountryObjects();
        l_countries.get(0).setArmyCount(10);

        try {

            l_player_first_obj.setConqueredCountries(Arrays.asList(new GameMap.Country[]{l_countries.get(0), l_countries.get(3), l_countries.get(4)}));
            l_player_second_obj.setConqueredCountries(Arrays.asList(new GameMap.Country[]{l_countries.get(1), l_countries.get(2), l_countries.get(5)}));

            d_source_country = l_player_first_obj.getConqueredCountries().get(0);
            d_destination_country = l_player_second_obj.getConqueredCountries().get(0);
            int l_armies = 6;

            AirliftOrder l_airlift_phase = new AirliftOrder(d_source_country, d_destination_country, l_armies);
            l_airlift_phase.execute(l_player_first_obj);

        } catch (Exception e) {
        }

        Assert.assertEquals(4, l_player_first_obj.getConqueredCountries().get(0).getArmyCount());
        Assert.assertEquals(1, l_player_first_obj.getConqueredCountries().get(3).getArmyCount());
        Assert.assertEquals(4, l_player_first_obj.getConqueredCountries().size());
        Assert.assertEquals(2, l_player_second_obj.getConqueredCountries().size());
        Assert.assertFalse(l_player_second_obj.getConqueredCountries().contains(d_destination_country));
        Assert.assertTrue(l_player_first_obj.getConqueredCountries().contains(d_destination_country));
        Assert.assertEquals(1, l_player_first_obj.getAvailableCards().size());

    }

    /**
     *  Test for verifying no changes after the airlift.
     */
    @Test
    public void airLiftAttackNoOwnershipChangeTest() {
        LinkedHashMap<String, Player> l_player_list = d_current_game_info.getPlayerList();
        Player l_player_first_obj = l_player_list.get("playerfirst");
        Player l_player_second_obj = l_player_list.get("playersecond");
        List<GameMap.Country> l_countries = d_current_game_info.getGameMap().getCountryObjects();

        l_countries.get(1).setArmyCount(10);

        try {

            l_player_first_obj.setConqueredCountries(Arrays.asList(new GameMap.Country[]{l_countries.get(0), l_countries.get(3), l_countries.get(4)}));
            l_player_second_obj.setConqueredCountries(Arrays.asList(new GameMap.Country[]{l_countries.get(1), l_countries.get(2), l_countries.get(5)}));

            d_source_country = l_player_first_obj.getConqueredCountries().get(0);
            d_destination_country = l_player_second_obj.getConqueredCountries().get(0);
            int l_armies = 4;

            AirliftOrder l_airlift_phase = new AirliftOrder(d_source_country, d_destination_country, l_armies);
            l_airlift_phase.execute(l_player_first_obj);

        } catch (Exception e) {
        }

        Assert.assertEquals(1, l_player_first_obj.getConqueredCountries().get(0).getArmyCount());
        Assert.assertEquals(6, l_player_second_obj.getConqueredCountries().get(0).getArmyCount());
        Assert.assertEquals(3, l_player_first_obj.getConqueredCountries().size());
        Assert.assertEquals(3, l_player_second_obj.getConqueredCountries().size());
        Assert.assertTrue(l_player_second_obj.getConqueredCountries().contains(d_destination_country));
        Assert.assertTrue(l_player_first_obj.getConqueredCountries().contains(d_source_country));
        Assert.assertFalse(l_player_second_obj.getConqueredCountries().contains(d_source_country));
        Assert.assertFalse(l_player_first_obj.getConqueredCountries().contains(d_destination_country));
        Assert.assertEquals(0, l_player_first_obj.getAvailableCards().size());
        Assert.assertEquals(0, l_player_second_obj.getAvailableCards().size());

    }

    /**
     * Test when the airlift results in no attacks.
     */
    @Test
    public void airLiftNonAttackTest() {
        LinkedHashMap<String, Player> l_player_list = d_current_game_info.getPlayerList();
        Player l_player_first_obj = l_player_list.get("playerfirst");
        Player l_player_second_obj = l_player_list.get("playersecond");
        List<GameMap.Country> l_countries = d_current_game_info.getGameMap().getCountryObjects();

        try {

            l_player_first_obj.setConqueredCountries(Arrays.asList(new GameMap.Country[]{l_countries.get(0), l_countries.get(3), l_countries.get(4)}));
            l_player_second_obj.setConqueredCountries(Arrays.asList(new GameMap.Country[]{l_countries.get(1), l_countries.get(2), l_countries.get(5)}));

            d_source_country = l_player_first_obj.getConqueredCountries().get(0);
            d_destination_country = l_player_first_obj.getConqueredCountries().get(1);
            int l_armies = 4;

            AirliftOrder l_airlift_phase = new AirliftOrder(d_source_country, d_destination_country, l_armies);
            l_airlift_phase.execute(l_player_first_obj);

        } catch (Exception e) {
        }

        Assert.assertEquals(1, l_player_first_obj.getConqueredCountries().get(0).getArmyCount());
        Assert.assertEquals(9, l_player_first_obj.getConqueredCountries().get(1).getArmyCount());
        Assert.assertEquals(3, l_player_first_obj.getConqueredCountries().size());
        Assert.assertEquals(3, l_player_second_obj.getConqueredCountries().size());
        Assert.assertEquals(0, l_player_first_obj.getAvailableCards().size());
        Assert.assertEquals(0, l_player_second_obj.getAvailableCards().size());

    }

}
