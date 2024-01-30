package ru.otus.spring.dto;

import lombok.Builder;

@Builder
public record CommentDto(long id, String message, long bookId) {
}
