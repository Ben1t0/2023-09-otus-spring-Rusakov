package ru.otus.spring.repositories;

import ru.otus.spring.exceptions.EntityNotFoundException;
import ru.otus.spring.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Optional<Book> findById(long id) throws EntityNotFoundException;

    List<Book> findAll();

    Book save(Book book);

    void deleteById(long id) throws EntityNotFoundException;
}
