package ru.otus.spring.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.mappers.GenreMapper;
import ru.otus.spring.models.Genre;
import ru.otus.spring.repositories.GenreRepository;
import ru.otus.spring.rest.dto.GenreDto;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    private final GenreMapper mapper;

    @Transactional(readOnly = true)
    @Override
    public List<GenreDto> findAll() {
        return genreRepository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Genre> findAllByIds(Collection<Long> genreIds) {
        var ids = genreIds.stream().distinct().toList();
        return genreRepository.findAllByIdIn(ids);
    }
}
