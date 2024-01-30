package ru.otus.spring.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.dto.BookCreateDto;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.BookUpdateDto;
import ru.otus.spring.exceptions.NotFoundException;
import ru.otus.spring.mappers.BookMapper;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Genre;
import ru.otus.spring.repositories.AuthorRepository;
import ru.otus.spring.repositories.BookRepository;
import ru.otus.spring.repositories.GenreRepository;

import java.util.List;

import static org.springframework.util.CollectionUtils.isEmpty;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    @Transactional(readOnly = true)
    @Override
    public BookUpdateDto findById(long id) {
        return bookMapper.toUpdateDto(bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found")));
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream().map(bookMapper::toDto).toList();
    }

    @Transactional
    @Override
    public BookDto create(BookCreateDto bookCreateDto) {
        var book = createBook(null, bookCreateDto.getTitle(),
                bookCreateDto.getAuthorId(), bookCreateDto.getGenreIds());

        return bookMapper.toDto(bookRepository.save(book));
    }

    @Transactional
    @Override
    public BookDto update(BookUpdateDto bookUpdateDto) {
        findById(bookUpdateDto.getId());
        var book = createBook(bookUpdateDto.getId(), bookUpdateDto.getTitle(),
                bookUpdateDto.getAuthorId(), bookUpdateDto.getGenreIds());

        return bookMapper.toDto(bookRepository.save(book));
    }

    private Book createBook(Long id, String title, long authorId, List<Long> genreIds) {
        var author = authorRepository.findById(authorId)
                .orElseThrow(
                        () -> new NotFoundException("Author with id %d not found"
                                .formatted(authorId)));
        List<Genre> genres = genreRepository.findAllByIdIn(genreIds);
        if (isEmpty(genres) || genres.size() != genreIds.size()) {
            throw new NotFoundException("Genres with ids %s not found".formatted(genreIds));
        }
        return new Book(id, title, author, genres);
    }

    @Override
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }
}
