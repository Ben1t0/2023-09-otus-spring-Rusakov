package ru.otus.spring.services;

import ru.otus.spring.dto.BookCreateDto;
import ru.otus.spring.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Optional<Book> findById(long id);

    List<Book> findAll();

    Book create(BookCreateDto bookCreateDto);

    Book update(BookCreateDto bookCreateDto);

    void deleteById(long id);
}
