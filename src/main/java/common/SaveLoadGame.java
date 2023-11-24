package common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import constants.GameConstants;
import gameplay.GameInformation;
import gameutils.GameException;

public class SaveLoadGame {

    public void saveGame(String p_file_name) throws Exception {
        try{
            
            checkDirectory();
            String l_save_file_path = GameConstants.D_SAVE_DIRECTORY + File.separator + p_file_name + ".ser";
            ObjectOutputStream l_save = new ObjectOutputStream(new FileOutputStream(l_save_file_path));
            l_save.writeObject(GameInformation.getInstance());
            l_save.close();
            System.out.println("Game saved successfully!!");

        } catch (Exception e){
            throw new GameException("Error saving the game");
        }

    }

    public GameInformation loadGame(String p_file_name) throws Exception {
        GameInformation l_game_info;
        try{
            
            String l_load_file_path = GameConstants.D_SAVE_DIRECTORY + File.separator + p_file_name + ".ser";
            FileInputStream l_saveFile =  new FileInputStream(l_load_file_path);
            ObjectInputStream save = new ObjectInputStream(l_saveFile);
            l_game_info = (GameInformation) save.readObject();
            save.close();
        } catch (Exception e){
            throw new GameException("Error loading the game");
        }
        return l_game_info;
    }

    private void checkDirectory(){
        String l_file_path = GameConstants.D_SAVE_DIRECTORY;
        File l_file_directory = new File(l_file_path);
        if(!l_file_directory.exists() || !l_file_directory.isDirectory()){
            l_file_directory.mkdirs();
        }
    }
}