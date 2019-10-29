package com.github.modsezam.monitorynote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication()
//@EnableScheduling
public class MonitoryNoteApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonitoryNoteApplication.class, args);
    }

}
