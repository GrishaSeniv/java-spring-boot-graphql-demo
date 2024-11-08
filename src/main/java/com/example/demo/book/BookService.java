package com.example.demo.book;

import com.example.demo.domain.dto.AddBookReq;
import com.example.demo.domain.dto.BookDTO;
import com.example.demo.domain.dto.BookDTOProjection;
import com.example.demo.domain.entity.BookEntity;
import com.example.demo.domain.util.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static com.example.demo.book.Converter.toBookDTO;
import static com.example.demo.book.Converter.toBookEntity;

/**
 * @author Hryhorii Seniv
 * @version 2024-10-31
 */
@Service
class BookService {
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);
    private final BookRepository repository;
    private final CustomBookRepository customRepository;

    BookService(BookRepository repository,
                CustomBookRepository customRepository) {
        this.repository = repository;
        this.customRepository = customRepository;
    }

    /**
     * Retrieves a paginated list of books with all fields included.
     * <p>
     * This method fetches a full set of BookDTOs, with the number of records limited by the pagination settings.
     * It is useful when you need to load the full entity and do not require a dynamic subset of fields.
     *
     * @param pagination the pagination details (limit and offset) to retrieve the records.
     * @return a list of BookDTOs.
     */
    public List<BookDTO> findAll(Pagination pagination) {
        int limit = pagination.getLimit();
        int offset = pagination.getOffset();
        logger.info("Get books, limit: {}, offset: {}", limit, offset);
        return repository.findAllBy(BookDTO.class, PageRequest.of(offset / limit, limit));
    }

    /**
     * Retrieves a paginated list of books with dynamically selected fields.
     * <p>
     * This method improves performance by only fetching the requested fields from the database,
     * reducing the amount of data retrieved and processed.
     * It's ideal when you only need a subset of the entity's fields
     * for performance reasons, especially with large datasets.
     *
     * @param pagination     the pagination details (limit and offset) to retrieve the records.
     * @param selectedFields the set of fields to be retrieved for each book, improving performance by limiting the query.
     * @return a list of BookDTOProjection containing only the requested fields.
     */
    public List<BookDTOProjection> findAllWithFields(Pagination pagination, Set<String> selectedFields) {
        int limit = pagination.getLimit();
        int offset = pagination.getOffset();
        logger.info("Get books, limit: {}, offset: {} with requested fields: {}", limit, offset, selectedFields);
        return customRepository.findAllWithFields(limit, offset, selectedFields);
    }

    /**
     * Retrieves a single book by its ID.
     * <p>
     * This method fetches the full BookDTO for a given author ID.
     * It is useful when you need the entire entity for a specific author.
     *
     * @param id the ID of the author to be fetched.
     * @return the BookDTO for the given author ID.
     */
    public BookDTO findById(Long id) {
        logger.info("Get book by id: {}", id);
        return repository.findById(BookDTO.class, id);
    }

    /**
     * Retrieves a single books by its ID with dynamically selected fields.
     * <p>
     * This method improves performance by only fetching the requested fields for the book from the database,
     * thus reducing the amount of data fetched.
     * It is particularly useful when you only need a subset of the author's details.
     *
     * @param id             the ID of the book to be fetched.
     * @param selectedFields the set of fields to be retrieved for the author, improving performance by limiting the query.
     * @return an BookDTOProjection containing only the requested fields for the given book ID.
     */
    public BookDTOProjection findByIdWithFields(Long id, Set<String> selectedFields) {
        logger.info("Get book by id: {} with requested fields: {}", id, selectedFields);
        return customRepository.findByIdWithFields(id, selectedFields);
    }

    /**
     * Retrieves a list of books with all fields included by authorIds
     * <p>
     * This method fetches a full set of BookDTOs
     * It is useful when you need to load the full entity and does not require a dynamic subset of fields.
     *
     * @param authorIds the IDs of the authors to be fetched.
     * @return a list of BookDTOs.
     */
    public List<BookDTO> findAllByAuthorIds(Set<Long> authorIds) {
        logger.info("Get books by authorIds: {}", authorIds);
        return repository.findAllByAuthorIdIn(BookDTO.class, authorIds);
    }

    /**
     * Retrieves a list of books with dynamically selected fields by authorIds
     * <p>
     * This method improves performance by only fetching the requested fields from the database,
     * reducing the amount of data retrieved and processed.
     * It's ideal when you only need a subset of the entity's fields
     * for performance reasons, especially with large datasets.
     *
     * @param authorIds      the IDs of the authors to be fetched.
     * @param selectedFields the set of fields to be retrieved for each book, improving performance by limiting the query.
     * @return a list of BookDTOProjection containing only the requested fields.
     */
    public List<BookDTOProjection> findAllByAuthorIdsWithFields(Set<Long> authorIds, Set<String> selectedFields) {
        logger.info("Get books by authorIds: {} with requested fields: {}", authorIds, selectedFields);
        return customRepository.findAllByAuthorIdsWithFields(authorIds, selectedFields);
    }

    public List<BookDTO> findAllByAuthorId(Long authorId) {
        logger.info("Get books by authorId: {}", authorId);
        return repository.findAllByAuthorId(BookDTO.class, authorId);
    }

    public BookDTOProjection save(AddBookReq req) {
        logger.info("Save book: {}", req);
        BookEntity saved = repository.save(toBookEntity(req));
        logger.info("Saved book: {}", saved);
        return toBookDTO(saved);
    }
}
