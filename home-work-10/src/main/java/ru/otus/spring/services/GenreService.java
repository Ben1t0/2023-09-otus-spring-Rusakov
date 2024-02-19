package ru.otus.spring.services;

import ru.otus.spring.rest.dto.GenreDto;

import java.util.List;

public interface GenreService {
    List<GenreDto> findAll();
}
