package ru.otus.spring.mappers;

import org.springframework.stereotype.Component;
import ru.otus.spring.dto.BookCreateDto;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Genre;

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

    public BookCreateDto toCreateDto(Book book) {
        return BookCreateDto.builder()
                .id(book.getId())
                .authorId(book.getAuthor().getId())
                .title(book.getTitle())
                .genreIds(book.getGenres().stream().map(Genre::getId).toList())
                .build();
    }
}
