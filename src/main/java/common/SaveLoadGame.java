import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SaveLoadGame {

    public boolean saveGame(GameInformation p_game_info){
        boolean l_isSaved = false;
        try{
            
            checkDirectory("saveGameFile");
            String l_save_file_path = "saveGameFile"+File.separator+GameConstants.D_SAVE_FILE_NAME; //need to create a file in Constants
            ObjectOutputStream l_save = new ObjectOutputStream(new FileOutputStream(l_save_file_path));
            l_save.writeObject(p_game_info);
            l_save.close();
            l_isSaved = true;
        } catch (Exception e){
            e.printStackTrace();
            l_isSaved = false;
            System.out.println("Error saving the game..");
        }
        return l_isSaved;

    }

    public GameInformation loadGame(){
        GameInformation l_game_info = new GameInformation();
        try{
            String l_save_file_path = "saveGameFile"+File.separator+GameConstants.D_SAVE_FILE_NAME;
            FileInputStream l_saveFile =  new FileInputStream(l_save_file_path);
            ObjectInputStream save = new ObjectInputStream(l_saveFile);
            l_game_info = (GameInformation) save.readObject();
            save.close();
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Error Loading the game...");
        }
        return l_game_info;
    }

    private void checkDirectory(String p_directory_path){
        File l_file_directory = new File(p_directory_path);
        if(!l_file_directory.exists() || !l_file_directory.isDirectory()){
            l_file_directory.mkdirs();
        }
    }
}