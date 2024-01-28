package ru.otus.spring.dto;

import lombok.Builder;

@Builder
public record CommentDto(String id, String message, String bookId) {
}
