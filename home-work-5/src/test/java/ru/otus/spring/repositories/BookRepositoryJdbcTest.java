package ru.otus.spring.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.exceptions.EntityNotFoundException;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@JdbcTest
@Import({BookRepositoryJdbc.class, GenreRepositoryJdbc.class})
class BookRepositoryJdbcTest {

    @Autowired
    private BookRepositoryJdbc jdbc;

    @Test
    void shouldFindBookById() {
        var g1 = new Genre(1, "Genre_1");
        var g2 = new Genre(2, "Genre_2");
        var a1 = new Author(1, "Author_1");
        var b1 = new Book(1, "BookTitle_1", a1, List.of(g1, g2));

        var response = jdbc.findById(b1.getId()).get();

        assertThat(response).isEqualTo(b1);
    }

    @Test
    void shouldFindAllBooks() {
        var g1 = new Genre(1, "Genre_1");
        var g2 = new Genre(2, "Genre_2");
        var g3 = new Genre(3, "Genre_3");
        var g4 = new Genre(4, "Genre_4");
        var g5 = new Genre(5, "Genre_5");
        var g6 = new Genre(6, "Genre_6");

        var a1 = new Author(1, "Author_1");
        var a2 = new Author(2, "Author_2");
        var a3 = new Author(3, "Author_3");

        var b1 = new Book(1, "BookTitle_1", a1, List.of(g1, g2));
        var b2 = new Book(2, "BookTitle_2", a2, List.of(g3, g4));
        var b3 = new Book(3, "BookTitle_3", a3, List.of(g5, g6));


        var response = jdbc.findAll();

        assertThat(response).hasSize(3).containsAll(List.of(b1, b2, b3));
    }

    @Test
    void shouldDeleteBookById() {
        var g1 = new Genre(1, "Genre_1");
        var g2 = new Genre(2, "Genre_2");
        var a1 = new Author(1, "Author_1");
        var b1 = new Book(1, "BookTitle_1", a1, List.of(g1, g2));

        var response = jdbc.findById(b1.getId()).get();

        assertThat(response).isEqualTo(b1);
        jdbc.deleteById(1);

        Exception exception = assertThrows(EntityNotFoundException.class, () -> jdbc.findById(1));

        assertThat(exception.getMessage()).isEqualTo("Book with id 1 not found");
    }

    @Test
    void shouldSaveBook() {
        Genre g4 = new Genre(4, "Genre_4");
        Genre g5 = new Genre(5, "Genre_5");
        Author a2 = new Author(2, "Author_2");
        Book book = new Book(0, "New Book", a2, List.of(g4, g5));

        book = jdbc.save(book);
        assertThat(book.getId()).isGreaterThan(0);

        var response = jdbc.findById(book.getId()).get();

        assertThat(response)
                .isNotNull()
                .hasFieldOrPropertyWithValue("title", "New Book");

        assertThat(response.getAuthor())
                .hasFieldOrPropertyWithValue("id", 2L)
                .hasFieldOrPropertyWithValue("fullName", "Author_2");
        assertThat(response.getGenres()).hasSize(2).containsAll(List.of(g4, g5));
    }

    @Test
    void shouldUpdateBook() {
        Genre g3 = new Genre(3, "Genre_3");
        Genre g4 = new Genre(4, "Genre_4");
        Genre g5 = new Genre(5, "Genre_5");
        Genre g6 = new Genre(6, "Genre_6");
        Author a2 = new Author(2, "Author_2");

        Book book = jdbc.findById(3).get();

        assertThat(book)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", 3L)
                .hasFieldOrPropertyWithValue("title", "BookTitle_3");

        assertThat(book.getAuthor())
                .hasFieldOrPropertyWithValue("id", 3L)
                .hasFieldOrPropertyWithValue("fullName", "Author_3");
        assertThat(book.getGenres()).hasSize(2).containsAll(List.of(g5, g6));

        book.setTitle("New Title");
        book.setAuthor(a2);
        book.setGenres(List.of(g3, g4));

        jdbc.save(book);

        Book response = jdbc.findById(3).get();

        assertThat(response)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", 3L)
                .hasFieldOrPropertyWithValue("title", "New Title");

        assertThat(response.getAuthor())
                .hasFieldOrPropertyWithValue("id", 2L)
                .hasFieldOrPropertyWithValue("fullName", "Author_2");
        assertThat(response.getGenres()).hasSize(2).containsAll(List.of(g3, g4));
    }

}