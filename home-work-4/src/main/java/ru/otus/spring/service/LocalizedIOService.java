package ru.otus.spring.service;

public interface LocalizedIOService extends IOService {
    void printMessageLocalized(String code);

    void printFormattedMessageLocalized(String code, Object... args);

    String readStringWithPromptLocalized(String promptCode);
}