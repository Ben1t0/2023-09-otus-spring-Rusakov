package ru.otus.spring.repositories;

import ru.otus.spring.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    Optional<Comment> findById(long commentId);

    List<Comment> findByBookId(long bookId);

    Comment save(Comment comment);

    void delete(Comment comment);
}
