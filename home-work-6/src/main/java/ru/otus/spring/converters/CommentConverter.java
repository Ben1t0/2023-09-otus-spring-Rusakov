package ru.otus.spring.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.spring.models.Comment;

@RequiredArgsConstructor
@Component
public class CommentConverter {
    public String commentToString(Comment comment) {
        return "Id: %d, Book title: %s, Text: %s".formatted(comment.getId(), comment.getBook().getTitle(),
                comment.getMessage());
    }
}