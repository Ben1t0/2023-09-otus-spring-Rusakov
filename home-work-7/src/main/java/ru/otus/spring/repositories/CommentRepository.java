package ru.otus.spring.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Override
    @EntityGraph("comment-book-graph")
    Optional<Comment> findById(Long id);

    @EntityGraph("comment-book-graph")
    List<Comment> findByBookId(Long bookId);
}
