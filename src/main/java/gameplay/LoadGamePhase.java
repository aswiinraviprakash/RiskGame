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
public class LoadGamePhase extends Phase{
    String d_file_path;
    
    public GameInformation d_current_game_info;
    
    public LoadGamePhase(String p_file_path){
        this.d_file_path = p_file_path;
    }
    
    @Override
    public Phase nextPhase() throws Exception{
        return new GameStartUpPhase();
    }
    
    @Override
     public void executePhase() throws Exception{
         this.loadGame();
     }
     
     public GameInformation loadGame() throws Exception {
        try{
            
            String l_load_file_path = GameConstants.D_SAVE_DIRECTORY + File.separator + this.d_file_path + ".ser";
            FileInputStream l_saveFile =  new FileInputStream(l_load_file_path);
            ObjectInputStream save = new ObjectInputStream(l_saveFile);
            d_current_game_info = (GameInformation) save.readObject();
            save.close();
            System.out.println(GameMessageConstants.D_LOAD_GAME_SUCCESS);
            d_current_game_info.setCurrentPhase(this.nextPhase());
            
        } catch (Exception e){
            throw new GameException(GameMessageConstants.D_LOAD_GAME_ERROR);
        }
        return d_current_game_info;
    }
     
     private void checkDirectory() {
        String l_file_path = GameConstants.D_SAVE_DIRECTORY;
        File l_file_directory = new File(l_file_path);
        if (!l_file_directory.exists() || !l_file_directory.isDirectory()) {
            l_file_directory.mkdirs();
        }
    }
    
}
