package ru.otus.spring.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Comment;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class JpaCommentRepositoryTest {

    @Autowired
    TestEntityManager em;

    @Autowired
    CommentRepository commentRepository;

    @Test
    void shouldFindCommentById() {
        var b1 = new Book(1L, "BookTitle_1", null, new ArrayList<>());
        var c = new Comment(1L, "Comment_1_for_book_1", b1);

        var response = commentRepository.findById(c.getId());

        assertThat(response)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .ignoringFields("book.author", "book.genres")
                .isEqualTo(c);
    }

    @Test
    void shouldFindAllComments() {
        var b1 = new Book(1L, "BookTitle_1", null, new ArrayList<>());
        var c1 = new Comment(1L, "Comment_1_for_book_1", b1);
        var c2 = new Comment(2L, "Comment_2_for_book_1", b1);
        var comments = List.of(c1, c2);

        var response = commentRepository.findByBookId(b1.getId());

        assertThat(response).hasSize(2)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("book.author", "book.genres")
                .containsAll(comments);
    }
}