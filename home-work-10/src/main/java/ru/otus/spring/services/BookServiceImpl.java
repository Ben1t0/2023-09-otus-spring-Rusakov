package ru.otus.spring.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.exceptions.NotFoundException;
import ru.otus.spring.mappers.BookMapper;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Genre;
import ru.otus.spring.repositories.AuthorRepository;
import ru.otus.spring.repositories.BookRepository;
import ru.otus.spring.repositories.GenreRepository;
import ru.otus.spring.rest.dto.BookCreateDto;
import ru.otus.spring.rest.dto.BookDto;
import ru.otus.spring.rest.dto.BookUpdateDto;

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
    public BookDto findById(long id) {
        return bookMapper.toDto(findByIdOrThrow(id));
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream().map(bookMapper::toDto).toList();
    }

    @Transactional
    @Override
    public BookDto create(BookCreateDto bookCreateDto) {
        var author = authorRepository.findById(bookCreateDto.authorId())
                .orElseThrow(() -> new NotFoundException("Author with id %d not found"
                        .formatted(bookCreateDto.authorId())));

        List<Genre> genres = genreRepository.findAllByIdIn(bookCreateDto.genreIds());
        if (isEmpty(genres) || genres.size() != bookCreateDto.genreIds().size()) {
            throw new NotFoundException("Genres with ids %s not found".formatted(bookCreateDto.genreIds()));
        }
        var book = bookMapper.toModel(bookCreateDto, author, genres);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Transactional
    @Override
    public BookDto update(BookUpdateDto bookUpdateDto) {
        bookRepository.findById(bookUpdateDto.id())
                .orElseThrow(() -> new NotFoundException("Book not found"));
        var author = authorRepository.findById(bookUpdateDto.authorId())
                .orElseThrow(() -> new NotFoundException("Author with id %d not found"
                        .formatted(bookUpdateDto.authorId())));
        List<Genre> genres = genreRepository.findAllByIdIn(bookUpdateDto.genreIds());
        if (isEmpty(genres) || genres.size() != bookUpdateDto.genreIds().size()) {
            throw new NotFoundException("Genres with ids %s not found".formatted(bookUpdateDto.genreIds()));
        }
        var book = bookMapper.toModel(bookUpdateDto, author, genres);
        return bookMapper.toDto(bookRepository.save(book));
    }

    private Book findByIdOrThrow(long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found"));
    }

    @Override
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }
}
