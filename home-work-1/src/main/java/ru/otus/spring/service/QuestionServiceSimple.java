package ru.otus.spring.service;

import ru.otus.spring.model.Answer;
import ru.otus.spring.model.Question;
import ru.otus.spring.repository.QuestionDao;

import java.util.Set;

public class QuestionServiceSimple implements QuestionService {
    private final QuestionDao questionDao;

    private final IOService IOService;

    public QuestionServiceSimple(QuestionDao questionDao, IOService questionIO) {
        this.questionDao = questionDao;
        this.IOService = questionIO;
    }

    @Override
    public void askAllQuestions() {
        Set<Question> questions = questionDao.getAllQuestions();
        for (Question q : questions) {
            IOService.printMessage("Question: " + q.getQuestion());
            if (q.getAnswers().size() > 0) {
                IOService.printMessage("Select answer: ");
                int i = 1;
                for (Answer a : q.getAnswers()) {
                    IOService.printMessage(i + " " + a.answer());
                    i++;
                }
            } else {
                IOService.printMessage("Please enter answer: ");
            }
            IOService.printMessage("%input from user%");
            IOService.printMessage("-----");
        }
    }
}
