import gameplay.GameEngine;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class GameMenu {
    public static void main(String[] args) {

        try {

            BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.printf("Welcome to Risk War Game!!!%nMenu");

            System.out.println("Type 1 - Map Editor 2 - GamePlay 3 - Exit");
            int l_menu_option = Integer.parseInt(l_reader.readLine());
            do {

                switch (l_menu_option) {
                    case 1:
                        break;
                    case 2:
                        GameEngine l_game_engine_obj = new GameEngine();
                        l_game_engine_obj.initializeAndRunEngine();
                        break;
                    default:
                        System.out.println("Enter Valid Input!! Type 1 - Map Editor 2 - GamePlay 3 - Exit");
                }

                l_menu_option = Integer.parseInt(l_reader.readLine());

            } while (l_menu_option != 3);

        } catch (Exception e) {
            System.out.println("There seems to be some issue try restarting the Game");
        }

    }
}