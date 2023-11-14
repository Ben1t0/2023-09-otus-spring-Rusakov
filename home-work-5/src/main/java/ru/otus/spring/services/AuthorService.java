package ru.otus.spring.services;

import ru.otus.spring.models.Author;

import java.util.List;

public interface AuthorService {
    List<Author> findAll();
}
