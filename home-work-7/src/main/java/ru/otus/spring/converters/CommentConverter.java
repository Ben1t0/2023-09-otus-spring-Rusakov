package ru.otus.spring.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.spring.dto.CommentDto;

@RequiredArgsConstructor
@Component
public class CommentConverter {
    public String commentDtoToString(CommentDto comment) {
        return "Id: %d, Book id: %d, Text: %s".formatted(comment.id(), comment.bookId(),
                comment.message());
    }
}