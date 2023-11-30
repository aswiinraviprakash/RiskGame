package gameplay;

import java.io.File;

import constants.GameConstants;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;

/**
 * Test class for save game
 */
public class SaveGamePhaseTest {
    /**
     * initializing game information object
     */
    private GameInformation d_current_game_info;

    /**
     * initializes test data
     */
    @Before
    public void initializeTestData() {
        d_current_game_info = GameInformation.getInstance();
    }

    /**
     * Test for testing save game
     */
    @Test
    public void testSaveGame() {
        try {
            SaveGamePhase l_save_game = new SaveGamePhase("gametest");
            l_save_game.setSaveDirectory(GameConstants.D_SAVE_TEST_DIRECTORY);
            l_save_game.saveGame();

            File l_file_dir = new File("").getCanonicalFile();
            String l_save_file_path = l_file_dir.getParent() + GameConstants.D_SAVE_TEST_DIRECTORY + "gametest" + ".ser";
            File l_save_file = new File(l_save_file_path);
            Assert.assertTrue(l_save_file.exists());
        } catch (Exception e) { }
    }

}
