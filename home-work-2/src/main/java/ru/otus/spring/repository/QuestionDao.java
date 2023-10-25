package ru.otus.spring.repository;

import ru.otus.spring.exception.QuestionReadException;
import ru.otus.spring.model.Question;

import java.util.List;

public interface QuestionDao {

    List<Question> getAllQuestions() throws QuestionReadException;
}
