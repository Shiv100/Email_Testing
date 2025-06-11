package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.demo") // Ensure all components are scanned
public class MailServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(MailServerApplication.class, args);
    }
}
