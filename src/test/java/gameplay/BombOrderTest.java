package gameplay;

import mapparser.GameMap;
import mapparser.LoadMapPhase;
import constants.GameConstants;
import org.junit.Test;
import org.junit.Before;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Assert;

/**
 * Test class for bomb order.
 */
public class BombOrderTest {

    private GameInformation d_current_game_info;
    private GameMap.Country d_destination_country;

    /**
     * Initialising test data.
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
     * Test for bombing the enemy country.
     */
    @Test
    public void BombingEnemyTerritoryTest() {
        try {
            LinkedHashMap<String, Player> l_player_list = d_current_game_info.getPlayerList();
            Player l_player_first_obj = l_player_list.get("playerfirst");
            Player l_player_second_obj = l_player_list.get("playersecond");
            List<GameMap.Country> l_countries = d_current_game_info.getGameMap().getCountryObjects();

            l_player_first_obj.setConqueredCountries(Arrays.asList(new GameMap.Country[]{l_countries.get(0), l_countries.get(3), l_countries.get(4)}));
            l_player_second_obj.setConqueredCountries(Arrays.asList(new GameMap.Country[]{l_countries.get(1), l_countries.get(2), l_countries.get(5)}));

            d_destination_country = l_player_second_obj.getConqueredCountries().get(0);

            BombOrder l_bomb_phase = new BombOrder(d_destination_country);
            l_bomb_phase.execute(l_player_first_obj);

            Assert.assertEquals(2, l_player_second_obj.getConqueredCountries().get(0).getArmyCount());

        } catch (Exception e) {
        }
    }

    /**
     * Test for bombing one self.
     */
    public void BombingOwnCountryTest(){
        try {
            LinkedHashMap<String, Player> l_player_list = d_current_game_info.getPlayerList();
            Player l_player_first_obj = l_player_list.get("playerfirst");
            Player l_player_second_obj = l_player_list.get("playersecond");
            List<GameMap.Country> l_countries = d_current_game_info.getGameMap().getCountryObjects();

            l_player_first_obj.setConqueredCountries(Arrays.asList(new GameMap.Country[]{l_countries.get(0), l_countries.get(3), l_countries.get(4)}));
            l_player_second_obj.setConqueredCountries(Arrays.asList(new GameMap.Country[]{l_countries.get(1), l_countries.get(2), l_countries.get(5)}));

            d_destination_country = l_player_first_obj.getConqueredCountries().get(0);

            BombOrder l_bomb_phase = new BombOrder(d_destination_country);
            l_bomb_phase.execute(l_player_first_obj);

            Assert.assertEquals(5, l_player_first_obj.getConqueredCountries().get(0).getArmyCount());

        } catch (Exception e) {
        }
    }

}
