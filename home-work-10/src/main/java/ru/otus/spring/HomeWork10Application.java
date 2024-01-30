package ru.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HomeWork10Application {
    public static void main(String[] args) {
        SpringApplication.run(HomeWork10Application.class, args);
        System.out.printf("Чтобы перейти на страницу сайта открывай: %n%s%n",
                "http://localhost:8080");
    }
}