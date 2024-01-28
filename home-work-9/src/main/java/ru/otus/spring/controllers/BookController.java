package ru.otus.spring.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.spring.dto.BookCreateDto;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.BookUpdateDto;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Genre;
import ru.otus.spring.services.AuthorService;
import ru.otus.spring.services.BookService;
import ru.otus.spring.services.GenreService;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BookController {

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    @GetMapping("/")
    public String listPage(Model model) {
        List<BookDto> books = bookService.findAll();
        model.addAttribute("books", books);
        return "list";
    }

    @GetMapping("/books/create")
    public String createPage(Model model) {
        BookCreateDto book = BookCreateDto.builder().build();
        model.addAttribute("book", book);
        List<Author> authors = authorService.findAll();
        model.addAttribute("authorsList", authors);
        List<Genre> genres = genreService.findAll();
        model.addAttribute("genresList", genres);
        return "create_book";
    }

    @PostMapping("/books/create")
    public String createBook(@Valid @ModelAttribute("book") BookCreateDto book, BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {

            return "redirect:/books/create";
        }

        bookService.create(book);

        return "redirect:/";
    }

    @GetMapping("/books/edit")
    public String editPage(@RequestParam("id") long id, Model model) {
        BookUpdateDto book = bookService.findById(id);
        model.addAttribute("book", book);
        List<Author> authors = authorService.findAll();
        model.addAttribute("authorsList", authors);
        List<Genre> genres = genreService.findAll();
        model.addAttribute("genresList", genres);
        return "edit_book";
    }

    @PostMapping("/books/edit")
    public String editBook(@Valid @ModelAttribute("book") BookUpdateDto book, BindingResult bindingResult,
                           Model model) {

        if (bindingResult.hasErrors()) {
            return "redirect:/books/edit";
        }
        bookService.update(book);

        return "redirect:/";
    }


    @PostMapping("/books/delete")
    public String deleteBook(@RequestParam("id") long id) {
        bookService.deleteById(id);
        return "redirect:/";
    }
}
