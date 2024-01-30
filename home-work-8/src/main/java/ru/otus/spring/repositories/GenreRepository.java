package ru.otus.spring.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.models.Genre;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface GenreRepository extends MongoRepository<Genre, String> {
    List<Genre> findAllByIdIn(Collection<String> id);

    Optional<Genre> findGenreByName(String name);
}
