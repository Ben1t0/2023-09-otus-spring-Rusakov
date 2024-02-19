package ru.otus.spring.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.mappers.BookMapper;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Genre;
import ru.otus.spring.rest.dto.BookCreateDto;
import ru.otus.spring.rest.dto.BookDto;
import ru.otus.spring.rest.dto.BookUpdateDto;
import ru.otus.spring.services.BookService;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
@Import(BookMapper.class)
class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private ObjectMapper mapper;

    @DisplayName("Должен успешно возвращать все книги")
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


        List<BookDto> dtos = Stream.of(b1, b2).map(bookMapper::toDto).toList();

        when(bookService.findAll()).thenReturn(dtos);

        mvc.perform(get("/api/v1/books"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(dtos)));
        verify(bookService, times(1)).findAll();
    }

    @DisplayName("Должен успешно возвращать книгу по Id")
    @Test
    void successfullyGetBookById() throws Exception {
        BookDto bookDto = new BookDto(1L, "Book_1", new BookDto.Author(1L, "author_1"),
                List.of(new BookDto.Genre(1L, "genre 1")));

        when(bookService.findById(anyLong())).thenReturn(bookDto);

        mvc.perform(get("/api/v1/books/{bookId}", bookDto.id()))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(bookDto)));
        verify(bookService, times(1)).findById(bookDto.id());
    }

    @DisplayName("Должен успешно создавать книгу")
    @Test
    void shouldCreateBook() throws Exception {
        BookCreateDto bookCreateDto = new BookCreateDto("new book", 1L, List.of(3L, 6L));

        BookDto bookDto = new BookDto(1L, bookCreateDto.title(), new BookDto.Author(1L, "Author 1"),
                List.of(new BookDto.Genre(3L, "genre 3"), new BookDto.Genre(6L, "genre 6")));

        when(bookService.create(bookCreateDto)).thenReturn(bookDto);

        mvc.perform(post("/api/v1/books")
                        .content(mapper.writeValueAsString(bookCreateDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(bookDto)));


        ArgumentCaptor<BookCreateDto> argument = ArgumentCaptor.forClass(BookCreateDto.class);
        verify(bookService, times(1)).create(argument.capture());
        assertThat(argument.getValue())
                .hasFieldOrPropertyWithValue("title", bookCreateDto.title())
                .hasFieldOrPropertyWithValue("authorId", bookCreateDto.authorId())
                .extracting("genreIds")
                .usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(bookCreateDto.genreIds());
    }

    @DisplayName("Должен успешно редактировать книгу")
    @Test
    void shouldEditBook() throws Exception {
        var bookUpdateDto = new BookUpdateDto(2L, "new title", 3L, List.of(1L, 2L));
        BookDto bookDto = new BookDto(1L, bookUpdateDto.title(), new BookDto.Author(3L, "Author 3"),
                List.of(new BookDto.Genre(1L, "genre 1"), new BookDto.Genre(2L, "genre 2")));

        when(bookService.update(bookUpdateDto)).thenReturn(bookDto);

        mvc.perform(put("/api/v1/books")
                        .content(mapper.writeValueAsString(bookUpdateDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(bookDto)));


        ArgumentCaptor<BookUpdateDto> argument = ArgumentCaptor.forClass(BookUpdateDto.class);
        verify(bookService, times(1)).update(argument.capture());
        assertThat(argument.getValue())
                .hasFieldOrPropertyWithValue("id", bookUpdateDto.id())
                .hasFieldOrPropertyWithValue("title", bookUpdateDto.title())
                .hasFieldOrPropertyWithValue("authorId", bookUpdateDto.authorId())
                .extracting("genreIds")
                .usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(bookUpdateDto.genreIds());
    }

    @DisplayName("Должен успешно удалять книгу")
    @Test
    void shouldDeleteBook() throws Exception {
        mvc.perform(delete("/api/v1/books/{bookId}", 1))
                .andExpect(status().isOk());

        verify(bookService, times(1)).deleteById(1);
    }
}