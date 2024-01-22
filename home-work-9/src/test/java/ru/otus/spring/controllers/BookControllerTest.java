package ru.otus.spring.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.dto.BookCreateDto;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.mappers.BookMapper;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Genre;
import ru.otus.spring.services.AuthorService;
import ru.otus.spring.services.BookService;
import ru.otus.spring.services.GenreService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
@Import(BookMapper.class)
class BookControllerTest {
    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @Autowired
    private BookMapper mapper;

    @Autowired
    private MockMvc mvc;

    @Test
    void shouldReturnCorrectBookList() throws Exception {

        var g1 = new Genre(1, "Genre_1");
        var g2 = new Genre(2, "Genre_2");
        var g3 = new Genre(3, "Genre_3");
        var g4 = new Genre(4, "Genre_4");

        var a1 = new Author(1, "Author_1");
        var a2 = new Author(2, "Author_2");

        var b1 = new Book(1L, "BookTitle_1", a1, List.of(g1, g2));
        var b2 = new Book(2L, "BookTitle_2", a2, List.of(g3, g4));

        List<Book> books = List.of(b1, b2);
        given(bookService.findAll()).willReturn(books);

        List<BookDto> dtos = books.stream().map(mapper::toDto).toList();
        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("list"))
                .andExpect(model().attributeExists("books"))
                .andExpect(model().attribute("books", dtos));
    }

    @Test
    void shouldReturnCreatePage() throws Exception {
        var g1 = new Genre(1, "Genre_1");
        var g2 = new Genre(2, "Genre_2");
        var g3 = new Genre(3, "Genre_3");
        var g4 = new Genre(4, "Genre_4");

        var a1 = new Author(1, "Author_1");
        var a2 = new Author(2, "Author_2");

        var authors = List.of(a1, a2);
        given(authorService.findAll()).willReturn(authors);

        var genres = List.of(g1, g2, g3, g4);
        given(genreService.findAll()).willReturn(genres);

        mvc.perform(get("/books/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("create_book"))
                .andExpect(model().attribute("authorsList", authors))
                .andExpect(model().attribute("genresList", genres))
                .andExpect(model().attributeExists("book"));
    }

    @Test
    void shouldCreateBook() throws Exception {
        mvc.perform(post("/books/create")
                        .param("title", "newBook")
                        .param("authorId", "3")
                        .param("genreIds", "1,2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/"));

        var bookDto = BookCreateDto.builder()
                .authorId(3L)
                .title("newBook")
                .genreIds(List.of(1L, 2L))
                .build();

        ArgumentCaptor<BookCreateDto> argument = ArgumentCaptor.forClass(BookCreateDto.class);
        verify(bookService, times(1)).create(argument.capture());
        assertThat(argument.getValue())
                .hasFieldOrPropertyWithValue("title", bookDto.getTitle())
                .hasFieldOrPropertyWithValue("authorId", bookDto.getAuthorId())
                .extracting("genreIds")
                .usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(bookDto.getGenreIds());
    }

    @Test
    void shouldReturnEditPage() throws Exception {
        var g1 = new Genre(1L, "Genre_1");
        var g2 = new Genre(2L, "Genre_2");
        var g3 = new Genre(3L, "Genre_3");
        var g4 = new Genre(4L, "Genre_4");

        var a1 = new Author(1L, "Author_1");
        var a2 = new Author(2L, "Author_2");

        var b1 = new Book(1L, "BookTitle_1", a1, List.of(g1, g2));

        given(bookService.findById(1L)).willReturn(Optional.of(b1));

        var authors = List.of(a1, a2);
        given(authorService.findAll()).willReturn(authors);

        var genres = List.of(g1, g2, g3, g4);
        given(genreService.findAll()).willReturn(genres);

        mvc.perform(get("/books/edit").param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("edit_book"))
                .andExpect(model().attribute("authorsList", authors))
                .andExpect(model().attribute("genresList", genres))
                .andExpect(model().attributeExists("book"))
                .andExpect(model().attribute("book", hasProperty("title", is(b1.getTitle()))))
                .andExpect(model()
                        .attribute("book", hasProperty("authorId", is(b1.getAuthor().getId()))))
                .andExpect(model()
                        .attribute("book", hasProperty("genreIds",
                                containsInAnyOrder(g1.getId(), g2.getId()))));
    }

    @Test
    void shouldEditBook() throws Exception {
        var bookDto = BookCreateDto.builder()
                .id(2L)
                .authorId(3L)
                .title("new title")
                .genreIds(List.of(1L, 2L))
                .build();

        mvc.perform(post("/books/edit")
                        .param("id", bookDto.getId().toString())
                        .param("title", bookDto.getTitle())
                        .param("authorId", bookDto.getAuthorId().toString())
                        .param("genreIds", bookDto.getGenreIds().stream()
                                .map(String::valueOf)
                                .collect(Collectors.joining(","))))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/"));


        ArgumentCaptor<BookCreateDto> argument = ArgumentCaptor.forClass(BookCreateDto.class);
        verify(bookService, times(1)).update(argument.capture());
        assertThat(argument.getValue())
                .hasFieldOrPropertyWithValue("id", bookDto.getId())
                .hasFieldOrPropertyWithValue("title", bookDto.getTitle())
                .hasFieldOrPropertyWithValue("authorId", bookDto.getAuthorId())
                .extracting("genreIds")
                .usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(bookDto.getGenreIds());
    }

    @Test
    void shouldDeleteBook() throws Exception {
        mvc.perform(post("/books/delete")
                        .param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/"));

        verify(bookService, times(1)).deleteById(1);
    }
}