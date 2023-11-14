package ru.otus.spring.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.models.Author;
import ru.otus.spring.repositories.AuthorRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }
}
