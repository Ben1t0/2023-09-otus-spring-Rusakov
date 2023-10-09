package ru.otus.spring.service;

import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.Test;
import ru.otus.spring.model.Answer;
import ru.otus.spring.model.Question;
import ru.otus.spring.repository.CsvQuestionDao;
import ru.otus.spring.repository.QuestionDao;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class QuestionServiceSimpleTest {
    @Test
    void shouldCorrectFillAnswers() {
        QuestionDao questionDao = new CsvQuestionDao("test-questions.csv");
        IOService io = new ConsoleIOService();
        QuestionService qs = new QuestionServiceSimple(questionDao, io);

        Question q1 = new Question(1, "q1");
        q1.addAnswer(new Answer("q1a1", false));
        q1.addAnswer(new Answer("q1a2", true));
        Question q2 = new Question(2, "q2");
        q2.addAnswer(new Answer("q2a1", true));
        q2.addAnswer(new Answer("q2a2", false));
        Question q3 = new Question(4, "q3");
        Question q4 = new Question(3, "q4");
        q4.addAnswer(new Answer("q4a1", true));

        Set<Question> questions = Set.of(q1, q2, q3, q4);

        RecursiveComparisonConfiguration configuration = RecursiveComparisonConfiguration.builder()
                .withIgnoreCollectionOrder(true)
                .withIgnoredCollectionOrderInFields("answers")
                .build();

        assertThat(questionDao.getAllQuestions())
                .hasSize(4)
                .usingRecursiveFieldByFieldElementComparator(configuration)
                .containsAll(questions);
    }
}