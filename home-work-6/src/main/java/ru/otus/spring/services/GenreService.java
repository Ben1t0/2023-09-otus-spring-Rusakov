package ru.otus.spring.services;

import ru.otus.spring.models.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> findAll();
}
