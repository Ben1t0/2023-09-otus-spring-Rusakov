package ru.otus.spring.services;

import ru.otus.spring.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    Optional<Comment> findById(long id);

    List<Comment> findByBookId(long id);

    Comment create(long bookId, String message);

    Comment update(long commentId, String message);

    void deleteById(long id);
}
