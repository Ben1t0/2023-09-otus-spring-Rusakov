package ru.otus.spring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
public class BookCreateDto {
    @Builder.Default
    @NotBlank
    private String title = "";

    @NotNull
    private Long authorId;

    @Builder.Default
    @NotEmpty
    private List<Long> genreIds = new ArrayList<>();
}
