package ru.otus.spring;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableMongock
public class HomeWork8Application {

    public static void main(String[] args) {
        SpringApplication.run(HomeWork8Application.class, args);
    }
}