package ru.otus.spring.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record BookCreateDto(Long id, String title, Long authorId, List<Long> genreIds) {
}
