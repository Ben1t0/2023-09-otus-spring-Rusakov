package ru.otus.spring.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CommentUpdateDto {
    @NotNull(message = "Comment ID can't be null")
    private Long commentId;

    @NotBlank(message = "Comment message can't be blank")
    private String message;
}
