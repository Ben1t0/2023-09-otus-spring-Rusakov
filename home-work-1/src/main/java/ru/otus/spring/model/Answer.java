package ru.otus.spring.model;

import java.util.Objects;

public record Answer(Integer questionId, String answer) {
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Answer answer1 = (Answer) o;
        return questionId.equals(answer1.questionId) && answer.equals(answer1.answer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionId, answer);
    }
}
