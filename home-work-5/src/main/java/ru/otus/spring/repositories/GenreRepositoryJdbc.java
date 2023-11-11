package ru.otus.spring.repositories;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Repository
public class GenreRepositoryJdbc implements GenreRepository {

    private final NamedParameterJdbcOperations jdbcNamed;

    private final JdbcOperations jdbc;

    public GenreRepositoryJdbc(NamedParameterJdbcOperations jdbcNamed) {
        this.jdbcNamed = jdbcNamed;
        this.jdbc = jdbcNamed.getJdbcOperations();
    }

    @Override
    public List<Genre> findAll() {
        return jdbc.query("SELECT g.id, g.name FROM genres as g",
                new GenreRepositoryJdbc.GenreRowMapper()).stream().toList();
    }

    @Override
    public List<Genre> findAllByIds(Collection<Long> ids) {
        Map<String, Object> params = Map.of("id", ids);
        var genre = jdbcNamed.query("SELECT g.id, g.name FROM genres as g WHERE g.id IN (:id)",
                params, new GenreRepositoryJdbc.GenreRowMapper());
        return genre;
    }

    private static class GenreRowMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet rs, int i) throws SQLException {
            return new Genre(rs.getLong("id"), rs.getString("name"));
        }
    }
}
