package ru.otus.spring.rest.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record BookDto(Long id, String title, Author author, List<Genre> genres) {

    public record Author(long id, String fullName) {
    }

    public record Genre(long id, String name) {
    }
}
