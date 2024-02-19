package ru.otus.spring.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.mappers.GenreMapper;
import ru.otus.spring.models.Genre;
import ru.otus.spring.rest.dto.GenreDto;
import ru.otus.spring.services.GenreService;

import java.util.List;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GenreController.class)
@Import(GenreMapper.class)
class GenreControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GenreService genreService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private GenreMapper genreMapper;

    @DisplayName("Должен успешно возвращать все жанры")
    @Test
    void shouldReturnCorrectBookList() throws Exception {

        var g1 = new Genre(1, "Genre_1");
        var g2 = new Genre(2, "Genre_2");
        var g3 = new Genre(3, "Genre_3");
        var g4 = new Genre(4, "Genre_4");


        List<GenreDto> dtos = Stream.of(g1, g2, g3, g4).map(genreMapper::toDto).toList();

        when(genreService.findAll()).thenReturn(dtos);

        mvc.perform(get("/api/v1/genres"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(dtos)));
        verify(genreService, times(1)).findAll();
    }
}