package ru.otus.spring.repositories;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.models.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class AuthorRepositoryJdbc implements AuthorRepository {

    private final NamedParameterJdbcOperations jdbcNamed;

    private final JdbcOperations jdbc;

    public AuthorRepositoryJdbc(NamedParameterJdbcOperations jdbcNamed) {
        this.jdbcNamed = jdbcNamed;
        this.jdbc = jdbcNamed.getJdbcOperations();
    }

    @Override
    public List<Author> findAll() {
        return jdbc.query("SELECT a.id, a.full_name FROM authors as a", new AuthorRowMapper()).stream().toList();
    }

    @Override
    public Optional<Author> findById(long id) {
        Map<String, Object> params = Map.of("id", id);
        var author = jdbcNamed.queryForObject("SELECT a.id, a.full_name FROM authors AS a WHERE id = :id",
                params, new AuthorRowMapper());
        return author != null ? Optional.of(author) : Optional.empty();
    }

    private static class AuthorRowMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet rs, int i) throws SQLException {
            return new Author(rs.getLong("id"), rs.getString("full_name"));
        }
    }
}
