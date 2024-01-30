package ru.otus.spring.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.spring.models.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MongoGenreRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private GenreRepository repository;

    @Test
    void shouldReturnAllGenresByIds() {
        var response = repository.findAllByIdIn(List.of("1", "3", "5"));
        Genre g1 = new Genre("1", "Genre_1");
        Genre g3 = new Genre("3", "Genre_3");
        Genre g5 = new Genre("5", "Genre_5");

        assertThat(response).hasSize(3)
                .usingRecursiveFieldByFieldElementComparator()
                .containsAll(List.of(g1, g3, g5));
    }
}