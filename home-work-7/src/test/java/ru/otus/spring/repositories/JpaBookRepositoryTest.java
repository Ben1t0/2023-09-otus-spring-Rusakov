package ru.otus.spring.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Genre;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class JpaBookRepositoryTest {

    @Autowired
    TestEntityManager em;

    @Autowired
    private BookRepository repository;

    @Test
    void shouldFindBookById() {
        var g1 = new Genre(1L, "Genre_1");
        var g2 = new Genre(2L, "Genre_2");
        var a1 = new Author(1L, "Author_1");
        var b1 = new Book(1L, "BookTitle_1", a1, List.of(g1, g2));

        var response = repository.findById(b1.getId());

        assertThat(response).isPresent().get().usingRecursiveComparison().isEqualTo(b1);
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

        var b1 = new Book(1L, "BookTitle_1", a1, List.of(g1, g2));
        var b2 = new Book(2L, "BookTitle_2", a2, List.of(g3, g4));
        var b3 = new Book(3L, "BookTitle_3", a3, List.of(g5, g6));

        var response = repository.findAll();

        assertThat(List.of(b1, b2, b3)).hasSize(3)
                .usingRecursiveFieldByFieldElementComparator()
                .containsAll(response);
    }


    @Test
    void shouldSaveBook() {
        var g4 = em.find(Genre.class, 4L);
        var g5 = em.find(Genre.class, 5L);
        var genres = List.of(g4, g5);
        Author a2 = em.find(Author.class, 2L);

        Book book = new Book(null, "New Book", a2, genres);

        book = repository.save(book);

        assertThat(book.getId()).isGreaterThan(0);

        var response = repository.findById(book.getId());

        assertThat(response)
                .isPresent()
                .get()
                .isNotNull()
                .hasFieldOrPropertyWithValue("title", "New Book")
                .extracting("id").isNotNull();

        assertThat(response.get().getAuthor())
                .hasFieldOrPropertyWithValue("id", 2L)
                .hasFieldOrPropertyWithValue("fullName", "Author_2");
        assertThat(response.get().getGenres()).hasSize(2).containsAll(genres);
    }

    @Test
    void shouldUpdateBook() {
        Genre g3 = new Genre(3, "Genre_3");
        Genre g4 = new Genre(4, "Genre_4");
        Genre g5 = new Genre(5, "Genre_5");
        Genre g6 = new Genre(6, "Genre_6");
        Author a2 = new Author(2, "Author_2");

        var book = repository.findById(3L);

        assertThat(book)
                .isPresent()
                .get()
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", 3L)
                .hasFieldOrPropertyWithValue("title", "BookTitle_3");

        assertThat(book.get().getAuthor())
                .hasFieldOrPropertyWithValue("id", 3L)
                .hasFieldOrPropertyWithValue("fullName", "Author_3");
        assertThat(book.get().getGenres()).hasSize(2)
                .usingRecursiveFieldByFieldElementComparator()
                .containsAll(List.of(g5, g6));

        book.get().setTitle("New Title");
        book.get().setAuthor(a2);

        List<Genre> newGenres = new ArrayList<>();
        newGenres.add(g3);
        newGenres.add(g4);
        book.get().setGenres(newGenres);

        repository.save(book.get());

        var response = repository.findById(3L);

        assertThat(response)
                .isPresent()
                .get()
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", 3L)
                .hasFieldOrPropertyWithValue("title", "New Title");

        assertThat(response.get().getAuthor())
                .hasFieldOrPropertyWithValue("id", 2L)
                .hasFieldOrPropertyWithValue("fullName", "Author_2");
        assertThat(response.get().getGenres()).hasSize(2)
                .usingRecursiveFieldByFieldElementComparator()
                .containsAll(List.of(g3, g4));
    }


    @Test
    void shouldDeleteBookById() {
        var response = repository.findById(1L);
        assertThat(response.isPresent()).isTrue();

        repository.deleteById(1L);

        assertThat(repository.findById(1L)).isEmpty();
    }
}