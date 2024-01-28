package ru.otus.spring.dto;

import lombok.Builder;

import java.util.Set;

@Builder
public record BookCreateDto(String id, String title, String authorId, Set<String> genreIds) {
}
