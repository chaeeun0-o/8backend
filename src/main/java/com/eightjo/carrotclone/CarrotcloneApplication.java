package com.eightjo.carrotclone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
@PropertySource(value = {"classpath:application.properties"})
public class CarrotcloneApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarrotcloneApplication.class, args);
    }

}
