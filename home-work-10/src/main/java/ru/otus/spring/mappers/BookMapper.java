package ru.otus.spring.mappers;

import org.springframework.stereotype.Component;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Genre;
import ru.otus.spring.rest.dto.BookCreateDto;
import ru.otus.spring.rest.dto.BookDto;
import ru.otus.spring.rest.dto.BookUpdateDto;

import java.util.List;

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

    public Book toModel(BookUpdateDto updateDto, Author author, List<Genre> genres) {
        return Book.builder()
                .id(updateDto.id())
                .title(updateDto.title())
                .author(author)
                .genres(genres)
                .build();
    }

    public Book toModel(BookCreateDto createDto, Author author, List<Genre> genres) {
        return Book.builder()
                .title(createDto.title())
                .author(author)
                .genres(genres)
                .build();
    }
}
