package com.example.demo.book;

import com.example.demo.domain.entity.BookEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @author Hryhorii Seniv
 * @version 2024-10-31
 */
@Repository
interface BookRepository extends JpaRepository<BookEntity, Long> {
    <T> List<T> findAllBy(Class<T> type, Pageable pageable);

    <T> T findById(Class<T> type, Long id);

    <T> List<T> findAllByAuthorId(Class<T> type, Long authorId);

    <T> List<T> findAllByAuthorIdIn(Class<T> type, Set<Long> authorIds);
}
