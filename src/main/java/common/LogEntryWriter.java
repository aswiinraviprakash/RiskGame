package common;

import constants.GameConstants;
import gameutils.GameCommonUtils;

import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;

/**
 * LogEntryWriter class implements the Observer Interface
 */
public class LogEntryWriter implements Observer {

    /**
     * Method to instantiate logEntryWriter
     * @param p_logbuffer LogBuffer parameter
     */
    public LogEntryWriter(LogEntryBuffer p_logbuffer) {
        p_logbuffer.addObserver(this);
    }

    public void update(Observable p_obervable) {
        writeLogFile(((LogEntryBuffer) p_obervable).getLoggedMessage());
    }

    
    /** Method to write the log message
     * @param p_log_message Log message parameter
     */
    public void writeLogFile(String p_log_message) {
        PrintWriter l_writer = null;
        try {

            GameCommonUtils.checkAndCreateDirectory("logfiles");
            String l_log_file_path = "logfiles" + File.separator + GameConstants.D_LOG_FILE_NAME;
            l_writer = new PrintWriter(new BufferedWriter(new FileWriter(l_log_file_path, true)));
            l_writer.println(p_log_message);

        } catch (Exception e) {
            System.out.println("There seems to some issue with logging");
        } finally {
            l_writer.close();
        }
    }

}