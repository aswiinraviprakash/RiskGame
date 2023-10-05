package gameplay;

import constants.GameConstants;
import constants.GameMessageConstants;
import gameutils.GameException;
import mapparser.GameMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;

public class GameStartUpPhaseTest {

    /**
     * Sets current game info.
     */
    private GameInformation d_current_game_info;

    /**
     * Initializes Test data
     */
    @Before
    public void initializeTestData() {
        d_current_game_info = new GameInformation();
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
}
