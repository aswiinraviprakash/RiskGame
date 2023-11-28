package gameplay;

import java.io.File;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import constants.GameConstants;

public class SaveLoadGameTest {
    private GameInformation d_current_game_info;
    private String d_save_path;
    

    @Before
    public void initializeTestData(){
        try {
            d_current_game_info = GameInformation.getInstance();
            d_save_path = GameConstants.D_SAVE_DIRECTORY;
        } catch (Exception e){
        }
    }

    @Test
    public void testSaveGame(){
        try{
            SaveGamePhase l_save_game = new SaveGamePhase(d_save_path);
            l_save_game.saveGame();
            File l_saveFile = new File(d_save_path);
            Assert.assertTrue(l_saveFile.exists());
        }catch (Exception e){}
    }

    @Test
    public void testLoadGame(){
        try{
            SaveGamePhase l_save_game = new SaveGamePhase(d_save_path);
            l_save_game.saveGame();
            LoadGamePhase l_GamePhase = new LoadGamePhase(d_save_path);
            GameInformation l_loadGame = l_GamePhase.getLoadedGameInformation();
            Assert.assertEquals(d_current_game_info, l_loadGame);
            
        }catch (Exception e){

        }
    }
}
