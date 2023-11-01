package ru.otus.spring.model;

import java.util.Collections;
import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;


public class Question {
    private final Integer id;

    private final String question;

    private final SortedMap<Integer, Answer> answers;

    public Question(Integer id, String question) {
        this(id, question, new TreeMap<>());
    }

    private Question(Integer id, String question, SortedMap<Integer, Answer> answers) {
        this.id = id;
        this.question = question;
        this.answers = answers;
    }

    public Integer getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public SortedMap<Integer, Answer> getAnswers() {
        return Collections.unmodifiableSortedMap(answers);
    }

    public void addAnswer(int id, Answer a) {
        answers.put(id, a);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Question question = (Question) o;
        return id.equals(question.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
