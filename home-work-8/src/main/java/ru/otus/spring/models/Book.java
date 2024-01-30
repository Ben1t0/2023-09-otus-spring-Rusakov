package ru.otus.spring.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "books")
@Getter
@Setter
@AllArgsConstructor
@Builder
public class Book {

    @Id
    private String id;

    private String title;

    private Author author;

    private List<Genre> genres;
}
