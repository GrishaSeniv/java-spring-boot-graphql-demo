package com.example.demo.book;

import com.example.demo.domain.dto.AddBookReq;
import com.example.demo.domain.dto.Book;
import com.example.demo.domain.dto.BookDTO;
import com.example.demo.domain.entity.BookEntity;

import java.util.Collections;
import java.util.List;

/**
 * @author Hryhorii Seniv
 * @version 2024-10-31
 */
class Converter {
    static BookEntity toBookEntity(AddBookReq req) {
        return new BookEntity()
                .setGenre(req.genre())
                .setTitle(req.title());
    }

    static BookDTO toBookDTO(BookEntity entity) {
        return new BookDTO(entity.getId(), entity.getTitle(), entity.getGenre());
    }

    static List<Book> toBooks(List<BookDTO> dtos) {
        if (dtos == null) {
            return Collections.emptyList();
        }
        return dtos.stream()
                .map(Converter::toBook)
                .toList();
    }

    static Book toBook(BookDTO dto) {
        return new Book(dto.id(), dto.title(), dto.genre());
    }
}
