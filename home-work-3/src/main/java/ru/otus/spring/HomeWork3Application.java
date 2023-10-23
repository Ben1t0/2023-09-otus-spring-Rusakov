package ru.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.otus.spring.service.QuestionService;
import ru.otus.spring.service.QuestionServiceSimple;

@SpringBootApplication
public class HomeWork3Application {

    public static void main(String[] args) {
        var context = SpringApplication.run(HomeWork3Application.class, args);

        QuestionService qs = context.getBean(QuestionServiceSimple.class);
        qs.startTesting();

    }
}
