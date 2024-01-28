package ru.otus.spring.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.spring.dto.BookCreateDto;
import ru.otus.spring.models.Genre;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BookServiceImplTest {
    @Autowired
    private BookService bookService;

    @Test
    void shouldInsertBook() {
        int countBefore = bookService.findAll().size();

        var book = bookService.create(
                BookCreateDto.builder()
                        .title("New Book")
                        .authorId("2")
                        .genreIds(Set.of("2", "6"))
                        .build()
        );

        var response = bookService.findById(book.getId());

        assertThat(response)
                .isPresent()
                .get()
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", book.getId())
                .hasFieldOrPropertyWithValue("title", "New Book");

        assertThat(response.get().getAuthor())
                .hasFieldOrPropertyWithValue("id", "2")
                .hasFieldOrPropertyWithValue("fullName", "Author_2");
        assertThat(response.get().getGenres()).hasSize(2);//.containsAll(List.of(g2, g6));
        assertThat(bookService.findAll().size()).isEqualTo(countBefore + 1);
    }

    @Test
    void shouldUpdateBook() {
        var g2 = new Genre("2", "Genre_2");
        var g6 = new Genre("6", "Genre_6");
        var book = bookService.update(
                BookCreateDto.builder()
                        .id("3")
                        .title("New Book")
                        .authorId("2")
                        .genreIds(Set.of("2", "6"))
                        .build()
        );

        var response = bookService.findById(book.getId());

        assertThat(response)
                .isPresent()
                .get()
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", "3")
                .hasFieldOrPropertyWithValue("title", "New Book");

        assertThat(response.get().getAuthor())
                .hasFieldOrPropertyWithValue("id", "2")
                .hasFieldOrPropertyWithValue("fullName", "Author_2");
        assertThat(response.get().getGenres()).hasSize(2)
                .usingRecursiveFieldByFieldElementComparator()
                .containsAll(List.of(g2, g6));
    }
}