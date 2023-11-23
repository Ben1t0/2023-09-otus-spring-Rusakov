package ru.otus.spring.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ru.otus.spring.models.Genre;

import java.util.Collection;
import java.util.List;

@Repository
public class JpaGenreRepository implements GenreRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Genre> findAll() {
        TypedQuery<Genre> query = em.createQuery("select g from Genre as g", Genre.class);
        return query.getResultList();
    }

    @Override
    public List<Genre> findAllByIds(Collection<Long> ids) {
        TypedQuery<Genre> query = em.createQuery("select g from Genre as g where g.id in :ids", Genre.class);
        query.setParameter("ids", ids);
        return query.getResultList();
    }
}
