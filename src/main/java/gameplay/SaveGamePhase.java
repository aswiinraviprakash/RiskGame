package gameplay;

import common.Phase;
import constants.GameConstants;
import constants.GameMessageConstants;
import gameutils.GameCommonUtils;
import gameutils.GameException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/**
 * Class for Save game phase
 */
public class SaveGamePhase extends Phase {

    /**
     * Contains file path
     */
    private String d_file_path;

    /**
     * Contains file directory
     */
    private String d_save_directory;

    /**
     * Contains current game information
     */
    private GameInformation d_current_game_info;

    /**
     * Method to save game phase
     * @param p_file_path The Save file Path
     */
    public SaveGamePhase(String p_file_path) {
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
     * Method for next phase
     * @return end game phase method
     * @throws Exception of any exception caught in the code block
     */
    @Override
    public Phase nextPhase() throws Exception {
        return new EndGamePhase();
    }

    /**
     * Method to execute phase
     * @throws Exception of any exception caught in the code block
     */
    @Override
    public void executePhase() throws Exception {
        this.saveGame();
    }

    /**
     * Method to save game
     * @throws Exception of any exception caught in the code block
     */
    public void saveGame() throws Exception {
        d_current_game_info = GameInformation.getInstance();

        try {
            GameCommonUtils.checkAndCreateDirectory(d_save_directory);

            File l_file_dir = new File("").getCanonicalFile();
            String l_save_file_path = l_file_dir.getParent() + d_save_directory + this.d_file_path + ".ser";
            File l_file_obj = new File(l_save_file_path);

            ObjectOutputStream l_save = new ObjectOutputStream(new FileOutputStream(l_file_obj));
            l_save.writeObject(d_current_game_info);
            l_save.close();
            System.out.println(GameMessageConstants.D_SAVE_GAME_SUCCESS);

            d_current_game_info.setCurrentPhase(this.nextPhase());
        } catch (Exception e) {
            throw new GameException(GameMessageConstants.D_SAVE_GAME_ERROR);
        }
    }
}