package ru.otus.spring.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {
    private Long id;

    private String title;

    private Author author;

    private List<Genre> genres;
}
