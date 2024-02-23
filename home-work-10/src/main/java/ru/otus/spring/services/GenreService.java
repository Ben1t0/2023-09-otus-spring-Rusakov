package ru.otus.spring.services;

import ru.otus.spring.models.Genre;
import ru.otus.spring.rest.dto.GenreDto;

import java.util.Collection;
import java.util.List;

public interface GenreService {
    List<GenreDto> findAll();

    List<Genre> findAllByIdsOrThrow(Collection<Long> genreIds);
}
