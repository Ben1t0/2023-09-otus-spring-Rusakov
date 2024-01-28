package ru.otus.spring.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "authors")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Author {

    @Id
    private String id;

    private String fullName;
}
