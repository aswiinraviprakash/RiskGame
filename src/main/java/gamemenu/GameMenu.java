import constants.GameMessageConstants;
import gameplay.GameEngine;
import gameutils.GameException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import mapparser.MapEditor;
public class GameMenu {
  
    public static void main(String[] args) {

        try {

            BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.printf("Welcome to Risk War Game!!!%nMenu%n");

            System.out.printf("Type 1 - Map Editor 2 - GamePlay 3 - Exit%n%n");
            int l_menu_option = Integer.parseInt(l_reader.readLine());
            do {

                switch (l_menu_option) {
                    case 1:
                        MapEditor l_map_edit_obj = new MapEditor();
                        l_map_edit_obj.initialiseMapEditingPhase();
                        break;
                    case 2:
                        GameEngine l_game_engine_obj = new GameEngine();
                        l_game_engine_obj.initializeAndRunEngine();
                        break;
                    default:
                        System.out.println("Enter Valid Input!! Type 1 - Map Editor 2 - GamePlay 3 - Exit");
                }

                System.out.printf("Menu%nType 1 - Map Editor 2 - GamePlay 3 - Exit%%");
                l_menu_option = Integer.parseInt(l_reader.readLine());

            } while (l_menu_option != 3);

        } catch (GameException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e);
            System.out.println(GameMessageConstants.D_INTERNAL_ERROR);
        }

    }
}