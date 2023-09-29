package ru.otus.spring.repository;

import ru.otus.spring.model.Question;

import java.util.Optional;
import java.util.Set;

public interface QuestionDao {

    Set<Question> getAllQuestions();

    Optional<Question> getQuestionById(Integer id);

    void save(Question question);
}
