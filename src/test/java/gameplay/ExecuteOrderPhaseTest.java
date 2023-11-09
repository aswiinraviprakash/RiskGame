package gameplay;

import constants.GameConstants;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import mapparser.GameMap;
import mapparser.LoadMapPhase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ExecuteOrderPhaseTest {

    /**
     * Contains Destination Country
     */
    private GameInformation d_current_game_info;

    /**
     * Initializing Test Data
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
     * Test to Check if Game Ending is Successful
     */
    @Test
    public void endGameTest() {
        
        LinkedHashMap<String, Player> l_player_list = d_current_game_info.getPlayerList();
        Player l_player_first_obj = l_player_list.get("playerfirst");
        Player l_player_second_obj = l_player_list.get("playersecond");
        List<GameMap.Country> l_countries = d_current_game_info.getGameMap().getCountryObjects();

        l_player_first_obj.setConqueredCountries(Arrays.asList(new GameMap.Country[]{l_countries.get(0), l_countries.get(3), l_countries.get(4),l_countries.get(1), l_countries.get(2), l_countries.get(5)}));
        l_player_second_obj.setConqueredCountries(Arrays.asList());
        
        try{
            ExecuteOrderPhase l_phase_obj = new ExecuteOrderPhase();
            l_phase_obj.executePhase();
        }catch(Exception e){
            
        }
        
        Assert.assertTrue(d_current_game_info.getCurrentPhase() instanceof EndGamePhase);


    }
}
