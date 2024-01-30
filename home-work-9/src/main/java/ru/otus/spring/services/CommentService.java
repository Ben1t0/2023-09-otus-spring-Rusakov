package ru.otus.spring.services;

import ru.otus.spring.dto.CommentCreateDto;
import ru.otus.spring.dto.CommentDto;
import ru.otus.spring.dto.CommentUpdateDto;

import java.util.List;

public interface CommentService {
    CommentDto findById(long id);

    List<CommentDto> findByBookId(long id);

    CommentDto create(CommentCreateDto commentCreateDto);

    CommentDto update(CommentUpdateDto commentUpdateDto);

    void deleteById(long id);
}
