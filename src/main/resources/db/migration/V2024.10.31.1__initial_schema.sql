-- Create Author Table
CREATE TABLE author
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    bio  TEXT
);

-- Create Book Table
CREATE TABLE book
(
    id        BIGSERIAL PRIMARY KEY,
    title     VARCHAR(255) NOT NULL,
    genre     VARCHAR(100),
    author_id BIGSERIAL,
    FOREIGN KEY (author_id) REFERENCES author (id)
);

-- Sample Data for Testing
INSERT INTO author (name, bio)
VALUES ('George Orwell', 'Author of 1984 and Animal Farm'),
       ('J.K. Rowling', 'Author of the Harry Potter series'),
       ('J.R.R. Tolkien', 'Author of The Lord of the Rings');

INSERT INTO book (title, genre, author_id)
VALUES ('1984', 'Dystopian', 1),
       ('Animal Farm', 'Political Satire', 1),
       ('Harry Potter and the Philosopher''s Stone', 'Fantasy', 2),
       ('Harry Potter and the Chamber of Secrets', 'Fantasy', 2),
       ('The Hobbit', 'Fantasy', 3),
       ('The Lord of the Rings', 'Fantasy', 3);
