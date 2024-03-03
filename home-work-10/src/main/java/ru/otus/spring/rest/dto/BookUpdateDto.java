package ru.otus.spring.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record BookUpdateDto(@NotNull Long id, @NotBlank String title, @NotNull Long authorId,
                            @NotEmpty List<@NotNull Long> genreIds) {
}
