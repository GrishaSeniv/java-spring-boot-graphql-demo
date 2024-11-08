package com.example.demo.book;

import com.example.demo.domain.dto.BookDTOProjection;

import java.util.List;
import java.util.Set;

/**
 * @author Hryhorii Seniv
 * @version 2024-11-07
 */
interface CustomBookRepository {
    List<BookDTOProjection> findAllWithFields(Integer limit, Integer offset, Set<String> fields);

    List<BookDTOProjection> findAllByAuthorIdsWithFields(Set<Long> authorIds, Set<String> fields);

    BookDTOProjection findByIdWithFields(Long id, Set<String> fields);
}
