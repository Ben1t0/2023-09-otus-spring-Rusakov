package ru.otus.spring.rest.dto;

import lombok.Builder;

@Builder
public record CommentDto(long id, String message, long bookId) {
}
