package ru.otus.spring.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.exceptions.NotFoundException;
import ru.otus.spring.mappers.CommentMapper;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Comment;
import ru.otus.spring.repositories.CommentRepository;
import ru.otus.spring.rest.dto.CommentCreateDto;
import ru.otus.spring.rest.dto.CommentDto;
import ru.otus.spring.rest.dto.CommentUpdateDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final BookService bookService;

    private final CommentMapper commentMapper;

    @Override
    @Transactional(readOnly = true)
    public CommentDto findById(long id) {
        return commentMapper.toDto(findByIdOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> findByBookId(long id) {
        return commentRepository.findByBookId(id).stream().map(commentMapper::toDto).toList();
    }

    @Override
    public CommentDto create(CommentCreateDto commentCreateDto) {
        Book book = bookService.findByIdOrThrow(commentCreateDto.getBookId());
        Comment comment = commentMapper.toModel(commentCreateDto, book);
        return commentMapper.toDto(commentRepository.save(comment));
    }

    @Override
    public CommentDto update(CommentUpdateDto commentUpdateDto) {
        Comment comment = findByIdOrThrow(commentUpdateDto.getCommentId());
        comment.setMessage(commentUpdateDto.getMessage());
        return commentMapper.toDto(commentRepository.save(comment));
    }

    private Comment findByIdOrThrow(long id) {
        return commentRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Comment with %d not found".formatted(id)));
    }

    @Override
    public void deleteById(long commentId) {
        commentRepository.deleteById(commentId);
    }
}
