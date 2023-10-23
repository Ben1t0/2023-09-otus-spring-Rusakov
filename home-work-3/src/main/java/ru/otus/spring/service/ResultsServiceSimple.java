package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.model.TestResult;

import java.util.Locale;

@Service
public class ResultsServiceSimple implements ResultsService {
    private final IOService ioService;

    private final MessageSource messageSource;

    private final int answerCountToPass;

    @Value("${app.questions.locale}")
    private Locale locale;

    public ResultsServiceSimple(IOService ioService, MessageSource messageSource, @Value("${app.questions.countToPass}") int countToPass) {
        this.ioService = ioService;
        this.messageSource = messageSource;
        this.answerCountToPass = countToPass;
    }

    @Override
    public void printResults(TestResult results) {

        if (results.rightAnswersCount() >= answerCountToPass) {
            ioService.printMessage(
                    messageSource.getMessage("user.test.pass", new String[]{results.student().firstName(),
                            results.student().lastName(),
                            String.valueOf(results.rightAnswersCount()),
                            String.valueOf(results.questionsCount())}, locale));
        } else {
            ioService.printMessage(
                    messageSource.getMessage("user.test.fail", new String[]{results.student().firstName(),
                            results.student().lastName(),
                            String.valueOf(results.rightAnswersCount()),
                            String.valueOf(results.questionsCount())}, locale));
        }
    }
}
