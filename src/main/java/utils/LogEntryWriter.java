package utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * The class deals with the implementation of writing thr logs into files.
 */
public class LogEntryWriter implements Observer {
    public String fileName;

    /**
     * Constructor initialising log file variable.
     * @param fileName log file.
     */
    public LogEntryWriter(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Method to write the logs into the console.
     * @param message the message to be updated
     */
    @Override
    public void update(String message) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new FileWriter(fileName, true));
            writer.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            writer.close();
        }
    }
}
