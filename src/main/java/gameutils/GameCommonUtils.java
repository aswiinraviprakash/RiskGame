package gameutils;

public class GameCommonUtils {

    public static boolean isNumeric(String p_number_value) {
        try {
            int l_number = Integer.parseInt(p_number_value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
}
