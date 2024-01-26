package ru.otus.spring.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collation = "comments")
public class Comment {

    @Id
    private String id;

    private String message;

    private Book book;
}
