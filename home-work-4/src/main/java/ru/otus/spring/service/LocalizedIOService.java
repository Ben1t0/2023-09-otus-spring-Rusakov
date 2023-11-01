package ru.otus.spring.service;

public interface LocalizedIOService {
    void printMessageLocalized(String code);

    void printFormattedMessageLocalized(String code, Object... args);

    String readStringWithPromptLocalized(String promptCode);
}
