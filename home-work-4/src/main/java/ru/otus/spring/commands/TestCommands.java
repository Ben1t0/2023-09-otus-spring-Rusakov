package ru.otus.spring.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.service.QuestionService;

@RequiredArgsConstructor
@ShellComponent
public class TestCommands {

    private final QuestionService questionService;

    @ShellMethod(value = "Start testing", key = {"start", "test"})
    public void startTesting() {
        questionService.startTesting();
    }

}
