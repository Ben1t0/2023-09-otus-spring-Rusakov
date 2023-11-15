package ru.otus.spring.repositories;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ru.otus.spring.exceptions.NotFoundException;
import ru.otus.spring.models.Book;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class BookRepositoryJpa implements BookRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Book> findById(long id) {
        Map<String, Object> properties = new HashMap<>();
        EntityGraph<?> entityGraph = em.getEntityGraph("book-author-genre-graph");
        properties.put("jakarta.persistence.fetchgraph", entityGraph);
        return Optional.ofNullable(em.find(Book.class, id, properties));
    }

    @Override
    public List<Book> findAll() {
        TypedQuery<Book> query = em.createQuery("select b from Book as b", Book.class);
        return query.getResultList();
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == null) {
            em.persist(book);
            return book;
        } else {
            return em.merge(book);
        }
    }

    @Override
    public void deleteById(long id) {
        var book = findById(id).orElseThrow(() -> new NotFoundException("book with %d not found".formatted(id)));
        em.remove(book);
    }
}
