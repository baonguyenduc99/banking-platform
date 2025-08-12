package com.banking.common.config.mapper;

import org.mapstruct.Builder;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.MapperConfig;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

/**
 * Global MapStruct configuration for all mappers across banking microservices.
 *
 * Usage:
 * 
 * @Mapper(config = CentralMappingConfig.class, uses = { CommonConverters.class }) public interface
 *                AccountMapper extends BaseMapper<Account, AccountDto> { ... }
 *
 *                Key behaviors: - Spring component model for DI (@Component mappers). - Constructor
 *                injection for safer, testable mappers. - Fail-fast on unmapped fields
 *                (ReportingPolicy.ERROR). - Null handling: * ALWAYS check for nulls before mapping.
 *                * Null collections map to empty collections. * Partial updates ignore null
 *                properties (won’t overwrite existing values). - Prefer "adder" for collection
 *                mapping on entities that expose addX(...) methods. - Disable builder support
 *                globally (avoid surprises unless you explicitly opt-in).
 */
@MapperConfig(componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_FROM_CONFIG,
        builder = @Builder(disableBuilder = true))
public interface CentralMappingConfig {

}
