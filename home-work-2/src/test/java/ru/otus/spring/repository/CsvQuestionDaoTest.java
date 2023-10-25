package ru.otus.spring.repository;

import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.Test;
import ru.otus.spring.exception.QuestionReadException;
import ru.otus.spring.model.Answer;
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
    void shouldCorrectReadQuestions(){
        QuestionDao repository = new CsvQuestionDao("test-questions.csv");

        Question q1 = new Question(1, "q1");
        q1.addAnswer(1, new Answer("q1a1", false));
        q1.addAnswer(2, new Answer("q1a2", true));
        Question q2 = new Question(2, "q2");
        q2.addAnswer(1, new Answer("q2a1", true));
        q2.addAnswer(2, new Answer("q2a2", false));
        Question q3 = new Question(3, "q3");
        q3.addAnswer(1, new Answer("q3a1", false));
        q3.addAnswer(2, new Answer("q3a2", false));
        q3.addAnswer(3, new Answer("q3a3", true));
        Question q4 = new Question(4, "q4");
        q4.addAnswer(2, new Answer("q4a2", false));
        q4.addAnswer(1, new Answer("q4a1", true));
        q4.addAnswer(3, new Answer("q4a3", false));

        Set<Question> questions = Set.of(q1, q2, q3, q4);

        RecursiveComparisonConfiguration configuration = RecursiveComparisonConfiguration.builder()
                .withIgnoreCollectionOrder(true)
                .withIgnoredCollectionOrderInFields("answers")
                .build();

        var readQuestions = repository.getAllQuestions();
        assertThat(readQuestions)
                .hasSize(4)
                .usingRecursiveFieldByFieldElementComparator(configuration)
                .containsAll(questions);
    }
}