package com.solaceisle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SolaceIsleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SolaceIsleApplication.class, args);
    }

}
