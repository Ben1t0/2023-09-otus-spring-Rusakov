package ru.otus.spring.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CommentCreateDto {
    @NotNull(message = "Book ID can't be null")
    private Long bookId;

    @NotBlank(message = "Comment message can't be blank")
    private String message;
}
