package com.banking.common.base.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import com.banking.common.base.entity.BaseEntity;

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity>
        extends JpaRepository<T, UUID>, JpaSpecificationExecutor<T>, QuerydslPredicateExecutor<T> {
    /**
     * Restores a previously soft-deleted entity by ID.
     * <p>
     * Note: Requires a separate query because @SQLDelete only applies to deletes.
     * </p>
     *
     * @param id entity ID
     * @return number of rows updated (0 if not found or already active)
     */
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @org.springframework.data.jpa.repository.Query("""
                UPDATE #{#entityName} e
                   SET e.deleted = false,
                       e.deletedAt = null
                 WHERE e.id = :id
                   AND e.deleted = true
            """)
    int restoreById(@Param("id") UUID id);
}
