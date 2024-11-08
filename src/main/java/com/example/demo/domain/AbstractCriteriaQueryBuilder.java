package com.example.demo.domain;

import com.example.demo.domain.util.Pagination;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Selection;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Abstract base class for building criteria queries.
 *
 * @param <E> the type of the entity class that the query is built for.
 *            This represents the entity being queried in the database.
 * @author Hryhorii Seniv
 * @version 2024-10-31
 */
public abstract class AbstractCriteriaQueryBuilder<E> {
    private static final String ASSOCIATION_FIELD_DELIMITER = ".";
    private static final String ASSOCIATION_FIELD_REGEX = "\\.";

    public Object[] fetchSelectedFieldsByFieldId(EntityManager entityManager, Set<String> fields,
                                                 Class<E> entityClazz, Long id, String fieldName) {
        return buildSelectedFieldsQueryByFieldId(entityManager, fields, entityClazz, id, fieldName).getSingleResult();
    }

    public List<Object[]> fetchSelectedFieldsList(EntityManager entityManager, Set<String> fields,
                                                  Class<E> entityClazz, Pagination pagination) {
        return buildSelectedFieldsQueryList(entityManager, fields, entityClazz, pagination).getResultList();
    }

    public List<Object[]> fetchSelectedFieldsByFieldIds(EntityManager entityManager, Set<String> fields,
                                                        Class<E> entityClazz, Set<Long> ids, String fieldName) {
        return buildSelectedFieldsQueryByFieldIds(entityManager, fields, entityClazz, ids, fieldName).getResultList();
    }

    private TypedQuery<Object[]> buildSelectedFieldsQueryByFieldIds(EntityManager entityManager, Set<String> fields, Class<E> entityClazz,
                                                                    Set<Long> ids, String fieldName) {
        CriteriaQuery<Object[]> cq = buildCriteriaQueryByFieldIds(entityManager, fields, entityClazz, ids, fieldName);
        return entityManager.createQuery(cq);
    }

    private TypedQuery<Object[]> buildSelectedFieldsQueryByFieldId(EntityManager entityManager, Set<String> fields, Class<E> entityClazz,
                                                                   Long id, String fieldName) {
        CriteriaQuery<Object[]> cq = buildCriteriaQueryByFieldId(entityManager, fields, entityClazz, id, fieldName);
        return entityManager.createQuery(cq);
    }

    private TypedQuery<Object[]> buildSelectedFieldsQueryList(EntityManager entityManager, Set<String> fields,
                                                              Class<E> entityClazz, Pagination pagination) {
        CriteriaQuery<Object[]> cq = buildCriteriaQuery(entityManager, fields, entityClazz);
        TypedQuery<Object[]> query = entityManager.createQuery(cq);
        query.setFirstResult(pagination.getOffset())
                .setMaxResults(pagination.getLimit());
        return query;
    }

    private CriteriaQuery<Object[]> buildCriteriaQueryByFieldIds(EntityManager entityManager, Set<String> fields, Class<E> entityClazz,
                                                                 Set<Long> ids, String fieldName) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<E> root = cq.from(entityClazz);
        List<Selection<?>> selections = buildSelections(fields, root);
        cq.multiselect(selections);
        buildWhereClauseByFieldIds(cq, root, ids, fieldName);
        return cq;
    }

    private CriteriaQuery<Object[]> buildCriteriaQueryByFieldId(EntityManager entityManager, Set<String> fields, Class<E> entityClazz,
                                                                Long id, String fieldName) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<E> root = cq.from(entityClazz);
        List<Selection<?>> selections = buildSelections(fields, root);
        cq.multiselect(selections);
        buildWhereClauseByFieldId(cb, cq, root, id, fieldName);
        return cq;
    }

    private CriteriaQuery<Object[]> buildCriteriaQuery(EntityManager entityManager, Set<String> fields, Class<E> entityClazz) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<E> root = cq.from(entityClazz);
        List<Selection<?>> selections = buildSelections(fields, root);
        cq.multiselect(selections);
        return cq;
    }

    private List<Selection<?>> buildSelections(Set<String> fields, Root<E> root) {
        return fields.stream()
                .map(root::get)
                .collect(Collectors.toList());
    }

    private void buildWhereClauseByFieldIds(CriteriaQuery<Object[]> cq, Root<E> root, Set<Long> ids, String fieldName) {
        cq.where(getPath(root, fieldName).in(ids));
    }

    private void buildWhereClauseByFieldId(CriteriaBuilder cb, CriteriaQuery<Object[]> cq, Root<E> root, Long id, String fieldName) {
        cq.where(cb.equal(getPath(root, fieldName), id));
    }

    private Path<?> getPath(Root<E> root, String fieldName) {
        Path<?> path = root;
        if (fieldName.contains(ASSOCIATION_FIELD_DELIMITER)) {
            String[] fields = fieldName.split(ASSOCIATION_FIELD_REGEX);
            for (String field : fields) {
                path = path.get(field);
            }
            return path;
        }
        return root.get(fieldName);
    }
}
