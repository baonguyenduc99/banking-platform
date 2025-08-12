package com.banking.common.base.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import com.banking.common.base.entity.BaseEntity;

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity>
        extends JpaRepository<T, UUID>, JpaSpecificationExecutor<T>, QuerydslPredicateExecutor<T> {

    /**
     * Finds an active (non-deleted) entity by ID
     */
    @Query("SELECT e FROM #{#entityName} e WHERE e.id = :id AND e.deleted = false")
    Optional<T> findActiveById(@Param("id") UUID id);

    /**
     * Finds all active (non-deleted) entities
     */
    @Query("SELECT e FROM #{#entityName} e WHERE e.deleted = false")
    List<T> findAllActive();

    /**
     * Finds all active entities with pagination
     */
    @Query("SELECT e FROM #{#entityName} e WHERE e.deleted = false")
    Page<T> findAllActive(Pageable pageable);

    /**
     * Counts all active (non-deleted) entities
     */
    @Query("SELECT COUNT(e) FROM #{#entityName} e WHERE e.deleted = false")
    long countActive();

    /**
     * Soft deletes an entity by ID
     */
    @Modifying
    @Query("UPDATE #{#entityName} e SET e.deleted = true, e.deletedAt = CURRENT_TIMESTAMP WHERE e.id = :id")
    int softDeleteById(@Param("id") UUID id);

    /**
     * Restores a soft-deleted entity by ID
     */
    @Modifying
    @Query("UPDATE #{#entityName} e SET e.deleted = false, e.deletedAt = null WHERE e.id = :id")
    int restoreById(@Param("id") UUID id);

    /**
     * Checks if entity exists and is active
     */
    @Query("SELECT COUNT(e) > 0 FROM #{#entityName} e WHERE e.id = :id AND e.deleted = false")
    boolean existsActiveById(@Param("id") UUID id);
}