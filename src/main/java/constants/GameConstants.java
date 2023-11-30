package constants;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This package contains all the constant classes.
 */
public class GameConstants {

    /**
     * Default army count
     */
    public static final int D_DEFAULT_ARMY_COUNT = 5;

    /**
     * Sets Is_conquered to false
     */
    public static final boolean D_DEFAULT_IS_CONQUERED = false;

    /**
     * Directory path for game map files.
     */
    public static final String D_MAP_DIRECTORY = File.separator + "RiskGame" + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator;

    /**
     * Directory path for game map test files.
     */
    public static final String D_MAP_TEST_DIRECTORY = File.separator + "RiskGame" + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator;

    /**
     * The file where all the gamelogs gets added
     */
    public static final String D_LOG_FILE_NAME = "gamelog.txt";

    /**
     * String for directory
     */
    public static final String D_SAVE_DIRECTORY = File.separator + "RiskGame" + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator+ "savedfiles" + File.separator;

    /**
     * String for test directory
     */
    public static final String D_SAVE_TEST_DIRECTORY = File.separator + "RiskGame" + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator+ "savedfiles" + File.separator;

    /**
     * Enum for gamestate
     */
    public enum GameState {
        
        /**
         * Enum Variable for Start Game
         */
        D_START_GAME,

        /**
         * Enum Variable for Load Game
         */
        D_LOAD_GAME
    }

    /**
     * List for game behaviors
     */
    public static final List<String> D_GAME_COMPUTER_STRATEGIES = Arrays.asList("human", "aggressive", "benevolent", "random", "cheater");

}