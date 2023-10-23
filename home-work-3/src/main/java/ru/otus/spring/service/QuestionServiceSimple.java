package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.model.Question;
import ru.otus.spring.model.Student;
import ru.otus.spring.model.TestResult;
import ru.otus.spring.repository.QuestionDao;

import java.util.Locale;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class QuestionServiceSimple implements QuestionService {
    private final QuestionDao questionDao;

    private final IOService iOService;

    private final StudentService studentService;

    private final ResultsService resultsService;

    private final MessageSource messageSource;

    @Value("${app.questions.countToPass}")
    private int answerCountToPass;

    @Value("${app.questions.locale}")
    private Locale locale;

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
        iOService.printMessage(messageSource.getMessage("question", new String[]{question.getQuestion()}, locale));
        for (var answers : question.getAnswers().entrySet()) {
            iOService.printMessage(answers.getKey() + " " + answers.getValue().answer());
        }
        while (true) {
            try {
                int answer = Integer.parseInt(iOService.getInput(messageSource.getMessage("answer.select",
                        new String[]{}, locale)));
                return question.getAnswers().containsKey(answer) && question.getAnswers().get(answer).isCorrect();
            } catch (NumberFormatException exception) {
                iOService.printMessage(messageSource.getMessage("answer.enter",
                        new String[]{}, locale));
            }
        }
    }
}