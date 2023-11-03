package ru.otus.spring.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.converters.AuthorConverter;
import ru.otus.spring.models.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@JdbcTest
@Import({AuthorRepositoryJdbc.class, AuthorConverter.class})
class AuthorRepositoryJdbcTest {

    @Autowired
    private AuthorRepositoryJdbc jdbc;

    @Autowired
    private AuthorConverter converter;

    @Test
    void shouldReturnAuthorById() {
        var response = jdbc.findById(1);
        assertFalse(response.isEmpty());
        assertEquals("Id: 1, FullName: Author_1", converter.authorToString(response.get()));
    }

    @Test
    void shouldReturnAllAuthors() {
        var response = jdbc.findAll();
        Author a1 = new Author(1, "Author_1");
        Author a2 = new Author(2, "Author_2");
        Author a3 = new Author(3, "Author_3");
        assertThat(response).hasSize(3).containsAll(List.of(a1, a2, a3));
    }
}