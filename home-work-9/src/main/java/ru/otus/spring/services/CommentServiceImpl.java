package ru.otus.spring.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.dto.CommentDto;
import ru.otus.spring.exceptions.NotFoundException;
import ru.otus.spring.mappers.CommentMapper;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Comment;
import ru.otus.spring.repositories.BookRepository;
import ru.otus.spring.repositories.CommentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    private final CommentMapper commentMapper;

    @Override
    public CommentDto findById(long id) {
        return commentRepository.findById(id).map(commentMapper::toDto).orElse(null);
    }

    @Override
    public List<CommentDto> findByBookId(long id) {
        return commentRepository.findByBookId(id).stream().map(commentMapper::toDto).toList();
    }

    @Override
    public CommentDto create(long bookId, String message) {
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new NotFoundException("book with %d not found".formatted(bookId)));
        Comment comment = new Comment(null, message, book);
        return commentMapper.toDto(commentRepository.save(comment));
    }

    @Override
    public CommentDto update(long commentId, String message) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NotFoundException("Comment with %d not found".formatted(commentId)));
        comment.setMessage(message);
        return commentMapper.toDto(commentRepository.save(comment));
    }

    @Override
    public void deleteById(long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NotFoundException("Comment with %d not found".formatted(commentId)));
        commentRepository.delete(comment);
    }
}
