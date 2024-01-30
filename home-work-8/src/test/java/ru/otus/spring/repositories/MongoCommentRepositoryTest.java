package ru.otus.spring.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.spring.models.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MongoCommentRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Test
    void shouldFindCommentById() {
        var c = new Comment("1", "Comment_1_for_book_1", null);

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
        var c1 = new Comment("1", "Comment_1_for_book_1", null);
        var c2 = new Comment("2", "Comment_2_for_book_1", null);
        var comments = List.of(c1, c2);

        var response = commentRepository.findByBookId("1");

        assertThat(response).hasSize(2)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("book")
                .containsAll(comments);
    }
}