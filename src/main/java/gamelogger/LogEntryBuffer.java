package gamelogger;

import java.io.IOException;
import java.util.Observable;
import java.util.logging.FileHandler;

/**
 *
 * @author USER
 */
public class LogEntryBuffer extends Observable {

    private String p_log_entry;

    public static String p_log_name = "game logger";

    public static FileHandler p_file;

    public LogEntryBuffer() {
        this.p_log_entry = "";

        try {
            p_file = new FileHandler("");
        } catch (IOException e) {

        }
    }

    public String getLogEntry() {
        return this.p_log_entry;
    }

    public void writeLogFile() {
        this.notifyObservers(this);
    }

}
