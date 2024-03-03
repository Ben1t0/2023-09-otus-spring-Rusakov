package ru.otus.spring.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.mappers.AuthorMapper;
import ru.otus.spring.models.Author;
import ru.otus.spring.rest.dto.AuthorDto;
import ru.otus.spring.services.AuthorService;

import java.util.List;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorController.class)
@Import(AuthorMapper.class)
class AuthorControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthorService authorService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private AuthorMapper authorMapper;

    @DisplayName("Должен успешно возвращать всех авторов")
    @Test
    void shouldReturnCorrectBookList() throws Exception {

        var a1 = new Author(1, "Author_1");
        var a2 = new Author(2, "Author_2");


        List<AuthorDto> dtos = Stream.of(a1, a2).map(authorMapper::toDto).toList();

        when(authorService.findAll()).thenReturn(dtos);

        mvc.perform(get("/api/v1/authors"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(dtos)));
        verify(authorService, times(1)).findAll();
    }
}