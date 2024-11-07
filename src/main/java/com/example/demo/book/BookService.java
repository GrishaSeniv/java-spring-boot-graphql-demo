package com.example.demo.book;

import com.example.demo.domain.dto.AddBookReq;
import com.example.demo.domain.dto.BookDTO;
import com.example.demo.domain.entity.BookEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static com.example.demo.book.Converter.toBookDTO;
import static com.example.demo.book.Converter.toBookEntity;
import static com.example.demo.domain.Constants.DEFAULT_LIMIT;
import static com.example.demo.domain.Constants.DEFAULT_OFFSET;

/**
 * @author Hryhorii Seniv
 * @version 2024-10-31
 */
@Service
class BookService {
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);
    private final BookRepository repository;

    BookService(BookRepository repository) {
        this.repository = repository;
    }

    public List<BookDTO> findAll(Integer limit, Integer offset) {
        if (limit == null || limit == 0) {
            limit = DEFAULT_LIMIT;
        }
        if (offset == null || offset < 0) {
            offset = DEFAULT_OFFSET;
        }
        logger.info("Get books, limit: {}, offset: {}", limit, offset);
        return repository.findAllBy(BookDTO.class, PageRequest.of(offset / limit, limit));
    }

    public BookDTO findById(Long id) {
        logger.info("Get book by id: {}", id);
        return repository.findById(BookDTO.class, id);
    }

    public List<BookDTO> findAllByAuthorIds(Set<Long> authorIds) {
        logger.info("Get books by authorIds: {}", authorIds);
        return repository.findAllByAuthorIdIn(BookDTO.class, authorIds);
    }

    public List<BookDTO> findAllByAuthorId(Long authorId) {
        logger.info("Get books by authorId: {}", authorId);
        return repository.findAllByAuthorId(BookDTO.class, authorId);
    }

    public BookDTO save(AddBookReq req) {
        logger.info("Save book: {}", req);
        BookEntity saved = repository.save(toBookEntity(req));
        logger.info("Saved book: {}", saved);
        return toBookDTO(saved);
    }
}
