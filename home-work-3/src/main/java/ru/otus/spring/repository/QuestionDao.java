package ru.otus.spring.repository;

import ru.otus.spring.exception.QuestionReadException;
import ru.otus.spring.model.Question;

import java.util.Set;

public interface QuestionDao {

    Set<Question> getAllQuestions() throws QuestionReadException;
}
