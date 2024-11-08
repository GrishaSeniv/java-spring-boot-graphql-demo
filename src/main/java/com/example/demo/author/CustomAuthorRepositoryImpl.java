package com.example.demo.author;

import com.example.demo.domain.AbstractCriteriaQueryBuilder;
import com.example.demo.domain.dto.AuthorDTOProjection;
import com.example.demo.domain.entity.AuthorEntity;
import com.example.demo.domain.util.Pagination;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

import static com.example.demo.domain.Constants.AUTHOR_ID_FIELD_NAME;
import static com.example.demo.domain.util.Mapper.mapToDTOWithReflection;
import static com.example.demo.domain.util.Mapper.mapToDTOWithReflectionList;

/**
 * @author Hryhorii Seniv
 * @version 2024-11-07
 */
@Repository
class CustomAuthorRepositoryImpl extends AbstractCriteriaQueryBuilder<AuthorEntity> implements CustomAuthorRepository {
    private static final Logger logger = LoggerFactory.getLogger(CustomAuthorRepositoryImpl.class);
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<AuthorDTOProjection> findAllWithFields(Integer limit, Integer offset, Set<String> fields) {
        List<Object[]> resultList = fetchSelectedFieldsList(entityManager, fields, AuthorEntity.class, new Pagination(offset, limit));
        return mapToDTOWithReflectionList(resultList, fields, AuthorDTOProjection.class);
    }

    @Override
    public AuthorDTOProjection findByIdWithFields(Long id, Set<String> fields) {
        Object[] singleResult = fetchSelectedFieldsByFieldId(entityManager, fields, AuthorEntity.class, id, AUTHOR_ID_FIELD_NAME);
        return mapToDTOWithReflection(singleResult, fields, AuthorDTOProjection.class);
    }
}
