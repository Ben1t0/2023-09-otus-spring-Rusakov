package ru.otus.spring.exception;

public class AnswerReadException extends RuntimeException {
    public AnswerReadException(String message) {
        super(message);
    }

    public AnswerReadException(String message, Throwable cause) {
        super(message, cause);
    }
}
