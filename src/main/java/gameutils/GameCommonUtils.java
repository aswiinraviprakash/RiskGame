package gameutils;

import java.io.File;

/**
 * This class provides utility methods for operations.
 */
public class GameCommonUtils {

    /**
     * Checks if the given string has a numeric value.
     * @param p_number_value The string to check.
     * @return True if the string has a numeric value.
     */
    public static boolean isNumeric(String p_number_value) {
        try {
            int l_number = Integer.parseInt(p_number_value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Checks and create the directory if not present
     * @param p_directory_path directory path parameter
     */
    public static void checkAndCreateDirectory(String p_directory_path) {
        File l_directory = new File(p_directory_path);
        if (!l_directory.exists() || !l_directory.isDirectory()) {
            l_directory.mkdirs();
        }
    }

}
