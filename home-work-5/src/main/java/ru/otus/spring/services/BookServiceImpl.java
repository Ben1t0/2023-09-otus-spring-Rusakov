package ru.otus.spring.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.dto.BookCreateDto;
import ru.otus.spring.exceptions.NotFoundException;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Genre;
import ru.otus.spring.repositories.AuthorRepository;
import ru.otus.spring.repositories.BookRepository;
import ru.otus.spring.repositories.GenreRepository;

import java.util.List;
import java.util.Optional;

import static org.springframework.util.CollectionUtils.isEmpty;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookRepository bookRepository;

    @Override
    public Optional<Book> findById(long id) {
        return bookRepository.findById(id);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book create(BookCreateDto bookCreateDto) {
        return save(bookCreateDto);
    }

    @Override
    public Book update(BookCreateDto bookCreateDto) {
        bookRepository.findById(bookCreateDto.id());
        return save(bookCreateDto);
    }

    @Override
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }

    private Book save(BookCreateDto bookCreateDto) {
        var author = authorRepository.findById(bookCreateDto.authorId())
                .orElseThrow(
                        () -> new NotFoundException("Author with id %d not found".formatted(bookCreateDto.authorId())));
        List<Genre> genres = genreRepository.findAllByIds(bookCreateDto.genreIds());
        if (isEmpty(genres) || genres.size() != bookCreateDto.genreIds().size()) {
            throw new NotFoundException("Genres with ids %s not found".formatted(bookCreateDto.genreIds()));
        }
        var book = new Book(bookCreateDto.id(), bookCreateDto.title(), author, genres);
        return bookRepository.save(book);
    }
}
