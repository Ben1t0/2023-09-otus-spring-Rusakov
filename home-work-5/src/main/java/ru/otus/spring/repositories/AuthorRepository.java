package ru.otus.spring.repositories;

import ru.otus.spring.models.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    List<Author> findAll();

    Optional<Author> findById(long id);
}
