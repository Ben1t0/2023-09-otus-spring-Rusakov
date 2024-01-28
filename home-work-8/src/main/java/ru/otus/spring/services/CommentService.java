package ru.otus.spring.services;

import ru.otus.spring.dto.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto findById(String id);

    List<CommentDto> findByBookId(String id);

    CommentDto create(String bookId, String message);

    CommentDto update(String commentId, String message);

    void deleteById(String id);
}
