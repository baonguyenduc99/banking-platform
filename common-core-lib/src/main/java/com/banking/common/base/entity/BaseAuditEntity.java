package com.banking.common.base.entity;

import java.util.UUID;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import com.banking.common.annotation.audit.SensitiveData;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Extended base entity with audit tracking for created/modified by user. Use this for entities that
 * need to track who performed changes.
 * 
 * @author Banking Platform Team
 * @since 1.0.0
 */
@Getter
@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
public class BaseAuditEntity extends BaseEntity {

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    @SensitiveData(type = com.banking.common.annotation.audit.MaskingType.PARTIAL)
    @Setter(AccessLevel.NONE)
    private UUID createdBy;

    @LastModifiedBy
    @Column(name = "updated_by")
    @SensitiveData(type = com.banking.common.annotation.audit.MaskingType.PARTIAL)
    @Setter(AccessLevel.NONE)
    private UUID updatedBy;

    @Column(name = "deleted_by")
    @SensitiveData(type = com.banking.common.annotation.audit.MaskingType.PARTIAL)
    private UUID deletedBy;

    /**
     * Sets the user who deleted this entity
     */
    public void setDeletedBy(UUID userId) {
        this.deletedBy = userId;
        markAsDeleted();
    }
}
