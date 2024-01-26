package ru.otus.spring.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collation = "books")
public class Book {

    @Id
    private String id;

    private String title;

    private Author author;

    private List<Genre> genres;
}
