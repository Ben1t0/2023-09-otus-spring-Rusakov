package ru.otus.spring.repositories;

import ru.otus.spring.models.Genre;

import java.util.List;

public interface GenreRepository {
    List<Genre> findAll();

    List<Genre> findAllByIds(List<Long> ids);
}
