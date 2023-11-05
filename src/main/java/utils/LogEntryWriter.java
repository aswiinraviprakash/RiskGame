package utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LogEntryWriter implements Observer {

    String l_Filename = "log.txt";
    private String update;
    private List<Observer> observers = new ArrayList<>();

    // FileWriter fileWriter = new FileWriter(l_Filename,true);

    LogEntryBuffer lb = new LogEntryBuffer();

    private String l_filename = "demo";

    public LogEntryWriter(LogEntryBuffer logbuffer) {
        logbuffer.addObserver(this);
    }

    public void update(Observable obervable) {
        //write to log file

        writeLogFile(((LogEntryBuffer)obervable).getLogString());
        
    
    }

    public void writeLogFile(String p_str) {
        PrintWriter l_WriteData = null;
        try {
            checkDirectory("logFiles");
            l_WriteData = new PrintWriter(new BufferedWriter(new FileWriter("logFiles/" + l_Filename + ".log", true)));
            l_WriteData.println(p_str);

        } catch (Exception p_Exception) {
            System.out.println(p_Exception.getMessage());
        } finally {
            l_WriteData.close();
        }
    }

    private void checkDirectory(String path) {
        File directory = new File(path);
        if (!directory.exists() || !directory.isDirectory()) {
            directory.mkdirs();
        }
    }

    // public void clearLogs() {
    //     try {
    //         checkDirectory("logFiles");
    //         File l_File = new File("logFiles/" + l_Filename + ".log");
    //         if (l_File.exists()) {
    //             l_File.delete();
    //         }
    //     } catch (Exception ex) {

    //     }
    // }

}