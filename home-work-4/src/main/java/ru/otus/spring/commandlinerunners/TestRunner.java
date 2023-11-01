package ru.otus.spring.commandlinerunners;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.otus.spring.service.QuestionService;

@Component
public class TestRunner implements CommandLineRunner {
    private final QuestionService questionService;

    public TestRunner(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    public void run(String... args) throws Exception {
        questionService.startTesting();
    }
}
