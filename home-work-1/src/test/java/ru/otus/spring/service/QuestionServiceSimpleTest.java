package ru.otus.spring.service;

import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.Test;
import ru.otus.spring.model.Answer;
import ru.otus.spring.model.Question;
import ru.otus.spring.repository.AnswerDao;
import ru.otus.spring.repository.AnswerDaoFromFile;
import ru.otus.spring.repository.QuestionDao;
import ru.otus.spring.repository.QuestionDaoFromFile;
import ru.otus.spring.view.ConsoleQuestionsIO;
import ru.otus.spring.view.QuestionsIO;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class QuestionServiceSimpleTest {
    @Test
    void shouldCorrectFillAnswers() {
        QuestionDao questionDao = new QuestionDaoFromFile("test-questions.csv");
        AnswerDao answerDao = new AnswerDaoFromFile("test-answers.csv");
        QuestionsIO io = new ConsoleQuestionsIO();
        QuestionService qs = new QuestionServiceSimple(questionDao, answerDao, io);

        Question q1 = new Question(1, "q1");
        q1.addAnswer(new Answer(1, "q1a1"));
        q1.addAnswer(new Answer(1, "q1a2"));
        Question q2 = new Question(2, "q2");
        q2.addAnswer(new Answer(2, "q2a1"));
        q2.addAnswer(new Answer(2, "q2a2"));
        Question q3 = new Question(3, "q3");
        Question q4 = new Question(4, "q4");
        q4.addAnswer(new Answer(4, "q4a1"));

        Set<Question> questions = Set.of(q1, q2, q3, q4);

        RecursiveComparisonConfiguration configuration = RecursiveComparisonConfiguration
                .builder()
                .withIgnoreCollectionOrder(true)
                .withIgnoredCollectionOrderInFields("answers")
                .build();

        assertThat(questionDao.getAllQuestions())
                .hasSize(4)
                .usingRecursiveFieldByFieldElementComparator(configuration)
                .containsAll(questions);
    }
}