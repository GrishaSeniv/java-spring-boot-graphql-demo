package com.example.demo.book;

import com.example.demo.domain.dto.AddBookReq;
import com.example.demo.domain.dto.Book;
import com.example.demo.domain.dto.BookDTOProjection;
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

    static BookDTOProjection toBookDTO(BookEntity entity) {
        return new BookDTOProjection(entity.getId(), entity.getTitle(), entity.getGenre());
    }

    static List<Book> toBooks(List<BookDTOProjection> dtos) {
        if (dtos == null) {
            return Collections.emptyList();
        }
        return dtos.stream()
                .map(Converter::toBook)
                .toList();
    }

    static Book toBook(BookDTOProjection dto) {
        return new Book(dto.getId(), dto.getTitle(), dto.getGenre());
    }
}
