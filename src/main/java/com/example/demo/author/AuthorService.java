package com.example.demo.author;

import com.example.demo.domain.dto.AddAuthorReq;
import com.example.demo.domain.dto.AuthorDTO;
import com.example.demo.domain.entity.AuthorEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.author.Converter.toAuthorDTO;
import static com.example.demo.author.Converter.toAuthorEntity;
import static com.example.demo.domain.Constants.DEFAULT_LIMIT;
import static com.example.demo.domain.Constants.DEFAULT_OFFSET;

/**
 * @author Hryhorii Seniv
 * @version 2024-10-31
 */
@Service
class AuthorService {
    private static final Logger logger = LoggerFactory.getLogger(AuthorService.class);
    private final AuthorRepository repository;

    AuthorService(AuthorRepository repository) {
        this.repository = repository;
    }

    public List<AuthorDTO> findAll(Integer limit, Integer offset) {
        if (limit == null || limit == 0) {
            limit = DEFAULT_LIMIT;
        }
        if (offset == null || offset < 0) {
            offset = DEFAULT_OFFSET;
        }
        logger.info("Get authors, limit: {}, offset: {}", limit, offset);
        return repository.findAllBy(AuthorDTO.class, PageRequest.of(offset / limit, limit));
    }

    public AuthorDTO findById(Long id) {
        logger.info("Get author by id: {}", id);
        return repository.findById(AuthorDTO.class, id);
    }

    public AuthorDTO save(AddAuthorReq req) {
        logger.info("Save author: {}", req);
        AuthorEntity saved = repository.save(toAuthorEntity(req));
        logger.info("Saved author: {}", saved);
        return toAuthorDTO(saved);
    }
}
