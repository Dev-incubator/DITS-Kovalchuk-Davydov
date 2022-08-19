package com.example.dits.loggers;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class AuthenticationLogger {
    private final File file = new File("src/main/resources/authentication.log");

    @SneakyThrows
    public void log(String massage) {
        FileUtils.writeStringToFile(file, massage, true);
    }
}
