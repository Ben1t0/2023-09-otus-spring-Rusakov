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
    public Book insert(BookCreateDto bookCreateDto) {
        return save(null, bookCreateDto.title(), bookCreateDto.authorId(), bookCreateDto.genreIds());
    }

    @Override
    public Book update(BookCreateDto bookCreateDto) {
        findById(bookCreateDto.id());
        return save(bookCreateDto.id(), bookCreateDto.title(), bookCreateDto.authorId(), bookCreateDto.genreIds());
    }

    @Override
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }

    private Book save(Long id, String title, long authorId, List<Long> genresIds) {
        var author = authorRepository.findById(authorId)
                .orElseThrow(() -> new NotFoundException("Author with id %d not found".formatted(authorId)));
        List<Genre> genres = genreRepository.findAllByIds(genresIds);
        if (isEmpty(genres) || genres.size() != genresIds.size()) {
            throw new NotFoundException("Genres with ids %s not found".formatted(genresIds));
        }
        var book = new Book(id, title, author, genres);
        return bookRepository.save(book);
    }
}
