package dev.mr3.sb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
/**
 * Bootstraps the Spring Boot application and starts component scanning.
 * Keywords: entry-point, spring-boot, auto-configuration
 */
public class SbApplication {

    public static void main(String[] args) {
        SpringApplication.run(SbApplication.class, args);
    }

}
