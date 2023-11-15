create table if not exists authors
(
    id        bigserial,
    full_name varchar(255),
    primary key (id)
);

create table if not exists genres
(
    id   bigserial,
    name varchar(255),
    primary key (id)
);

create table if not exists books
(
    id        bigserial,
    title     varchar(255),
    author_id bigint,
    primary key (id),
    FOREIGN KEY (author_id) REFERENCES authors (id) ON DELETE CASCADE
);

create table if not exists books_genres
(
    book_id  bigint,
    genre_id bigint,
    primary key (book_id, genre_id),
    FOREIGN KEY (book_id) REFERENCES books (id) ON DELETE CASCADE,
    FOREIGN KEY (genre_id) REFERENCES genres (id) ON DELETE CASCADE
);