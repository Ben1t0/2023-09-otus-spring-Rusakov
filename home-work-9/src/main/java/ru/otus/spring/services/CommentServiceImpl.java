package ru.otus.spring.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.dto.CommentCreateDto;
import ru.otus.spring.dto.CommentDto;
import ru.otus.spring.dto.CommentUpdateDto;
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
    public CommentDto create(CommentCreateDto commentCreateDto) {
        Book book = bookRepository.findById(commentCreateDto.getBookId()).orElseThrow(
                () -> new NotFoundException("book with %d not found".formatted(commentCreateDto.getBookId())));
        Comment comment = new Comment(null, commentCreateDto.getMessage(), book);
        return commentMapper.toDto(commentRepository.save(comment));
    }

    @Override
    public CommentDto update(CommentUpdateDto commentUpdateDto) {
        Comment comment = commentRepository.findById(commentUpdateDto.getCommentId()).orElseThrow(
                () -> new NotFoundException("Comment with %d not found".formatted(commentUpdateDto.getCommentId())));
        comment.setMessage(commentUpdateDto.getMessage());
        return commentMapper.toDto(commentRepository.save(comment));
    }

    @Override
    public void deleteById(long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NotFoundException("Comment with %d not found".formatted(commentId)));
        commentRepository.delete(comment);
    }
}
