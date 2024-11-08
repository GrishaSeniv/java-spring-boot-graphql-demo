package com.example.demo.author;

import com.example.demo.domain.dto.AddAuthorReq;
import com.example.demo.domain.dto.AuthorDTO;
import com.example.demo.domain.dto.AuthorDTOProjection;
import com.example.demo.domain.entity.AuthorEntity;
import com.example.demo.domain.util.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static com.example.demo.author.Converter.toAuthorDTO;
import static com.example.demo.author.Converter.toAuthorEntity;

/**
 * @author Hryhorii Seniv
 * @version 2024-10-31
 */
@Service
class AuthorService {
    private static final Logger logger = LoggerFactory.getLogger(AuthorService.class);
    private final AuthorRepository repository;
    private final CustomAuthorRepository customRepository;

    AuthorService(AuthorRepository repository,
                  CustomAuthorRepository customRepository) {
        this.repository = repository;
        this.customRepository = customRepository;
    }

    /**
     * Retrieves a paginated list of authors with all fields included.
     * <p>
     * This method fetches a full set of AuthorDTOs, with the number of records limited by the pagination settings.
     * It is useful when you need to load the full entity and do not require a dynamic subset of fields.
     *
     * @param pagination the pagination details (limit and offset) to retrieve the records.
     * @return a list of AuthorDTOs.
     */
    public List<AuthorDTO> findAll(Pagination pagination) {
        int limit = pagination.getLimit();
        int offset = pagination.getOffset();
        logger.info("Get authors, limit: {}, offset: {}", limit, offset);
        return repository.findAllBy(AuthorDTO.class, PageRequest.of(offset / limit, limit));
    }

    /**
     * Retrieves a paginated list of authors with dynamically selected fields.
     * <p>
     * This method improves performance by only fetching the requested fields from the database,
     * reducing the amount of data retrieved and processed.
     * It's ideal when you only need a subset of the entity's fields
     * for performance reasons, especially with large datasets.
     *
     * @param pagination the pagination details (limit and offset) to retrieve the records.
     * @param fields     the set of fields to be retrieved for each author, improving performance by limiting the query.
     * @return a list of AuthorDTOProjection containing only the requested fields.
     */
    public List<AuthorDTOProjection> findAllWithFields(Pagination pagination, Set<String> fields) {
        int limit = pagination.getLimit();
        int offset = pagination.getOffset();
        logger.info("Get authors, limit: {}, offset: {}, with requested fields: {}", limit, offset, fields);
        return customRepository.findAllWithFields(limit, offset, fields);
    }

    /**
     * Retrieves a single author by its ID.
     * <p>
     * This method fetches the full AuthorDTO for a given author ID.
     * It is useful when you need the entire entity for a specific author.
     *
     * @param id the ID of the author to be fetched.
     * @return the AuthorDTO for the given author ID.
     */
    public AuthorDTO findById(Long id) {
        logger.info("Get author by id: {}", id);
        return repository.findById(AuthorDTO.class, id);
    }

    /**
     * Retrieves a single author by its ID with dynamically selected fields.
     * <p>
     * This method improves performance by only fetching the requested fields for the author from the database,
     * thus reducing the amount of data fetched.
     * It is particularly useful when you only need a subset of the author's details.
     *
     * @param id     the ID of the author to be fetched.
     * @param fields the set of fields to be retrieved for the author, improving performance by limiting the query.
     * @return an AuthorDTOProjection containing only the requested fields for the given author ID.
     */
    public AuthorDTOProjection findByIdWithFields(Long id, Set<String> fields) {
        logger.info("Get author by id: {} with requested fields: {}", id, fields);
        return customRepository.findByIdWithFields(id, fields);
    }

    public AuthorDTO save(AddAuthorReq req) {
        logger.info("Save author: {}", req);
        AuthorEntity saved = repository.save(toAuthorEntity(req));
        logger.info("Saved author: {}", saved);
        return toAuthorDTO(saved);
    }
}
