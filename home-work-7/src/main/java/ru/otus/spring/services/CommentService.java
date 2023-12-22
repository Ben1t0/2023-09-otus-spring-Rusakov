package ru.otus.spring.services;

import ru.otus.spring.dto.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto findById(long id);

    List<CommentDto> findByBookId(long id);

    CommentDto create(long bookId, String message);

    CommentDto update(long commentId, String message);

    void deleteById(long id);
}
