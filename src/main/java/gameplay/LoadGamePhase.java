package gameplay;

import common.Phase;
import constants.GameConstants;
import constants.GameMessageConstants;
import gameutils.GameException;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

/**
 * Class for load game phase
 */
public class LoadGamePhase extends Phase {

    /**
     * String file path
     */
    private String d_file_path;

    /**
     * Contains file directory
     */
    private String d_save_directory;

    /**
     * Contains current game info
     */
    private GameInformation d_current_game_info;

    /**
     * Method for load game phase
     * @param p_file_path The Loadgame file path
     */
    public LoadGamePhase(String p_file_path) {
        this.d_file_path = p_file_path;
        this.d_save_directory = GameConstants.D_SAVE_DIRECTORY;
    }

    /**
     * method for setting directory
     * @param p_save_directory directory path
     */
    public void setSaveDirectory(String p_save_directory) {
        this.d_save_directory = p_save_directory;
    }

    /**
     * Getter for loaded game information
     * @return current game information
     */
    public GameInformation getLoadedGameInformation() {
        return this.d_current_game_info;
    }

    /**
     * Method for next phase
     * @return game startup phase
     * @throws Exception Throws exception if the nextPhase() call is unsuccessful.
     */
    @Override
    public Phase nextPhase() throws Exception {
        return new GameStartUpPhase();
    }

    /**
     * Method for execute phase
     * @throws Exception if any exception are caught in the code block
     */
    @Override
     public void executePhase() throws Exception {
         this.loadGame();
     }

    /**
     * Method to load game
     * @throws Exception if any exception are caught in the code block
     */
    public void loadGame() throws Exception {
         try {

             File l_file_dir = new File("").getCanonicalFile();
             String l_load_file_path = l_file_dir.getParent() + d_save_directory + this.d_file_path + ".ser";

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