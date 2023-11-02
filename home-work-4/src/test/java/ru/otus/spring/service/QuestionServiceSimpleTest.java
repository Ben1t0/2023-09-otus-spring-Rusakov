package ru.otus.spring.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.config.AppConfig;
import ru.otus.spring.model.Answer;
import ru.otus.spring.model.Question;
import ru.otus.spring.model.Student;
import ru.otus.spring.model.TestResult;
import ru.otus.spring.repository.QuestionDao;

import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest(properties = "spring.shell.interactive.enabled=false")
@EnableConfigurationProperties(value = AppConfig.class)
class QuestionServiceSimpleTest {

    @MockBean
    @Qualifier("consoleIo")
    private IOService consoleIO;

    @MockBean
    @Qualifier("localizedIo")
    private LocalizedIOService io;

    @MockBean
    private QuestionDao questionDao;

    @MockBean
    private ResultsService resultsService;

    @Autowired
    private QuestionService questionService;

    @Test
    void shouldCorrectReadAnswers() {
        int rightAnswersCount = 2;
        String studentFirstName = "Ivan";
        String studentLastName = "Ivanov";
        Student student = new Student(studentFirstName, studentLastName);

        Question q1 = new Question(1, "q1");
        q1.addAnswer(1, new Answer("q1a1", true));
        q1.addAnswer(2, new Answer("q1a2", false));
        Question q2 = new Question(2, "q2");
        q2.addAnswer(1, new Answer("q2a1", true));
        q2.addAnswer(2, new Answer("q2a2", false));
        Question q3 = new Question(3, "q3");
        q3.addAnswer(1, new Answer("q3a1", false));
        q3.addAnswer(2, new Answer("q3a2", true));
        q3.addAnswer(3, new Answer("q3a3", false));
        List<Question> questions = List.of(q1, q2, q3);

        when(questionDao.getAll()).thenReturn(questions);
        when(io.readStringWithPromptLocalized("user.input.firstName")).thenReturn(studentFirstName);
        when(io.readStringWithPromptLocalized("user.input.lastName")).thenReturn(studentLastName);
        when(io.readStringWithPromptLocalized("answer.select")).thenReturn("1");

        questionService.startTesting();

        verify(resultsService, times(1))
                .printResults(new TestResult(student, rightAnswersCount, questions.size()));

    }
}