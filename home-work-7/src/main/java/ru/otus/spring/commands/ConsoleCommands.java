package ru.otus.spring.commands;

import org.h2.tools.Console;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.sql.SQLException;

@ShellComponent
public class ConsoleCommands {

    @ShellMethod(value = "Start H2 console", key = "console")
    public void findAllBooks() throws SQLException {
        Console.main();
    }
}
