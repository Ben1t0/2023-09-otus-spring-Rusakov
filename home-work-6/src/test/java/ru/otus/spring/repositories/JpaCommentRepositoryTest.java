package ru.otus.spring.repositories;

import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Comment;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@Import({JpaCommentRepository.class})
class JpaCommentRepositoryTest {

    @Autowired
    TestEntityManager em;

    @Autowired
    JpaCommentRepository commentRepository;

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

    @Test
    void shouldSaveComment() {
        var book = em.find(Book.class, 1L);

        var comment = new Comment(null, "New comment to book 1", book);
        var savedComment = commentRepository.save(comment);

        var response = commentRepository.findByBookId(book.getId());

        assertThat(response)
                .hasSize(3)
                .extracting(Comment::getId)
                .anyMatch(id -> id.equals(savedComment.getId()));
    }

    @Test
    void shouldUpdateComment() {
        var comment = commentRepository.findById(2L);
        if (comment.isPresent()) {
            comment.get().setMessage("new comment");
            commentRepository.save(comment.get());
        }

        var response = commentRepository.findById(2L);

        assertThat(response)
                .isPresent()
                .get()
                .hasFieldOrPropertyWithValue("id", 2L)
                .hasFieldOrPropertyWithValue("message", "new comment");
    }

    @Test
    void shouldDeleteComment() {
        var response = commentRepository.findById(1);
        assertThat(response.isPresent()).isTrue();

        commentRepository.delete(response.get());

        assertThatThrownBy(() -> commentRepository.findById(1)).isInstanceOf(NoResultException.class);
    }
}