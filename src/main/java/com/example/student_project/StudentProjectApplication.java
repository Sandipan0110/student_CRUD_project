package com.example.student_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@EnableCaching
@ComponentScan({"com.ailiens.common", "com.example.student_project"})
@EnableAutoConfiguration
@SpringBootApplication
public class StudentProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentProjectApplication.class, args);
    }
}
