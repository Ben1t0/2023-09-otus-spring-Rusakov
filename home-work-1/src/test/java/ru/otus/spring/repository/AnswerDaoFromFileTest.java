package ru.otus.spring.repository;

import org.junit.jupiter.api.Test;
import ru.otus.spring.exception.AnswerReadException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AnswerDaoFromFileTest {
    @Test
    void shouldThrowExceptionWhenFileNotFound() {
        AnswerDao dao = new AnswerDaoFromFile("missingFile.csv");
        assertThatThrownBy(dao::getAllAnswers)
                .isInstanceOf(AnswerReadException.class)
                .hasMessage("File missingFile.csv not found!");
    }
}