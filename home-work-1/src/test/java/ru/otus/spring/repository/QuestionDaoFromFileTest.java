package ru.otus.spring.repository;

import org.junit.jupiter.api.Test;
import ru.otus.spring.exception.QuestionReadException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class QuestionDaoFromFileTest {

    @Test
    void shouldThrowExceptionWhenFileNotFound() {
        assertThatThrownBy(() -> new QuestionDaoFromFile("missingFile.csv"))
                .isInstanceOf(QuestionReadException.class)
                .hasMessage("File missingFile.csv not found!");
    }

    @Test
    void shouldReadQuestionFileCorrect() {
        QuestionDao dao = new QuestionDaoFromFile("test-questions.csv");
        assertThat(dao.getAllQuestions())
                .hasSize(4);
    }
}