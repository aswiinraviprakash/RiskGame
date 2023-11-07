package common;

import constants.GameConstants;

import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;

public class LogEntryWriter implements Observer {

    public LogEntryWriter(LogEntryBuffer p_logbuffer) {
        p_logbuffer.addObserver(this);
    }

    public void update(Observable p_obervable) {
        writeLogFile(((LogEntryBuffer) p_obervable).getLoggedMessage());
    }

    public void writeLogFile(String p_log_message) {
        PrintWriter l_writer = null;
        try {

            checkDirectory("logfiles");
            String l_log_file_path = "logfiles" + File.separator + GameConstants.D_LOG_FILE_NAME;
            l_writer = new PrintWriter(new BufferedWriter(new FileWriter(l_log_file_path, true)));
            l_writer.println(p_log_message);

        } catch (Exception e) {
            System.out.println("There seems to some issue with logging");
        } finally {
            l_writer.close();
        }
    }

    private void checkDirectory(String p_directory_path) {
        File l_directory = new File(p_directory_path);
        if (!l_directory.exists() || !l_directory.isDirectory()) {
            l_directory.mkdirs();
        }
    }

}