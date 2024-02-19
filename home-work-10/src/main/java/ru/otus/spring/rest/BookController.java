package ru.otus.spring.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.rest.dto.BookCreateDto;
import ru.otus.spring.rest.dto.BookDto;
import ru.otus.spring.rest.dto.BookUpdateDto;
import ru.otus.spring.services.BookService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BookController {
    private final BookService bookService;

    @GetMapping("/api/v1/books")
    public List<BookDto> listPage() {
        return bookService.findAll();
    }

    @GetMapping("/api/v1/books/{bookId}")
    public BookDto getById(@PathVariable Long bookId) {
        return bookService.findById(bookId);
    }

    @PostMapping("/api/v1/books")
    public BookDto createBook(@Valid @RequestBody BookCreateDto book) {
        return bookService.create(book);
    }

    @PutMapping("/api/v1/books")
    public BookDto editBook(@Valid @RequestBody BookUpdateDto book) {
        return bookService.update(book);
    }

    @DeleteMapping("/api/v1/books/{bookId}")
    public void deleteBook(@PathVariable long bookId) {
        bookService.deleteById(bookId);
    }
}
