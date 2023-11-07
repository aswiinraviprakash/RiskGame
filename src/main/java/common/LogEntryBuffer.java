package common;

public class LogEntryBuffer extends Observable {
    private static LogEntryBuffer d_logger_instance = null;

    private String d_log_message;

    private LogEntryBuffer() {}

    public static LogEntryBuffer getInstance() {
        if (d_logger_instance == null) d_logger_instance = new LogEntryBuffer();
        return d_logger_instance;
    }

    public String getLoggedMessage() {
        return this.d_log_message;
    }

    public void addLogger(String p_log_message) {
        this.d_log_message = p_log_message;
        notifyObservers(this);
    }
}