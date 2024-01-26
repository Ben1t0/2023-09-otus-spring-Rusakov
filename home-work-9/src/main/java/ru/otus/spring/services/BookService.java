package ru.otus.spring.services;

import ru.otus.spring.dto.BookCreateDto;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.BookUpdateDto;

import java.util.List;

public interface BookService {
    BookUpdateDto findById(long id);

    List<BookDto> findAll();

    BookDto create(BookCreateDto bookCreateDto);

    BookDto update(BookUpdateDto bookUpdateDto);

    void deleteById(long id);
}
