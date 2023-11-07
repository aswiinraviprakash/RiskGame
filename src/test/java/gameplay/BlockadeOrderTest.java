package gameplay;

import mapparser.GameMap;
import mapparser.LoadMapPhase;
import constants.GameConstants;
import org.junit.Test;
import org.junit.Before;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;


public class BlockadeOrderTest {
    private GameInformation d_current_game_info;
    private GameMap.Country d_destination_country;

    @Before
    public void InitializeTestData(){
        try{

            d_current_game_info = GameInformation.getInstance();

            String l_gamemap_filename = "valid-testmap.map";
            LoadMapPhase l_loadmap_phase = new LoadMapPhase(l_gamemap_filename, false);
            l_loadmap_phase.setMapDirectory(GameConstants.D_MAP_TEST_DIRECTORY);
            l_loadmap_phase.executePhase();
            mapparser.GameMap l_gamemap_obj = l_loadmap_phase.getLoadedMap();
            
            d_current_game_info.setCurrenGameMap(l_gamemap_obj);

            HashMap<String, Player> l_player_list = d_current_game_info.getPlayerList();
            Player l_player_obj = new Player("playerfirst");
            l_player_list.put("playerfirst", l_player_obj);
            
            List<GameMap.Country> l_countries = d_current_game_info.getGameMap().getCountryObjects();

            l_countries.get(0).setPlayerName("playerfirst");
            l_countries.get(3).setPlayerName("playerfirst");
            l_countries.get(4).setPlayerName("playerfirst");

        } catch (Exception e) {}
    }

    @Test
    public void blockadeTest(){
        try {
            HashMap<String, Player> l_player_list = d_current_game_info.getPlayerList();
            Player l_player_first_obj = l_player_list.get("playerfirst");
            List<GameMap.Country> l_countries = d_current_game_info.getGameMap().getCountryObjects();

            l_player_first_obj.setConqueredCountries(Arrays.asList(new GameMap.Country[]{ l_countries.get(0), l_countries.get(3), l_countries.get(4)}));
            
            d_destination_country = l_player_first_obj.getConqueredCountries().get(0);

            BlockadeOrder l_blockade_phase = new BlockadeOrder(d_destination_country);
            l_blockade_phase.execute(l_player_first_obj);

            Assert.assertEquals(15,d_destination_country.getArmyCount());

        } catch (Exception e) {}
    }

    @Test
    public void ownershipChangeTest(){
        try{
            HashMap<String, Player> l_player_list = d_current_game_info.getPlayerList();
            Player l_player_first_obj = l_player_list.get("playerfirst");
            List<GameMap.Country> l_countries = d_current_game_info.getGameMap().getCountryObjects();

            l_player_first_obj.setConqueredCountries(Arrays.asList(new GameMap.Country[]{ l_countries.get(0), l_countries.get(3), l_countries.get(4)}));
            
            d_destination_country = l_player_first_obj.getConqueredCountries().get(0);

            BlockadeOrder l_blockade_phase = new BlockadeOrder(d_destination_country);
            l_blockade_phase.execute(l_player_first_obj);

            Assert.assertEquals(2, l_player_first_obj.getConqueredCountries().size());
        } catch (Exception e) {}
    }

}
