package ru.otus.spring.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.spring.models.Genre;

import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = "spring.shell.interactive.enabled=false")
class BookServiceImplTest {
    @Autowired
    private BookService bookService;

    @Test
    void shouldInsertBook() {
        var g2 = new Genre(2, "Genre_2");
        var g6 = new Genre(6, "Genre_6");

        int countBefore = bookService.findAll().size();

        var book = bookService.insert("New Book", 2, List.of(2L, 6L));

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
        var book = bookService.update(3, "New Book", 2, List.of(2L, 6L));

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