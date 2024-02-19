package ru.otus.spring.services;

import ru.otus.spring.rest.dto.AuthorDto;

import java.util.List;

public interface AuthorService {
    List<AuthorDto> findAll();
}
