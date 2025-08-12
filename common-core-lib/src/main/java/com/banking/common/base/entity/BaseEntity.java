package com.banking.common.base.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.banking.common.annotation.audit.MaskingType;
import com.banking.common.annotation.audit.SensitiveData;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

/**
 * Base entity class providing common fields for all domain entities.
 * Includes ID, timestamps, and audit tracking capabilities.
 * 
 * @author BaoND
 * @since 1.0.0
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Instant updatedAt;

    @Column(name = "version")
    @Version
    private Long version;

    @Column(name = "deleted_at")
    @SensitiveData(type = MaskingType.PARTIAL)
    private Instant deletedAt;

    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;

    /**
     * Marks this entity as deleted (soft delete)
     */
    public void markAsDeleted() {
        this.deleted = true;
        this.deletedAt = Instant.now();
    }

    /**
     * Checks if this entity is active (not deleted)
     */
    public boolean isActive() {
        return !deleted;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        BaseEntity that = (BaseEntity) obj;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
