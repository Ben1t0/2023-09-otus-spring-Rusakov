package ru.otus.spring.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collation = "genres")
public class Genre {
    @Id
    private String id;

    private String name;
}
