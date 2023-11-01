package ru.otus.spring.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.converters.AuthorConverter;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Import({AuthorRepositoryJdbc.class, AuthorConverter.class})
class AuthorRepositoryJdbcTest {

    @Autowired
    private AuthorRepositoryJdbc jdbc;

    @Autowired
    private AuthorConverter converter;

    @Test
    void shouldReturnAuthorById() {
        var response= jdbc.findById(1);
        assertFalse(response.isEmpty());
        assertEquals("Id: 1, FullName: Author_1", converter.authorToString(response.get()));
    }

    @Test
    void shouldReturnAllAuthors() {
        System.out.printf(jdbc.findById(1).map(a -> a.getId() + " " + a.getFullName()).toString());
    }
}