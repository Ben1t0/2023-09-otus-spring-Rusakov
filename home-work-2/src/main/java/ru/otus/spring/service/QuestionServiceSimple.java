package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring.model.Question;
import ru.otus.spring.model.Student;
import ru.otus.spring.repository.QuestionDao;

import java.util.Set;

@Service
public class QuestionServiceSimple implements QuestionService {
    private final QuestionDao questionDao;

    private final IOService iOService;


    private int answerCountToPass;

    public QuestionServiceSimple(QuestionDao questionDao, IOService iOService,
                                 @Value("${app.questions.countToPass}") int countToPass) {
        this.questionDao = questionDao;
        this.iOService = iOService;
        this.answerCountToPass = countToPass;
    }

    @Override
    public void startTesting() {
        Student st = getAcquaintedWithStudent();
        int rightAnswersCount = 0;
        Set<Question> questions = questionDao.getAllQuestions();
        if (questions.size() < answerCountToPass) {
            answerCountToPass = questions.size();
        }
        for (Question q : questions) {
            rightAnswersCount += askQuestionAndCheckAnswer(q) ? 1 : 0;
        }
        printResults(st, rightAnswersCount, questions.size());
    }

    private Student getAcquaintedWithStudent() {
        return new Student(iOService.getInput("Please, enter your first name"),
                iOService.getInput("Please, enter your last name"));
    }

    private void printResults(Student student, int rightAnswersCount, int questionsCount) {
        if (rightAnswersCount >= answerCountToPass) {
            iOService.printMessage(
                    String.format("Dear, %s %s, you PASS the test. (%d correct answer(s) of %d)", student.firstName(),
                            student.lastName(), rightAnswersCount, questionsCount));
        } else {
            iOService.printMessage(
                    String.format("Dear, %s %s, you FAIL the test. (%d correct answer(s) of %d)", student.firstName(),
                            student.lastName(), rightAnswersCount, questionsCount));
        }

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