package gameplay;

import common.Phase;
import constants.GameConstants;
import constants.GameMessageConstants;
import gameutils.GameException;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

/**
 * Class for game phase
 */
public class LoadGamePhase extends Phase {

    /**
     * Ccntains file path
     */
    private String d_file_path;

    /**
     * Contains game information
     */
    private GameInformation d_current_game_info;

    /**
     * Methid for game phase
     * @param p_file_path string of file path
     */
    public LoadGamePhase(String p_file_path) {
        this.d_file_path = p_file_path;
    }

    /**
     * Getter for loaded game information
     * @return current game information
     */
    public GameInformation getLoadedGameInformation() {
        return this.d_current_game_info;
    }

    /**
     * Method for nextphase
     * @return gamestartupphase function
     * @throws Exception if any exceptions are caught
     */
    @Override
    public Phase nextPhase() throws Exception {
        return new GameStartUpPhase();
    }

    /**
     * method for execute phase
     * @throws Exception if any exceptions are caught
     */
    @Override
     public void executePhase() throws Exception {
         this.loadGame();
     }

    /**
     * Method for loadgame
     * @throws Exception if any exceptions are caught
     */
     public void loadGame() throws Exception {
         try {

             String l_load_file_path = GameConstants.D_SAVE_DIRECTORY + File.separator + this.d_file_path + ".ser";
             FileInputStream l_saveFile =  new FileInputStream(l_load_file_path);
             ObjectInputStream save = new ObjectInputStream(l_saveFile);
             d_current_game_info = (GameInformation) save.readObject();
             save.close();
             System.out.println(GameMessageConstants.D_LOAD_GAME_SUCCESS);

         } catch (Exception e) {
             throw new GameException(GameMessageConstants.D_LOAD_GAME_ERROR);
         }
     }
}