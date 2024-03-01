package ru.otus.spring.services;

import ru.otus.spring.rest.dto.BookCreateDto;
import ru.otus.spring.rest.dto.BookDto;
import ru.otus.spring.rest.dto.BookUpdateDto;

import java.util.List;

public interface BookService {
    BookDto findById(long id);

    List<BookDto> findAll();

    BookDto create(BookCreateDto bookCreateDto);

    BookDto update(BookUpdateDto bookUpdateDto);

    void deleteById(long id);
}
