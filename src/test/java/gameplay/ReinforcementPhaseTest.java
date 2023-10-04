package gameplay;

import constants.GameConstants;
import mapparser.GameMap;
import org.junit.Test;
import org.junit.Before;
import org.junit.Assert;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class ReinforcementPhaseTest {

    private GameInformation d_current_game_info;

    @Before
    public void initializeTestData() {
        try {

            d_current_game_info = new GameInformation();

            File l_file_dir = new File("").getCanonicalFile();
            String l_gamemap_filename = l_file_dir.getParent() + GameConstants.D_MAP_TEST_DIRECTORY + "valid-testmap.map";

            File l_gamemap_file = new File(l_gamemap_filename);
            mapparser.GameMap l_gamemap_obj = new mapparser.GameMap(l_gamemap_filename);

            d_current_game_info.setCurrenGameMap(l_gamemap_obj);

            LinkedHashMap<String, Player> l_player_list = d_current_game_info.getPlayerList();
            Player l_player_obj = new Player("playerfirst");
            l_player_list.put("playerfirst", l_player_obj);

            l_player_obj = new Player("playersecond");
            l_player_list.put("playersecond", l_player_obj);
            d_current_game_info.setPlayerList(l_player_list);

        } catch (Exception e) {}
    }

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
            l_phase_obj.executePhase(d_current_game_info);

            //checking playerfirst armies assigned
            Assert.assertEquals(8, l_player_first_obj.getCurrentArmies());

            //checking playersecond armies assigned
            Assert.assertEquals(8, l_player_Second_obj.getCurrentArmies());

        } catch (Exception e) {}
    }

    @Test
    public void testArmiesAssigmentWithContientBonus() {
        try {
            LinkedHashMap<String, Player> l_player_list = d_current_game_info.getPlayerList();
            Player l_player_first_obj = l_player_list.get("playerfirst");
            Player l_player_Second_obj = l_player_list.get("playersecond");
            List<GameMap.Country> l_countries = d_current_game_info.getGameMap().getCountryObjects();

            l_player_first_obj.setConqueredCountries(Arrays.asList(new GameMap.Country[]{ l_countries.get(0), l_countries.get(3), l_countries.get(4),  l_countries.get(5)}));
            l_player_Second_obj.setConqueredCountries(Arrays.asList(new GameMap.Country[]{ l_countries.get(1), l_countries.get(2)}));

            ReinforcementPhase l_phase_obj = new ReinforcementPhase();
            l_phase_obj.executePhase(d_current_game_info);

            //checking playerfirst armies assigned with control bonus
            Assert.assertEquals(12, l_player_first_obj.getCurrentArmies());

            //checking playersecond armies assigned
            Assert.assertEquals(8, l_player_Second_obj.getCurrentArmies());

        } catch (Exception e) {}
    }

    @Test
    public void testArmiesAssigmentDefault() {
        try {
            LinkedHashMap<String, Player> l_player_list = d_current_game_info.getPlayerList();
            Player l_player_first_obj = l_player_list.get("playerfirst");
            Player l_player_Second_obj = l_player_list.get("playersecond");
            List<GameMap.Country> l_countries = d_current_game_info.getGameMap().getCountryObjects();

            ReinforcementPhase l_phase_obj = new ReinforcementPhase();
            l_phase_obj.executePhase(d_current_game_info);

            //checking playerfirst armies assigned without countries assigned
            Assert.assertEquals(8, l_player_first_obj.getCurrentArmies());

            //checking playersecond armies assigned without countries assigned
            Assert.assertEquals(8, l_player_Second_obj.getCurrentArmies());

        } catch (Exception e) {}
    }

}
