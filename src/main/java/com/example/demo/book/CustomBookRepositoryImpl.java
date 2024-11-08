package com.example.demo.book;

import com.example.demo.domain.AbstractCriteriaQueryBuilder;
import com.example.demo.domain.dto.BookDTOProjection;
import com.example.demo.domain.entity.BookEntity;
import com.example.demo.domain.util.Pagination;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

import static com.example.demo.domain.Constants.BOOK_AUTHOR_ID_FIELD_NAME;
import static com.example.demo.domain.Constants.BOOK_ID_FIELD_NAME;
import static com.example.demo.domain.util.Mapper.mapToDTOWithReflection;
import static com.example.demo.domain.util.Mapper.mapToDTOWithReflectionList;

/**
 * @author Hryhorii Seniv
 * @version 2024-11-07
 */
@Repository
class CustomBookRepositoryImpl extends AbstractCriteriaQueryBuilder<BookEntity> implements CustomBookRepository {
    private static final Logger logger = LoggerFactory.getLogger(CustomBookRepositoryImpl.class);
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<BookDTOProjection> findAllWithFields(Integer limit, Integer offset, Set<String> fields) {
        List<Object[]> resultList = fetchSelectedFieldsList(entityManager, fields, BookEntity.class, new Pagination(offset, limit));
        return mapToDTOWithReflectionList(resultList, fields, BookDTOProjection.class);
    }

    @Override
    public List<BookDTOProjection> findAllByAuthorIdsWithFields(Set<Long> authorIds, Set<String> fields) {
        List<Object[]> resultList = fetchSelectedFieldsByFieldIds(entityManager, fields, BookEntity.class, authorIds, BOOK_AUTHOR_ID_FIELD_NAME);
        return mapToDTOWithReflectionList(resultList, fields, BookDTOProjection.class);
    }

    @Override
    public BookDTOProjection findByIdWithFields(Long id, Set<String> fields) {
        Object[] singleResult = fetchSelectedFieldsByFieldId(entityManager, fields, BookEntity.class, id, BOOK_ID_FIELD_NAME);
        return mapToDTOWithReflection(singleResult, fields, BookDTOProjection.class);
    }
}
