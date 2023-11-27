package ru.otus.spring.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.exceptions.NotFoundException;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Comment;
import ru.otus.spring.repositories.BookRepository;
import ru.otus.spring.repositories.CommentRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    @Override
    public Optional<Comment> findById(long id) {
        return commentRepository.findById(id);
    }

    @Override
    public List<Comment> findByBookId(long id) {
        return commentRepository.findByBookId(id);
    }

    @Override
    public Comment create(long bookId, String message) {
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new NotFoundException("book with %d not found".formatted(bookId)));
        Comment comment = new Comment(null, message, book);
        return commentRepository.save(comment);
    }

    @Override
    public Comment update(long commentId, String message) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NotFoundException("Comment with %d not found".formatted(commentId)));
        comment.setMessage(message);
        return commentRepository.save(comment);
    }

    @Override
    public void deleteById(long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NotFoundException("Comment with %d not found".formatted(commentId)));
        commentRepository.delete(comment);
    }
}
