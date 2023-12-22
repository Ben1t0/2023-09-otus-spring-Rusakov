package ru.otus.spring.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Override
    @EntityGraph("book-author-genre-graph")
    Optional<Book> findById(Long id);

    @EntityGraph("book-author-genre-graph")
    @Override
    List<Book> findAll();
}
