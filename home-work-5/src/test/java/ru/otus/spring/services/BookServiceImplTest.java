package ru.otus.spring.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.spring.dto.BookCreateDto;
import ru.otus.spring.models.Genre;

import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BookServiceImplTest {
    @Autowired
    private BookService bookService;

    @Test
    void shouldInsertBook() {
        var g2 = new Genre(2, "Genre_2");
        var g6 = new Genre(6, "Genre_6");

        int countBefore = bookService.findAll().size();

        var book = bookService.insert(
                BookCreateDto.builder()
                        .title("New Book")
                        .authorId(2L)
                        .genreIds(List.of(2L, 6L))
                        .build()
        );

        assertThat(book.getId()).isGreaterThan(0);

        var response = bookService.findById(book.getId()).get();

        assertThat(response)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", book.getId())
                .hasFieldOrPropertyWithValue("title", "New Book");

        assertThat(response.getAuthor())
                .hasFieldOrPropertyWithValue("id", 2L)
                .hasFieldOrPropertyWithValue("fullName", "Author_2");
        assertThat(response.getGenres()).hasSize(2).containsAll(List.of(g2, g6));
        assertThat(bookService.findAll().size()).isEqualTo(countBefore + 1);
    }

    @Test
    void shouldUpdateBook() throws SQLException {
        var g2 = new Genre(2, "Genre_2");
        var g6 = new Genre(6, "Genre_6");
        var book = bookService.update(
                BookCreateDto.builder()
                        .id(3L)
                        .title("New Book")
                        .authorId(2L)
                        .genreIds(List.of(2L, 6L))
                        .build()
        );

        var response = bookService.findById(book.getId()).get();

        assertThat(response)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", 3L)
                .hasFieldOrPropertyWithValue("title", "New Book");

        assertThat(response.getAuthor())
                .hasFieldOrPropertyWithValue("id", 2L)
                .hasFieldOrPropertyWithValue("fullName", "Author_2");
        assertThat(response.getGenres()).hasSize(2).containsAll(List.of(g2, g6));
    }
}