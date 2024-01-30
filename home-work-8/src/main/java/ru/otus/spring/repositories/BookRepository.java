package ru.otus.spring.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.models.Book;

public interface BookRepository extends MongoRepository<Book, String> {
}
