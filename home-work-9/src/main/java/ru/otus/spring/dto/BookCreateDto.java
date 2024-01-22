package ru.otus.spring.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
public class BookCreateDto {
    private Long id;

    @Builder.Default
    private String title = "";

    private Long authorId;

    @Builder.Default
    private List<Long> genreIds = new ArrayList<>();
}
