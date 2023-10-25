package ru.otus.spring.service;

import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class ConsoleIOService implements IOService {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }

    @Override
    public String getInput(String message) {
        printMessage(message);
        return scanner.nextLine();
    }
}
