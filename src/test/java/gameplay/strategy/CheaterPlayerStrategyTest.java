package gameplay.strategy;

import constants.GameConstants;
import gameplay.GameInformation;
import gameplay.Player;
import gameplay.order.Order;
import mapparser.GameMap;
import mapparser.LoadMapPhase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class CheaterPlayerStrategyTest {

    /**
     * Contains the Current Game Information
     */
    private GameInformation d_current_game_info;

    /**
     * Initializes test data
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
            Player l_player_obj = new Player("cheater");
            l_player_obj.setPlayerStrategy(new CheaterPlayerStrategy());
            l_player_list.put("cheater", l_player_obj);

            l_player_obj = new Player("benevolent");
            l_player_obj.setPlayerStrategy(new BenevolentPlayerStrategy());
            l_player_list.put("benevolent", l_player_obj);
            d_current_game_info.setPlayerList(l_player_list);

        } catch (Exception e) {}
    }

    /**
     * Test for cheater conquer enemy countries.
     */
    @Test
    public void testConquerEnemyCountries() {

        try {
            LinkedHashMap<String, Player> l_player_list = d_current_game_info.getPlayerList();

            Player l_player_first_obj = l_player_list.get("cheater");
            Player l_player_Second_obj = l_player_list.get("benevolent");
            List<GameMap.Country> l_countries = d_current_game_info.getGameMap().getCountryObjects();

            l_countries.get(0).setPlayerName("cheater");
            l_countries.get(2).setPlayerName("cheater");
            l_countries.get(3).setPlayerName("cheater");
            l_player_first_obj.setConqueredCountries(Arrays.asList(new GameMap.Country[]{ l_countries.get(0), l_countries.get(3), l_countries.get(2)}));
            l_player_first_obj.setCurrentArmies(8);
            l_countries.get(1).setPlayerName("benevolent");
            l_countries.get(4).setPlayerName("benevolent");
            l_player_Second_obj.setConqueredCountries(Arrays.asList(new GameMap.Country[]{ l_countries.get(1), l_countries.get(4) }));
            l_player_first_obj.setCurrentArmies(8);

            List<Order> l_player_orders = l_player_first_obj.getPlayerStrategy().createOrders(l_player_first_obj);
            Assert.assertEquals(0, l_player_orders.size());
            Assert.assertEquals(0, l_player_Second_obj.getConqueredCountries().size());
            Assert.assertEquals(6, l_player_first_obj.getConqueredCountries().size());

        } catch (Exception e) {}

    }

}
