package gamemenu;

import constants.GameMessageConstants;
import gameplay.GameEngine;
import gameutils.GameException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import mapparser.GameMapEditor;

public class GameMenu {
  
    public static void main(String[] args) {

        try {

            BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.printf("Welcome to Risk War Game!!!%nMenu%n");

            System.out.printf("Type: mapeditor - Map Editor / gameplay - GamePlay / exit - Exit%n%n");
            String l_menu_option = l_reader.readLine();
            do {

                switch (l_menu_option) {
                    case "mapeditor":
                        GameMapEditor l_map_edit_obj = new GameMapEditor();
                        l_map_edit_obj.initialiseMapEditingPhase();
                        break;
                    case "gameplay":
                        GameEngine l_game_engine_obj = new GameEngine();
                        l_game_engine_obj.initializeAndRunEngine();
                        break;
                    default:
                        System.out.println("Enter Valid Input!!");
                }

                System.out.printf("Type: mapeditor - Map Editor / gameplay - GamePlay / exit - Exit%n");
                l_menu_option = l_reader.readLine();

            } while (!l_menu_option.equals("exit"));

        } catch (GameException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e);
            System.out.println(GameMessageConstants.D_INTERNAL_ERROR);
        }

    }
}