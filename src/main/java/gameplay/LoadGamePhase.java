package gameplay;

import common.Phase;
import constants.GameConstants;
import constants.GameMessageConstants;
import gameutils.GameException;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

/**
 *
 * @author USER
 */
public class LoadGamePhase extends Phase {

    private String d_file_path;
    
    private GameInformation d_current_game_info;
    
    public LoadGamePhase(String p_file_path) {
        this.d_file_path = p_file_path;
    }

    public GameInformation getLoadedGameInformation() {
        return this.d_current_game_info;
    }
    
    @Override
    public Phase nextPhase() throws Exception {
        return new GameStartUpPhase();
    }
    
    @Override
     public void executePhase() throws Exception {
         this.loadGame();
     }

     public void loadGame() throws Exception {
         try {

             File l_file_dir = new File("").getCanonicalFile();
             String l_load_file_path = l_file_dir.getParent() + GameConstants.D_SAVE_DIRECTORY + this.d_file_path + ".ser";

             File l_file = new File(l_load_file_path);
             if (!l_file.exists()) throw new GameException(GameMessageConstants.D_GAME_LOAD_FAILED);

             FileInputStream l_save_file = new FileInputStream(l_file);
             ObjectInputStream save = new ObjectInputStream(l_save_file);
             d_current_game_info = (GameInformation) save.readObject();
             GameInformation.loadGameInfoInstance(d_current_game_info);
             GameInformation.getInstance().setGameState(GameConstants.GameState.D_LOAD_GAME);
             save.close();
             System.out.println(GameMessageConstants.D_LOAD_GAME_SUCCESS);

         } catch (Exception e) {
             throw new GameException(GameMessageConstants.D_GAME_LOAD_FAILED);
         }
     }
}