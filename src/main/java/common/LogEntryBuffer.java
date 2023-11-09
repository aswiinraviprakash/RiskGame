package common;

/**
 * Class that acts as an Observable for the entire game
 */
public class LogEntryBuffer extends Observable {
    private static LogEntryBuffer d_logger_instance = null;

    /**
     * Log message
     */
    private String d_log_message;

    /**
     * LogEntryBuffer Constructor
     */
    private LogEntryBuffer() {}

    /**
     * Method to get the currentInstance
     * @return Returns the current log
     */
    public static LogEntryBuffer getInstance() {
        if (d_logger_instance == null) d_logger_instance = new LogEntryBuffer();
        return d_logger_instance;
    }

    /**
     * This method is used to get Log messages
     * @return Returns the log message
     */
    public String getLoggedMessage() {
        return this.d_log_message;
    }

    /**
     * This method is used to add loggers
     * @param p_log_message log message parameter
     */
    public void addLogger(String p_log_message) {
        this.d_log_message = p_log_message;
        notifyObservers(this);
    }
}