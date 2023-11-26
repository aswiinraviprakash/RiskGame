package gameplay;

import common.Phase;
import constants.GameConstants;
import constants.GameMessageConstants;
import gameutils.GameException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author USER
 */
public class SaveGamePhase extends Phase {

    String d_file_path;
    
    public GameInformation d_current_game_info;

    public SaveGamePhase(String p_file_path) {
        this.d_file_path = p_file_path;
    }

    @Override
    public Phase nextPhase() throws Exception {
        return new EndGamePhase();
    }

    @Override
    public void executePhase() throws Exception {
        this.saveGame();

    }

    public void saveGame() throws Exception {
        
        d_current_game_info = GameInformation.getInstance();
        try {

            checkDirectory();
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

    private void checkDirectory() {
        String l_file_path = GameConstants.D_SAVE_DIRECTORY;
        File l_file_directory = new File(l_file_path);
        if (!l_file_directory.exists() || !l_file_directory.isDirectory()) {
            l_file_directory.mkdirs();
        }
    }
}