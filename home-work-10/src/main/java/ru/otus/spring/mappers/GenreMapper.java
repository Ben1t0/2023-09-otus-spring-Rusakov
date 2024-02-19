package ru.otus.spring.mappers;

import org.springframework.stereotype.Component;
import ru.otus.spring.models.Genre;
import ru.otus.spring.rest.dto.GenreDto;

@Component
public class GenreMapper {
    public GenreDto toDto(Genre genre) {
        return new GenreDto(genre.getId(), genre.getName());
    }
}
