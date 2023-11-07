package ru.otus.spring.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.exceptions.NotFoundException;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Genre;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class BookRepositoryJdbc implements BookRepository {

    private final GenreRepository genreRepository;

    private final NamedParameterJdbcOperations jdbcNamed;

    private final JdbcOperations jdbc;

    public BookRepositoryJdbc(NamedParameterJdbcOperations jdbcNamed, GenreRepository genreRepository) {
        this.jdbcNamed = jdbcNamed;
        this.jdbc = jdbcNamed.getJdbcOperations();
        this.genreRepository = genreRepository;
    }

    @Override
    public Optional<Book> findById(long id) {
        String sql = """
                SELECT b.id, b.title, b.author_id , a.full_name as author_full_name, bg.genre_id, g.name as genre_name
                FROM books AS b JOIN authors AS a ON b.author_id = a.id
                LEFT JOIN books_genres AS bg ON b.id = bg.book_id
                LEFT JOIN genres AS g ON g.id = bg.genre_id
                WHERE b.id = :id""";

        Map<String, Object> params = Map.of("id", id);
        var book = jdbcNamed.query(sql, params, new BookResultSetExtractor());

        if (book.isEmpty()) {
            throw new NotFoundException("Book with id " + id + " not found");
        }
        return book;
    }

    @Override
    public List<Book> findAll() {
        var genres = genreRepository.findAll();
        var relations = getAllGenreRelations();
        var books = getAllBooksWithoutGenres();
        mergeBooksInfo(books, genres, relations);
        return books;
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == null) {
            return insert(book);
        }
        return update(book);
    }

    @Override
    public void deleteById(long id) {
        String sql = """
                BEGIN TRANSACTION;
                DELETE FROM authors WHERE id IN (SELECT author_id FROM books WHERE id = :id); 
                DELETE FROM books WHERE id  = :id; 
                COMMIT;
                """;

        findById(id).ifPresent(book -> {
            Map<String, Object> params = Map.of("id", id, "authorId", book.getAuthor().getId());
            jdbcNamed.update(sql, params);
            removeGenresRelationsFor(book);
        });

    }

    private List<Book> getAllBooksWithoutGenres() {
        String sql = """
                SELECT b.id, b.title, b.author_id, a.full_name
                FROM books AS b LEFT JOIN authors AS a ON b.author_id = a.id 
                """;
        return jdbc.query(sql, new BookRepositoryJdbc.BookRowMapper()).stream().toList();
    }

    private List<BookGenreRelation> getAllGenreRelations() {
        return jdbc.query("SELECT * FROM books_genres",
                (rs, i) -> new BookGenreRelation(rs.getLong("book_id"), rs.getLong("genre_id")));
    }

    private void mergeBooksInfo(List<Book> booksWithoutGenres, List<Genre> genres,
                                List<BookGenreRelation> relations) {
        for (Book book : booksWithoutGenres) {
            book.getGenres().addAll(genres.stream()
                    .filter(g -> relations.stream()
                            .anyMatch(r -> r.bookId == book.getId() && r.genreId == g.getId()))
                    .toList());
        }
    }

    private Book insert(Book book) {
        var keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("title", book.getTitle());
        params.addValue("authorId", book.getAuthor().getId());
        jdbcNamed.update("INSERT INTO books (title, author_id) values (:title, :authorId)", params, keyHolder);
        book.setId(keyHolder.getKeyAs(Long.class));
        batchInsertGenresRelationsFor(book);
        return book;
    }

    private Book update(Book book) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", book.getId());
        params.addValue("title", book.getTitle());
        params.addValue("authorId", book.getAuthor().getId());
        removeGenresRelationsFor(book);

        jdbcNamed.update("UPDATE books SET title = :title, author_id = :authorId where id = :id", params);

        batchInsertGenresRelationsFor(book);

        return book;
    }

    private void batchInsertGenresRelationsFor(Book book) {
        jdbc.batchUpdate("INSERT INTO books_genres (book_id, genre_id) values (?,?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                        preparedStatement.setString(1, String.valueOf(book.getId()));
                        preparedStatement.setString(2, String.valueOf(book.getGenres().get(i).getId()));
                    }

                    @Override
                    public int getBatchSize() {
                        return book.getGenres().size();
                    }
                }
        );
    }

    private void removeGenresRelationsFor(Book book) {
        jdbc.update("DELETE FROM books_genres AS b WHERE b.book_id = ?", book.getId());
    }


    private static class BookRowMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Book.builder()
                    .id(rs.getLong("id"))
                    .title(rs.getString("title"))
                    .author(new Author(rs.getLong("author_id"), rs.getString("full_name")))
                    .genres(new ArrayList<>())
                    .build();
        }
    }

    @SuppressWarnings("ClassCanBeRecord")
    @RequiredArgsConstructor
    private static class BookResultSetExtractor implements ResultSetExtractor<Optional<Book>> {

        @Override
        public Optional<Book> extractData(ResultSet rs) throws SQLException, DataAccessException {
            Map<Long, Book> books = new HashMap<>();
            while (rs.next()) {
                long id = rs.getLong("id");
                Book book = books.get(id);
                if (book == null) {
                    book = Book.builder().id(id)
                            .title(rs.getString("title"))
                            .author(new Author(rs.getLong("author_id"),
                                    rs.getString("author_full_name")))
                            .genres(new ArrayList<>())
                            .build();
                    books.put(book.getId(), book);
                }
                if (rs.getString("genre_id") != null) {
                    book.getGenres().add(new Genre(rs.getLong("genre_id"),
                            rs.getString("genre_name")));
                }
            }
            if (books.size() == 0) {
                return Optional.empty();
            } else {
                return books.values().stream().findFirst();
            }
        }
    }

    private record BookGenreRelation(long bookId, long genreId) {
    }
}
