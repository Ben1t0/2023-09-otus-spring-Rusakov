package ru.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.otus.spring.service.RunnerService;
import ru.otus.spring.service.RunnerServiceImpl;

@SpringBootApplication
public class HomeWork3Application {

    public static void main(String[] args) {
        var context = SpringApplication.run(HomeWork3Application.class, args);
        RunnerService runner = context.getBean(RunnerServiceImpl.class);
        runner.run();
    }
}
