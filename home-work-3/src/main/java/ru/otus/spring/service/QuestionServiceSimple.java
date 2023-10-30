package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.model.Question;
import ru.otus.spring.model.Student;
import ru.otus.spring.model.TestResult;
import ru.otus.spring.repository.QuestionDao;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionServiceSimple implements QuestionService {
    private final QuestionDao questionDao;

    private final LocalizedIOService localizedIOService;

    private final StudentService studentService;

    private final ResultsService resultsService;

    @Override
    public void startTesting() {
        Student st = studentService.getStudent();
        int rightAnswersCount = 0;
        List<Question> questions = questionDao.getAll();

        for (Question q : questions) {
            rightAnswersCount += askQuestionAndCheckAnswer(q) ? 1 : 0;
        }
        resultsService.printResults(new TestResult(st, rightAnswersCount, questions.size()));
    }


    private boolean askQuestionAndCheckAnswer(Question question) {
        localizedIOService.printFormattedMessageLocalized("question", question.getQuestion());

        for (var answers : question.getAnswers().entrySet()) {
            ((IOService) localizedIOService).printMessage(answers.getKey() + " " + answers.getValue().answer());
        }

        while (true) {
            try {
                int answer = Integer.parseInt(localizedIOService
                        .readStringWithPromptLocalized("answer.select"));
                return question.getAnswers().containsKey(answer) && question.getAnswers().get(answer).isCorrect();
            } catch (NumberFormatException exception) {
                localizedIOService.printMessageLocalized("answer.enter");
            }
        }
    }
}