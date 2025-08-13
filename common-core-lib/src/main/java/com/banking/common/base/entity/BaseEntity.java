package com.banking.common.base.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;

import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AccessLevel;

/**
 * Base entity class providing common fields for all domain entities. Includes ID, timestamps, and
 * audit tracking capabilities.
 * 
 * @author BaoND
 * @since 1.0.0
 */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {


    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID id;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    @Setter(AccessLevel.NONE)
    private Instant updatedAt;

    @Column(name = "version", nullable = false)
    @Version
    @Setter(AccessLevel.NONE)
    private Long version;

    @Column(name = "deleted_at", nullable = true)
    @Setter(AccessLevel.NONE)
    private Instant deletedAt;

    @Column(name = "deleted", nullable = false)
    @Setter(AccessLevel.NONE)
    private boolean deleted = false;

    /**
     * Marks this entity as deleted (soft delete)
     */
    public void markAsDeleted() {
        this.deleted = true;
        this.deletedAt = Instant.now();
    }

    /** Optional: restore */
    public void restore() {
        this.deleted = false;
        this.deletedAt = null;
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
        return Hibernate.getClass(this).hashCode();
    }
}
