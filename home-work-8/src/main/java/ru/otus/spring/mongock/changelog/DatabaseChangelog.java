package ru.otus.spring.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.spring.exceptions.NotFoundException;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Comment;
import ru.otus.spring.models.Genre;
import ru.otus.spring.repositories.AuthorRepository;
import ru.otus.spring.repositories.BookRepository;
import ru.otus.spring.repositories.CommentRepository;
import ru.otus.spring.repositories.GenreRepository;

import java.util.List;

@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "dropDb", runAlways = true, author = "g")
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertAuthors", author = "g")
    public void insertAuthors(AuthorRepository authorRepository) {
        authorRepository.save(Author.builder().id("1").fullName("Author_1").build());
        authorRepository.save(Author.builder().id("2").fullName("Author_2").build());
        authorRepository.save(Author.builder().id("3").fullName("Author_3").build());
    }

    @ChangeSet(order = "003", id = "insertGenres", author = "g")
    public void insertGenres(GenreRepository genreRepository) {
        genreRepository.save(Genre.builder().id("1").name("Genre_1").build());
        genreRepository.save(Genre.builder().id("2").name("Genre_2").build());
        genreRepository.save(Genre.builder().id("3").name("Genre_3").build());
        genreRepository.save(Genre.builder().id("4").name("Genre_4").build());
        genreRepository.save(Genre.builder().id("5").name("Genre_5").build());
        genreRepository.save(Genre.builder().id("6").name("Genre_6").build());
    }

    @ChangeSet(order = "004", id = "insertBooks", author = "g")
    public void insertBooks(BookRepository bookRepository, AuthorRepository authorRepository,
                            GenreRepository genreRepository) {
        var a1 = authorRepository.findById("1")
                .orElseThrow(() -> new NotFoundException("author_1 not found"));
        var a2 = authorRepository.findById("2")
                .orElseThrow(() -> new NotFoundException("author_2 not found"));
        var a3 = authorRepository.findById("3")
                .orElseThrow(() -> new NotFoundException("author_3 not found"));

        var g1 = genreRepository.findById("1")
                .orElseThrow(() -> new NotFoundException("Genre_1 not found"));
        var g2 = genreRepository.findById("2")
                .orElseThrow(() -> new NotFoundException("Genre_2 not found"));
        var g3 = genreRepository.findById("3")
                .orElseThrow(() -> new NotFoundException("Genre_3 not found"));
        var g4 = genreRepository.findById("4")
                .orElseThrow(() -> new NotFoundException("Genre_4 not found"));
        var g5 = genreRepository.findById("5")
                .orElseThrow(() -> new NotFoundException("Genre_5 not found"));
        var g6 = genreRepository.findById("6")
                .orElseThrow(() -> new NotFoundException("Genre_6 not found"));

        bookRepository.save(Book.builder().id("1").title("BookTitle_1").author(a1).genres(List.of(g1, g2)).build());
        bookRepository.save(Book.builder().id("2").title("BookTitle_2").author(a2).genres(List.of(g3, g4)).build());
        bookRepository.save(Book.builder().id("3").title("BookTitle_3").author(a3).genres(List.of(g5, g6)).build());
    }

    @ChangeSet(order = "005", id = "insertComments", author = "g")
    public void insertComments(CommentRepository commentRepository, BookRepository bookRepository) {
        var b1 = bookRepository.findById("1")
                .orElseThrow(() -> new NotFoundException("BookTitle_1 not found"));
        var b2 = bookRepository.findById("2")
                .orElseThrow(() -> new NotFoundException("BookTitle_2 not found"));
        var b3 = bookRepository.findById("3")
                .orElseThrow(() -> new NotFoundException("BookTitle_3 not found"));

        commentRepository.save(Comment.builder().id("1").book(b1).message("Comment_1_for_book_1").build());
        commentRepository.save(Comment.builder().id("2").book(b1).message("Comment_2_for_book_1").build());
        commentRepository.save(Comment.builder().id("3").book(b2).message("Comment_1_for_book_2").build());
        commentRepository.save(Comment.builder().id("4").book(b3).message("Comment_1_for_book_3").build());
    }
}
