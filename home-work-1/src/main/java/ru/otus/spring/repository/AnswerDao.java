package ru.otus.spring.repository;

import ru.otus.spring.model.Answer;

import java.util.List;

public interface AnswerDao {

    List<Answer> getAllAnswers();
}
