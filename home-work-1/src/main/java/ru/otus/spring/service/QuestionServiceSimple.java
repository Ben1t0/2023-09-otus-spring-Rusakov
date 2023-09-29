package ru.otus.spring.service;

import ru.otus.spring.exception.AnswerReadException;
import ru.otus.spring.model.Answer;
import ru.otus.spring.model.Question;
import ru.otus.spring.repository.AnswerDao;
import ru.otus.spring.repository.QuestionDao;
import ru.otus.spring.view.QuestionsIO;

import java.util.List;
import java.util.Optional;

public class QuestionServiceSimple implements QuestionService {
    private final QuestionDao questionDao;

    private final AnswerDao answerDao;

    private final QuestionsIO questionsIO;

    public QuestionServiceSimple(QuestionDao questionDao, AnswerDao answerDao, QuestionsIO questionIO) {
        this.questionDao = questionDao;
        this.answerDao = answerDao;
        this.questionsIO = questionIO;
        fillQuestionsByAnswers();
    }

    private void fillQuestionsByAnswers() {
        try {
            List<Answer> answers = answerDao.getAllAnswers();
            for (Answer a : answers) {
                Optional<Question> q = questionDao.getQuestionById(a.questionId());
                if (q.isPresent()) {
                    q.get().addAnswer(a);
                    questionDao.save(q.get());
                }
            }
        } catch (AnswerReadException ex) {
            questionsIO.printMessage(ex.getMessage());
        }
    }

    @Override
    public void askAllQuestions() {
        for (Question q : questionDao.getAllQuestions()) {
            questionsIO.printMessage("Question: " + q.getQuestion());
            if (q.getAnswers().size() > 0) {
                questionsIO.printMessage("Select answer: ");
                int i = 1;
                for (Answer a : q.getAnswers()) {
                    questionsIO.printMessage(i + " " + a.answer());
                    i++;
                }
            } else {
                questionsIO.printMessage("Please enter answer: ");
            }
            questionsIO.printMessage("%input from user%");
            questionsIO.printMessage("-----");
        }
    }
}
