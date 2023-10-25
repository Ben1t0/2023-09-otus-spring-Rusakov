package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring.model.TestResult;

@Service
public class ResultsServiceSimple implements ResultsService {
    private final IOService ioService;

    private final int answerCountToPass;

    public ResultsServiceSimple(IOService ioService, @Value("${app.questions.countToPass}") int countToPass) {
        this.ioService = ioService;
        this.answerCountToPass = countToPass;
    }

    @Override
    public void printResults(TestResult results) {

        if (results.rightAnswersCount() >= answerCountToPass) {
            ioService.printMessage(
                    String.format("Dear, %s %s, you PASS the test. (%d correct answer(s) of %d)",
                            results.student().firstName(),
                            results.student().lastName(),
                            results.rightAnswersCount(),
                            results.questionsCount()));
        } else {
            ioService.printMessage(
                    String.format("Dear, %s %s, you FAIL the test. (%d correct answer(s) of %d)",
                            results.student().firstName(),
                            results.student().lastName(),
                            results.rightAnswersCount(),
                            results.questionsCount()));
        }
    }
}
