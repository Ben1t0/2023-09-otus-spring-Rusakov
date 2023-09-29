package ru.otus.spring.view;

import java.util.Scanner;

public class ConsoleQuestionsIO implements QuestionsIO {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }

    @Override
    public String getInput() {
        return scanner.nextLine();
    }
}
