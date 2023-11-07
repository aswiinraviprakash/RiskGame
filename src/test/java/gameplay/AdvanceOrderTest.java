package gameplay;

import constants.GameConstants;
import junit.framework.TestCase;
import mapparser.GameMap;
import mapparser.LoadMapPhase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class AdvanceOrderTest  {



    private GameMap.Country d_destination_country;
    private GameMap.Country d_source_country;
    private GameInformation d_current_game_info;

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

        } catch (Exception e) {
        }
    }

    /**
     *  Test to verify that the advance order does not execute when the number of armies to advance is greater than the number of armies in the source country.
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

        } catch (Exception e) {
        }
        d_source_country = l_player_first_obj.getConqueredCountries().get(0);
        d_destination_country = l_player_second_obj.getConqueredCountries().get(1);
        Player currentPlayer = new Player("Player1");
        Player destinationPlayer = new Player("Player 2");
        //Player destinationPlayer = new Player("Player2");
        int armies = 100;
        AdvanceOrder advanceOrder = new AdvanceOrder(d_source_country, d_destination_country, armies);
        //Advance order is executed
        advanceOrder.attackDestinationCountry(currentPlayer, destinationPlayer);
        //Checks the number of armies at the source country
        Assert.assertEquals(20,d_source_country.getArmyCount());
        //Checks the number of armies in the destination country
        Assert.assertEquals(5,d_destination_country.getArmyCount());
        // Checks if the ownership is still the same
        Assert.assertFalse(d_source_country.getPlayerName(), Boolean.parseBoolean(d_destination_country.getPlayerName()));
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
            l_countries.get(1).setArmyCount(5);
        } catch (Exception e) {
        }
        Player currentPlayer = new Player("Player1");
        Player destinationPlayer = new Player("Player 2");
        d_source_country = l_player_first_obj.getConqueredCountries().get(0);
        d_destination_country = l_player_second_obj.getConqueredCountries().get(1);

        int armies = 10;

        AdvanceOrder advanceOrder = new AdvanceOrder(d_source_country, d_destination_country, armies);
        Assert.assertEquals(d_source_country.getPlayerName(),d_destination_country.getPlayerName());
        //Advance order is executed
        advanceOrder.attackDestinationCountry(currentPlayer, destinationPlayer);
        // Advance order on opponent territory is executed
        Assert.assertEquals(10,d_source_country.getArmyCount());
        //Checks army in the destination country
        Assert.assertEquals(5,d_destination_country.getArmyCount());
        //Checks if ownership is changed
        Assert.assertEquals("Player1",d_destination_country.getPlayerName());
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
        }
        catch (Exception e) {
        }
        d_source_country = l_player_first_obj.getConqueredCountries().get(0);
        d_destination_country = l_player_first_obj.getConqueredCountries().get(1);
        Player currentPlayer = new Player("Player1");
        Player destinationPlayer = currentPlayer;
        int armies = 6;
        AdvanceOrder advanceOrder = new AdvanceOrder(d_source_country, d_destination_country, armies);
        // Advance order on friendly territory is executed
        advanceOrder.movesArmiesToDestinationCountry();
        //Checks the army count in source country
        Assert.assertEquals(4,d_source_country.getArmyCount());
        //Checks for ownership
        Assert.assertEquals(currentPlayer.getPlayerName(),destinationPlayer.getPlayerName());
        //Checks army in the destination country
        Assert.assertEquals(16,d_destination_country.getArmyCount());
    }

    /**
     * Test to check Advance order an  Unconqured Territory
     */
    @Test
    public void attackOnUnconqueredTerritory() {

        GameMap gameMap = new GameMap("resources/valid-testmap.map");
        GameMap.Country sourceCountry = gameMap.new Country(1, "Source Country", false, 15, "Continent 1", "Player 1");
        GameMap.Country destinationCountry = gameMap.new Country(2, "Destination Country", false, 3, "Continent 1", null);
        // To check player name in destination country before the advance order is executed
        Assert.assertEquals(null,destinationCountry.getPlayerName());
        AdvanceOrder advanceOrder = new AdvanceOrder(sourceCountry, destinationCountry, 5);
        Player currentPlayer = new Player("Player1");
        Player destinationPlayer = null;
        //Advance order is executed
        advanceOrder.attackDestinationCountry(currentPlayer,destinationPlayer);
        //Checks the army count in source country
        Assert.assertEquals(10, sourceCountry.getArmyCount());
        //Checks the army count in destination country
        Assert.assertEquals(2, destinationCountry.getArmyCount());
        //Checks if the unconquered territory is captured by player1
        Assert.assertEquals("Player1",destinationCountry.getPlayerName());
    }

}