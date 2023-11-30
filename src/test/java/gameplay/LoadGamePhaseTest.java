package gameplay;

import constants.GameConstants;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for load game
 */
public class LoadGamePhaseTest {

    /**
     * Test for testing load game
     */
    @Test
    public void testLoadGame() {
        try {
            LoadGamePhase l_save_game = new LoadGamePhase("gametest");
            l_save_game.setSaveDirectory(GameConstants.D_SAVE_TEST_DIRECTORY);
            l_save_game.loadGame();
            GameInformation l_game_information = l_save_game.getLoadedGameInformation();
            Assert.assertNotEquals(null, l_game_information);
        } catch (Exception e) { }
    }
}
