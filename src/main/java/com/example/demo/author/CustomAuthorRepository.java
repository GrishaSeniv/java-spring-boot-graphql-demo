package com.example.demo.author;

import com.example.demo.domain.dto.AuthorDTOProjection;

import java.util.List;
import java.util.Set;

/**
 * @author Hryhorii Seniv
 * @version 2024-11-07
 */
interface CustomAuthorRepository {
    List<AuthorDTOProjection> findAllWithFields(Integer limit, Integer offset, Set<String> fields);

    AuthorDTOProjection findByIdWithFields(Long id, Set<String> fields);
}
