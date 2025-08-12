package com.banking.common.base.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import com.banking.common.base.entity.BaseEntity;
import com.banking.common.base.repository.BaseRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

/**
 * Base service class providing common CRUD operations. Implements standard patterns for entity
 * management.
 * 
 * @param <T> Entity type
 * @param <R> Repository type
 * @author Banking Platform Team
 * @since 1.0.0
 */
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BaseService<T extends BaseEntity, R extends BaseRepository<T>> {

    protected final R repository;

    /**
     * Finds entity by ID, throws exception if not found
     */
    public T findById(UUID id) {
        return repository.findActiveById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found with ID: " + id));
    }


    /**
     * Finds entity by ID, returns Optional
     */
    public Optional<T> findByIdOptional(UUID id) {
        return repository.findActiveById(id);
    }

    /**
     * Finds all active entities
     */
    public List<T> findAll() {
        return repository.findAllActive();
    }

    /**
     * Finds all entities with pagination
     */
    public Page<T> findAll(Pageable pageable) {
        return repository.findAllActive(pageable);
    }

    /**
     * Saves entity
     */
    @Transactional
    public T save(T entity) {
        return repository.save(entity);
    }

    /**
     * Saves multiple entities
     */
    @Transactional
    public List<T> saveAll(List<T> entities) {
        return repository.saveAll(entities);
    }

    /**
     * Soft deletes entity by ID
     */
    @Transactional
    public void deleteById(UUID id) {
        if (!repository.existsActiveById(id)) {
            throw new EntityNotFoundException(getEntityName(), NotFOundEx);
        }
        repository.softDeleteById(id);
    }

    /**
     * Returns the entity name for error messages
     */
    protected abstract String getEntityName();

}
