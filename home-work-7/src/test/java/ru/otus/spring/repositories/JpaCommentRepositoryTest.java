package ru.otus.spring.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.spring.models.Comment;

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
        var c = new Comment(1L, "Comment_1_for_book_1", null);

        var response = commentRepository.findById(c.getId());

        assertThat(response)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .ignoringFields("book")
                .isEqualTo(c);
    }

    @Test
    void shouldFindAllComments() {
        var c1 = new Comment(1L, "Comment_1_for_book_1", null);
        var c2 = new Comment(2L, "Comment_2_for_book_1", null);
        var comments = List.of(c1, c2);

        var response = commentRepository.findByBookId(1L);

        assertThat(response).hasSize(2)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("book")
                .containsAll(comments);
    }
}