package ru.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class HomeWork5Application {

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(HomeWork5Application.class, args);
    }
}
