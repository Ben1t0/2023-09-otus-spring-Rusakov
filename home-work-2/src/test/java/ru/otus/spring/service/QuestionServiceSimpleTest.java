package ru.otus.spring.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.spring.model.Answer;
import ru.otus.spring.model.Question;
import ru.otus.spring.repository.CsvQuestionDao;
import ru.otus.spring.repository.QuestionDao;

import java.util.List;

import static org.mockito.Mockito.*;

class QuestionServiceSimpleTest {

    @Test
    void shouldCorrectReadAnswers() {
        QuestionDao questionDao = Mockito.mock(CsvQuestionDao.class);
        IOService io = Mockito.mock(ConsoleIOService.class);
        int questionsCount = 3;
        int rightAnswersCount = 2;
        int countToPass = 2;
        String studentFirstName = "Ivan";
        String studentLastName = "Ivanov";


        Question q1 = new Question(1, "q1");
        q1.addAnswer(1, new Answer("q1a1", true));
        q1.addAnswer(2, new Answer("q1a2", false));
        Question q2 = new Question(2, "q2");
        q2.addAnswer(1, new Answer("q2a1", true));
        q2.addAnswer(2, new Answer("q2a2", false));
        Question q3 = new Question(3, "q3");
        q3.addAnswer(1, new Answer("q3a1", false));
        q3.addAnswer(2, new Answer("q3a2", true));
        q3.addAnswer(2, new Answer("q3a3", false));

        List<Question> questions = List.of(q1, q2, q3);

        when(questionDao.getAllQuestions()).thenReturn(questions);
        when(io.getInput("Please, enter your first name")).thenReturn(studentFirstName);
        when(io.getInput("Please, enter your last name")).thenReturn(studentLastName);
        when(io.getInput("Select answer: ")).thenReturn("1");

        StudentService studentService = new StudentServiceSimple(io);
        ResultsService resultsService = new ResultsServiceSimple(io, countToPass);

        QuestionService qs = new QuestionServiceSimple(questionDao, io, studentService, resultsService, countToPass);

        qs.startTesting();

        verify(io, times(1))
                .printMessage(String.format("Dear, %s %s, you PASS the test. (%d correct answer(s) of %d)",
                        studentFirstName, studentLastName, rightAnswersCount, questionsCount));
    }
}