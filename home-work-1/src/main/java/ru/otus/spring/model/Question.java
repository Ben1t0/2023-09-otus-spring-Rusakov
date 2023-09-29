package ru.otus.spring.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Question {
    private final Integer id;

    private final String question;

    private final Set<Answer> answers;

    public Question(Integer id, String question) {
        this(id, question, new HashSet<>());
    }

    private Question(Integer id, String question, Set<Answer> answers) {
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

    public Set<Answer> getAnswers() {
        return Collections.unmodifiableSet(answers);
    }


    public void addAnswer(Answer a) {
        if (a.questionId().equals(this.id)) {
            answers.add(a);
        }
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

    public Question clone() {
        return new Question(this.id, this.question, this.answers);
    }

}
