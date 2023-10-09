package ru.otus.spring.repository;

import org.junit.jupiter.api.Test;
import ru.otus.spring.exception.QuestionReadException;
import ru.otus.spring.model.Question;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CsvQuestionDaoTest {

    @Test
    void shouldThrowExceptionWhenFileNotFound() {
        QuestionDao dao = new CsvQuestionDao("missingFile.csv");
        assertThatThrownBy(dao::getAllQuestions)
                .isInstanceOf(QuestionReadException.class)
                .hasMessage("File missingFile.csv not found!");
    }

    @Test
    void shouldReadQuestionFileCorrect() {
        QuestionDao dao = new CsvQuestionDao("test-questions.csv");
        Set<Question> questions = dao.getAllQuestions();
        assertThat(questions)
                .hasSize(4);
    }
}