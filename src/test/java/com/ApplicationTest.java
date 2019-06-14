package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
public class ApplicationTest {

    public static void main(String[] args) {
        ConfigurableApplicationContext context =  SpringApplication.run(ApplicationTest.class, args);

    }
}