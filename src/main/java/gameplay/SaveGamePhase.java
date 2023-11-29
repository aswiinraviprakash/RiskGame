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
 *
 * @author USER
 */
public class SaveGamePhase extends Phase {

    /**
     * Contains file path
     */
    private String d_file_path;

    /**
     * Contains game information
     */
    private GameInformation d_current_game_info;

    /**
     * Method for save game phase
     * @param p_file_path
     */
    public SaveGamePhase(String p_file_path) {
        this.d_file_path = p_file_path;
    }

    /**
     * method for next phase
     * @return new eng game phase method
     * @throws Exception if any exceptions are caught
     */
    @Override
    public Phase nextPhase() throws Exception {
        return new EndGamePhase();
    }

    /**
     * Execute phase
     * @throws Exception if any exceptions are caught
     */
    @Override
    public void executePhase() throws Exception {
        this.saveGame();
    }

    /**
     *  Save game method
     * @throws Exception if any exceptions are caught
     */
    public void saveGame() throws Exception {
        d_current_game_info = GameInformation.getInstance();

        try {
            GameCommonUtils.checkAndCreateDirectory(GameConstants.D_SAVE_DIRECTORY);

            String l_save_file_path = GameConstants.D_SAVE_DIRECTORY + File.separator + this.d_file_path + ".ser";
            ObjectOutputStream l_save = new ObjectOutputStream(new FileOutputStream(l_save_file_path));
            l_save.writeObject(d_current_game_info);
            l_save.close();
            System.out.println(GameMessageConstants.D_SAVE_GAME_SUCCESS);

            d_current_game_info.setCurrentPhase(this.nextPhase());
        } catch (Exception e) {
            throw new GameException(GameMessageConstants.D_SAVE_GAME_ERROR);
        }
    }
}