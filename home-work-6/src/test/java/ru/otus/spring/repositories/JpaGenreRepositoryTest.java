package ru.otus.spring.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.models.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({JpaGenreRepository.class})
class JpaGenreRepositoryTest {

    @Autowired
    private JpaGenreRepository repository;

    @Test
    void shouldReturnAllGenresByIds() {
        var response = repository.findAllByIds(List.of(1L, 3L, 5L));
        Genre g1 = new Genre(1, "Genre_1");
        Genre g3 = new Genre(3, "Genre_3");
        Genre g5 = new Genre(5, "Genre_5");

        assertThat(response).hasSize(3).containsAll(List.of(g1, g3, g5));
    }

    @Test
    void shouldReturnAllGenres() {
        var response = repository.findAll();
        Genre g1 = new Genre(1, "Genre_1");
        Genre g2 = new Genre(2, "Genre_2");
        Genre g3 = new Genre(3, "Genre_3");
        Genre g4 = new Genre(4, "Genre_4");
        Genre g5 = new Genre(5, "Genre_5");
        Genre g6 = new Genre(6, "Genre_6");

        assertThat(response).hasSize(6).containsAll(List.of(g1, g2, g3, g4, g5, g6));
    }
}