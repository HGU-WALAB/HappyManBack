package com.walab.happymanback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HappyManBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(HappyManBackApplication.class, args);
    }

}
