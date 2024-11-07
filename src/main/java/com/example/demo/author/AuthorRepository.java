package com.example.demo.author;

import com.example.demo.domain.entity.AuthorEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Hryhorii Seniv
 * @version 2024-10-31
 */
@Repository
interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {
    <T> List<T> findAllBy(Class<T> type, Pageable pageable);

    <T> T findById(Class<T> type, Long id);
}
