package gameplay;

import constants.GameMessageConstants;
import gameplay.strategy.AggressivePlayerStrategy;
import gameplay.strategy.BenevolentPlayerStrategy;
import gameplay.strategy.CheaterPlayerStrategy;
import gameplay.strategy.RandomPlayerStratergy;
import gameutils.GameException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.LinkedHashMap;

/**
 * Test class for Game startup up phase
 */
public class GameStartUpPhaseTest {

    /**
     * Game information object
     */
    private GameInformation d_current_game_info;

    /**
     * Initializes Test data
     */
    @Before
    public void initializeTestData() {
        d_current_game_info = GameInformation.getInstance();
    }

    /**
     * Test to add or remove player.
     */
    @Test
    public void testAddorRemovePlayer() {
        try {
            GameStartUpPhase l_phase_obj = new GameStartUpPhase();
            l_phase_obj.d_current_game_info = this.d_current_game_info;

            // checking player addition
            l_phase_obj.validateAndExecuteCommands("gameplayer -add playerfirst -add playersecond");
            LinkedHashMap<String, Player> l_player_list = d_current_game_info.getPlayerList();
            Assert.assertEquals(2, l_player_list.values().size());
            Assert.assertEquals(true, l_player_list.containsKey("playerfirst"));
            Assert.assertEquals(true, l_player_list.containsKey("playersecond"));

            // checking player removal
            l_phase_obj.validateAndExecuteCommands("gameplayer -remove playersecond");
            l_player_list = d_current_game_info.getPlayerList();
            Assert.assertEquals(1, l_player_list.values().size());
            Assert.assertEquals(false, l_player_list.containsKey("playersecond"));

        } catch (Exception e) {}
    }

    /**
     * Test to add or remove players, but invalid test.
     */
    @Test
    public void testAddorRemovePlayerInvalid() {
        GameStartUpPhase l_phase_obj = new GameStartUpPhase();
        l_phase_obj.d_current_game_info = this.d_current_game_info;

        // invalid command case
        try {
            l_phase_obj.validateAndExecuteCommands("gameplay -add playerfirst -add playersecond");
        } catch (GameException e) {
            Assert.assertEquals(GameMessageConstants.D_COMMAND_INVALID, e.getMessage());
        } catch (Exception e){ }

        // removing player does not exists
        try {
            l_phase_obj.validateAndExecuteCommands("gameplayer -remove playerfirst");
        } catch (GameException e) {
            Assert.assertEquals(GameMessageConstants.D_PLAYER_NOTFOUND, e.getMessage());
        } catch (Exception e) { }
    }

    /**
     * Test for checking add startegies valid.
     */
    @Test
    public void testAddStrategiesValid() {
        GameStartUpPhase l_phase_obj = new GameStartUpPhase();
        l_phase_obj.d_current_game_info = this.d_current_game_info;

        try {
            l_phase_obj.validateAndAddStrategy("aggressive");
            Assert.assertEquals(true, d_current_game_info.getPlayerList().containsKey("aggressive"));
            Assert.assertEquals(true, d_current_game_info.getPlayerList().get("aggressive").getPlayerStrategy() instanceof AggressivePlayerStrategy);
            l_phase_obj.validateAndAddStrategy("random");
            Assert.assertEquals(true, d_current_game_info.getPlayerList().containsKey("random"));
            Assert.assertEquals(true, d_current_game_info.getPlayerList().get("random").getPlayerStrategy() instanceof RandomPlayerStratergy);
            l_phase_obj.validateAndAddStrategy("benevolent");
            Assert.assertEquals(true, d_current_game_info.getPlayerList().containsKey("benevolent"));
            Assert.assertEquals(true, d_current_game_info.getPlayerList().get("benevolent").getPlayerStrategy() instanceof BenevolentPlayerStrategy);
            l_phase_obj.validateAndAddStrategy("cheater");
            Assert.assertEquals(true, d_current_game_info.getPlayerList().containsKey("cheater"));
            Assert.assertEquals(true, d_current_game_info.getPlayerList().get("cheater").getPlayerStrategy() instanceof CheaterPlayerStrategy);

        } catch (Exception e){}

    }

    /**
     * Test for checking add startegies invalid.
     */
    @Test
    public void testAddStrategiesInValid() {
        GameStartUpPhase l_phase_obj = new GameStartUpPhase();
        l_phase_obj.d_current_game_info = this.d_current_game_info;

        try {
            l_phase_obj.validateAndAddStrategy("aggressivetest");

        } catch (Exception e){
            Assert.assertEquals(GameMessageConstants.D_STRATEGIES_INVALID, e.getMessage());
        }

    }


}
