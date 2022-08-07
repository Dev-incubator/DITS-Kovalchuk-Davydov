package com.example.dits;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class DitsApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(DitsApplication.class, args);
    }
}
