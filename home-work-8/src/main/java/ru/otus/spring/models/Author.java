package ru.otus.spring.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collation = "authors")
public class Author {

    @Id
    private String id;

    private String fullName;
}
