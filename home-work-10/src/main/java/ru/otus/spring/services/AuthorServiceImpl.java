package ru.otus.spring.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.exceptions.NotFoundException;
import ru.otus.spring.mappers.AuthorMapper;
import ru.otus.spring.models.Author;
import ru.otus.spring.repositories.AuthorRepository;
import ru.otus.spring.rest.dto.AuthorDto;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    private final AuthorMapper mapper;

    @Transactional(readOnly = true)
    @Override
    public List<AuthorDto> findAll() {
        return authorRepository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Author findByIdOrThrow(long authorId) {
        return authorRepository.findById(authorId)
                .orElseThrow(
                        () -> new NotFoundException("Author with id %d not found"
                                .formatted(authorId)));
    }
}
