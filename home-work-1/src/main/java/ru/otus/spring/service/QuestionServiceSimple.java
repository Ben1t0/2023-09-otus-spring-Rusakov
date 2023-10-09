package ru.otus.spring.service;

import ru.otus.spring.model.Answer;
import ru.otus.spring.model.Question;
import ru.otus.spring.repository.QuestionDao;

import java.util.Set;

public class QuestionServiceSimple implements QuestionService {
    private final QuestionDao questionDao;

    private final IOService iOService;

    public QuestionServiceSimple(QuestionDao questionDao, IOService iOService) {
        this.questionDao = questionDao;
        this.iOService = iOService;
    }

    @Override
    public void askAllQuestions() {
        Set<Question> questions = questionDao.getAllQuestions();
        for (Question q : questions) {
            iOService.printMessage("Question: " + q.getQuestion());
            if (q.getAnswers().size() > 0) {
                iOService.printMessage("Select answer: ");
                int i = 1;
                for (Answer a : q.getAnswers()) {
                    iOService.printMessage(i + " " + a.answer());
                    i++;
                }
            } else {
                iOService.printMessage("Please enter answer: ");
            }
            iOService.printMessage("%input from user%");
            iOService.printMessage("-----");
        }
    }
}
