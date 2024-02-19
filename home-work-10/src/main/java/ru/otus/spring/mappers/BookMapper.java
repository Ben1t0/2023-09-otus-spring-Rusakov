package ru.otus.spring.mappers;

import org.springframework.stereotype.Component;
import ru.otus.spring.models.Book;
import ru.otus.spring.rest.dto.BookDto;

@Component
public class BookMapper {

    public BookDto toDto(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .author(new BookDto.Author(book.getAuthor().getId(), book.getAuthor().getFullName()))
                .title(book.getTitle())
                .genres(book.getGenres().stream().map(g -> new BookDto.Genre(g.getId(), g.getName())).toList())
                .build();
    }
}
