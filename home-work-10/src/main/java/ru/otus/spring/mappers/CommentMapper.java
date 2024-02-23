package ru.otus.spring.mappers;

import org.springframework.stereotype.Component;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Comment;
import ru.otus.spring.rest.dto.CommentCreateDto;
import ru.otus.spring.rest.dto.CommentDto;
import ru.otus.spring.rest.dto.CommentUpdateDto;

@Component
public class CommentMapper {
    public CommentDto toDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .message(comment.getMessage())
                .bookId(comment.getBook().getId())
                .build();
    }

    public Comment toModel(CommentCreateDto createDto, Book book) {
        return Comment.builder()
                .message(createDto.getMessage())
                .book(book)
                .build();
    }

    public Comment toModel(CommentUpdateDto updateDto, Book book) {
        return Comment.builder()
                .id(updateDto.getCommentId())
                .message(updateDto.getMessage())
                .book(book)
                .build();
    }
}
