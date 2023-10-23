package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring.model.Question;
import ru.otus.spring.model.Student;
import ru.otus.spring.model.TestResult;
import ru.otus.spring.repository.QuestionDao;

import java.util.Set;

@Service
public class QuestionServiceSimple implements QuestionService {
    private final QuestionDao questionDao;

    private final IOService iOService;

    private final StudentService studentService;

    private final ResultsService resultsService;

    private int answerCountToPass;

    public QuestionServiceSimple(QuestionDao questionDao, IOService iOService, StudentService studentService,
                                 ResultsService resultsService,
                                 @Value("${app.questions.countToPass}") int countToPass) {
        this.questionDao = questionDao;
        this.iOService = iOService;
        this.studentService = studentService;
        this.resultsService = resultsService;
        this.answerCountToPass = countToPass;
    }

    @Override
    public void startTesting() {
        Student st = studentService.getStudent();
        int rightAnswersCount = 0;
        Set<Question> questions = questionDao.getAllQuestions();
        if (questions.size() < answerCountToPass) {
            answerCountToPass = questions.size();
        }

        for (Question q : questions) {
            rightAnswersCount += askQuestionAndCheckAnswer(q) ? 1 : 0;
        }
        resultsService.printResults(new TestResult(st, rightAnswersCount, questions.size()));
    }


    private boolean askQuestionAndCheckAnswer(Question question) {
        iOService.printMessage("Question: " + question.getQuestion());
        for (var answers : question.getAnswers().entrySet()) {
            iOService.printMessage(answers.getKey() + " " + answers.getValue().answer());
        }
        while (true) {
            try {
                int answer = Integer.parseInt(iOService.getInput("Select answer: "));
                return question.getAnswers().containsKey(answer) && question.getAnswers().get(answer).isCorrect();
            } catch (NumberFormatException exception) {
                iOService.printMessage("Please enter number");
            }
        }
    }
}