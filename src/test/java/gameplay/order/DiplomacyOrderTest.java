package gameplay.order;

import java.util.LinkedHashMap;

import gameplay.GameInformation;
import gameplay.Player;
import gameplay.order.AirliftOrder;
import gameplay.order.DiplomacyOrder;
import mapparser.GameMap;
import mapparser.LoadMapPhase;
import org.junit.Before;
import constants.GameConstants;
import java.util.Arrays;
import java.util.List;
import mapparser.GameMap.Country;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for Diplomacy Order
 */
public class DiplomacyOrderTest {

    /**
     * Contains Current Game Information
     */
    private GameInformation d_current_game_info;

    /**
     * Contains the Destination player
     */
    private Player d_target_player;

    /**
     * Initializing Test Data
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
     * Test to check if Diplomacy is Successful
     */
    @Test
    public void diplomacyTest() {

        LinkedHashMap<String, Player> l_player_list = d_current_game_info.getPlayerList();
        Player l_player_first_obj = l_player_list.get("playerfirst");
        Player l_player_second_obj = l_player_list.get("playersecond");
        List<GameMap.Country> l_countries = d_current_game_info.getGameMap().getCountryObjects();

        l_countries.get(1).setArmyCount(10);

        try {

            l_player_first_obj.setConqueredCountries(Arrays.asList(new GameMap.Country[]{l_countries.get(0), l_countries.get(3), l_countries.get(4)}));
            l_player_second_obj.setConqueredCountries(Arrays.asList(new GameMap.Country[]{l_countries.get(1), l_countries.get(2), l_countries.get(5)}));

            Country l_source_country = l_player_second_obj.getConqueredCountries().get(0);
            Country l_destination_country = l_player_first_obj.getConqueredCountries().get(0);
            int l_armies = 6;

            AirliftOrder l_airlift_phase = new AirliftOrder(l_source_country, l_destination_country, l_armies);
            l_player_second_obj.d_current_order = l_airlift_phase;
            l_player_second_obj.issue_order();
            
            DiplomacyOrder l_diplomacy_phase = new DiplomacyOrder(l_player_second_obj);
            l_player_first_obj.d_current_order = l_diplomacy_phase;
            l_player_first_obj.issue_order();
            
            l_diplomacy_phase.execute(l_player_first_obj);
            
           Assert.assertEquals(0,l_player_second_obj.getOrders().size());
            
        } catch (Exception e) {

        }

    }

}
