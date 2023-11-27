package gamemenu;

import common.LogEntryBuffer;
import common.LogEntryWriter;
import constants.GameMessageConstants;
import gameplay.GameEngine;
import gameutils.GameException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import mapparser.GameMapEditor;

/**
 * The GameMenu class is the entry point of the game.
 */
public class GameMenu {
    /**
     * Adds the logger
     */
    private static LogEntryBuffer d_logger = LogEntryBuffer.getInstance();

    /**
     * dirver class for executing the entire game
     * @param args String[] args The main class parameter
     * Main class gets the input from the player.
     */
    public static void main(String[] args) {

        try {

            BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));

            LogEntryWriter l_log_writer = new LogEntryWriter(d_logger);

            System.out.printf("Welcome to Risk War Game!!!%nMenu%n");
            d_logger.addLogger("Welcome to Risk War Game!!!%nMenu%n");

            System.out.printf("Type: mapeditor - Map Editor / gameplay - GamePlay / exit - Exit%n%n");
            String l_menu_option = l_reader.readLine();
            do {

                switch (l_menu_option) {
                    case "mapeditor":
                        GameMapEditor l_map_edit_obj = new GameMapEditor();
                        l_map_edit_obj.initialiseAndRunMapEditor();
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
            d_logger.addLogger(e.getMessage());
        } catch (Exception e) {
            System.out.println(GameMessageConstants.D_INTERNAL_ERROR);
            d_logger.addLogger(e.getMessage());
            d_logger.addLogger(GameMessageConstants.D_INTERNAL_ERROR);
        }

    }
}