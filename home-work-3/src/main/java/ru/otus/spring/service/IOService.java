package ru.otus.spring.service;

public interface IOService {
    void printMessage(String message);

    String readString();

    String readStringWithPrompt(String message);
}
