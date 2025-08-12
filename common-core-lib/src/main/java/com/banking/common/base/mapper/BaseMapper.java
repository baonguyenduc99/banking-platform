package com.banking.common.base.mapper;

import java.util.List;

import org.mapstruct.MappingTarget;

/**
 * Base mapper interface for entity-DTO conversions. Provides standard mapping methods that all
 * mappers should implement.
 * 
 * @param <E> Entity type
 * @param <D> DTO type
 * @author Banking Platform Team
 * @since 1.0.0
 */
public interface BaseMapper<E, D> {

    /**
     * Converts entity to DTO
     */
    D toDto(E entity);

    /**
     * Converts DTO to entity
     */
    E toEntity(D dto);

    /**
     * Converts list of entities to list of DTOs
     */
    List<D> toDtoList(List<E> entities);

    /**
     * Converts list of DTOs to list of entities
     */
    List<E> toEntityList(List<D> dtos);

    /**
     * Updates existing entity with DTO data
     */
    void updateEntityFromDto(D dto, @MappingTarget E entity);

    /**
     * Partial update - only non-null fields from DTO
     */
    void partialUpdate(D dto, @MappingTarget E entity);
}


