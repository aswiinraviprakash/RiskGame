package utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * The class deals with the implementation of writing thr logs into files.
 */
public class LogEntryWriter implements Observer {
    public String fileName;
    public LogEntryWriter(String fileName) {
        this.fileName = fileName;
    }
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
