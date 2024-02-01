package uz.maniac4j.rtutest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RtuTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(RtuTestApplication.class, args);
    }

}
