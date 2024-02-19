package ru.otus.spring.services;

import ru.otus.spring.rest.dto.CommentCreateDto;
import ru.otus.spring.rest.dto.CommentDto;
import ru.otus.spring.rest.dto.CommentUpdateDto;

import java.util.List;

public interface CommentService {
    CommentDto findById(long id);

    List<CommentDto> findByBookId(long id);

    CommentDto create(CommentCreateDto commentCreateDto);

    CommentDto update(CommentUpdateDto commentUpdateDto);

    void deleteById(long id);
}
