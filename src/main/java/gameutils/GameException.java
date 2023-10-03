package gameutils;

/**
 * Throws exception
 */
public class GameException extends Exception {

    /**
     * @param p_message Error message describing the error
     */
    public GameException(String p_message) {
        super(p_message);
    }
}
