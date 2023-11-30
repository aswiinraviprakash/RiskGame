package gameplay.strategy;

import constants.GameConstants;
import gameplay.GameInformation;
import gameplay.Player;
import gameplay.order.AdvanceOrder;
import gameplay.order.DeployOrder;
import gameplay.order.Order;
import mapparser.GameMap;
import mapparser.LoadMapPhase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class AggressivePlayerStrategyTest {

    /**
     * Contains the Current Game Information
     */
    private GameInformation d_current_game_info;

    /**
     * Intializes test data.
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
            Player l_player_obj = new Player("aggressive");
            l_player_obj.setPlayerStrategy(new AggressivePlayerStrategy());
            l_player_list.put("aggressive", l_player_obj);

            l_player_obj = new Player("benevolent");
            l_player_obj.setPlayerStrategy(new BenevolentPlayerStrategy());
            l_player_list.put("benevolent", l_player_obj);
            d_current_game_info.setPlayerList(l_player_list);

        } catch (Exception e) {}
    }

    /**
     * Test for deploy on strongest country.
     */
    @Test
    public void testDeployOnStrongestCountry() {

        try {
            LinkedHashMap<String, Player> l_player_list = d_current_game_info.getPlayerList();

            Player l_player_first_obj = l_player_list.get("aggressive");
            Player l_player_Second_obj = l_player_list.get("benevolent");
            List<GameMap.Country> l_countries = d_current_game_info.getGameMap().getCountryObjects();

            l_countries.get(0).setPlayerName("aggressive");
            l_countries.get(3).setPlayerName("aggressive");
            l_player_first_obj.setConqueredCountries(Arrays.asList(new GameMap.Country[]{ l_countries.get(0), l_countries.get(3) }));
            l_player_first_obj.setCurrentArmies(8);
            l_countries.get(1).setPlayerName("benevolent");
            l_countries.get(4).setPlayerName("benevolent");
            l_player_Second_obj.setConqueredCountries(Arrays.asList(new GameMap.Country[]{ l_countries.get(1), l_countries.get(4) }));
            l_player_first_obj.setCurrentArmies(8);

            List<Order> l_player_orders = l_player_first_obj.getPlayerStrategy().createOrders(l_player_first_obj);
            Assert.assertEquals(true, l_player_orders.size() > 0);
            Assert.assertEquals(true, l_player_orders.get(0) instanceof DeployOrder);
            Assert.assertEquals("Country-First", ((DeployOrder) l_player_orders.get(0)).getCountryName());

        } catch (Exception e) {}

    }

    /**
     * Test for attack on strongest country.
     */
    @Test
    public void testAttackOnStrongestCountry() {
        try {
            LinkedHashMap<String, Player> l_player_list = d_current_game_info.getPlayerList();

            Player l_player_first_obj = l_player_list.get("aggressive");
            Player l_player_Second_obj = l_player_list.get("benevolent");
            List<GameMap.Country> l_countries = d_current_game_info.getGameMap().getCountryObjects();

            l_countries.get(0).setPlayerName("aggressive");
            l_countries.get(3).setPlayerName("aggressive");
            l_player_first_obj.setConqueredCountries(Arrays.asList(new GameMap.Country[]{ l_countries.get(0), l_countries.get(3) }));
            l_player_first_obj.setCurrentArmies(8);
            l_countries.get(1).setArmyCount(4);
            l_countries.get(1).setPlayerName("benevolent");
            l_countries.get(4).setPlayerName("benevolent");
            l_player_Second_obj.setConqueredCountries(Arrays.asList(new GameMap.Country[]{ l_countries.get(1), l_countries.get(4) }));
            l_player_first_obj.setCurrentArmies(8);

            List<Order> l_player_orders = l_player_first_obj.getPlayerStrategy().createOrders(l_player_first_obj);
            Assert.assertEquals(true, l_player_orders.size() > 0);
            Assert.assertEquals(true, l_player_orders.get(1) instanceof AdvanceOrder);
            Assert.assertEquals("Country-Second", ((AdvanceOrder) l_player_orders.get(1)).getDestinationCountry().getCountryName());

        } catch (Exception e) {}
    }

}
