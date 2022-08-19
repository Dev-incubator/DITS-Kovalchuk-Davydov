package com.example.dits.loggers;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class AdminLogger {
    private final File file = new File("src/main/resources/logs/adminLogs.txt");
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public void log(String message, Date date) {
        String completeMessage = getCompleteMessage(date, message);

        try {
            FileUtils.writeStringToFile(file, completeMessage, true);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getCompleteMessage(Date date, String message) {
        return dateFormat.format(date) + ": " +
                message + ";\n";
    }
}
