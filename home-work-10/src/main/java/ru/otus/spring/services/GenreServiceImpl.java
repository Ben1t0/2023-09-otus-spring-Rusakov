package ru.otus.spring.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.exceptions.NotFoundException;
import ru.otus.spring.mappers.GenreMapper;
import ru.otus.spring.models.Genre;
import ru.otus.spring.repositories.GenreRepository;
import ru.otus.spring.rest.dto.GenreDto;

import java.util.Collection;
import java.util.List;

import static org.springframework.util.CollectionUtils.isEmpty;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    private final GenreMapper mapper;

    @Transactional(readOnly = true)
    @Override
    public List<GenreDto> findAll() {
        return genreRepository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Genre> findAllByIdsOrThrow(Collection<Long> genreIds) {
        var ids = genreIds.stream().distinct().toList();
        List<Genre> genres = genreRepository.findAllByIdIn(ids);
        if (isEmpty(genres) || genres.size() != ids.size()) {
            throw new NotFoundException("Genres with ids %s not found".formatted(ids));
        }
        return genres;
    }
}
