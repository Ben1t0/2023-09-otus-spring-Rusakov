package ru.otus.spring.mappers;

import org.springframework.stereotype.Component;
import ru.otus.spring.models.Comment;
import ru.otus.spring.rest.dto.CommentDto;

@Component
public class CommentMapper {
    public CommentDto toDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .message(comment.getMessage())
                .bookId(comment.getBook().getId())
                .build();
    }
}
