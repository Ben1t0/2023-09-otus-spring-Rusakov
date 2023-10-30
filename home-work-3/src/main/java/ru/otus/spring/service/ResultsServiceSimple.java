package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.config.TestConfig;
import ru.otus.spring.model.TestResult;

@Service
public class ResultsServiceSimple implements ResultsService {
    private final LocalizedIOService localizedIOService;

    private final TestConfig testConfig;

    public ResultsServiceSimple(LocalizedIOService ioService, TestConfig testConfig) {
        this.localizedIOService = ioService;
        this.testConfig = testConfig;
    }

    @Override
    public void printResults(TestResult results) {

        if (results.rightAnswersCount() >= testConfig.getCountToPass()) {
            localizedIOService.printFormattedMessageLocalized("user.test.pass", results.student().firstName(),
                    results.student().lastName(),
                    String.valueOf(results.rightAnswersCount()),
                    String.valueOf(results.questionsCount()));
        } else {
            localizedIOService.printFormattedMessageLocalized("user.test.fail", results.student().firstName(),
                    results.student().lastName(),
                    String.valueOf(results.rightAnswersCount()),
                    String.valueOf(results.questionsCount()));
        }
    }
}
