package com.banking.common.base.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Base entity with optimistic locking for high-concurrency scenarios. Use this for critical
 * entities like accounts, transactions, balances.
 * 
 * @author Banking Platform Team
 * @since 1.0.0
 */
@Getter
@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
public class BaseVersionedEntity extends BaseAuditEntity {

    @Version
    @Column(name = "lock_version", nullable = false)
    private Long lockVersion = 0L;

    /**
     * Increments the version for manual version control
     */
    public void incrementVersion() {
        this.lockVersion = (this.lockVersion == null) ? 1L : this.lockVersion + 1L;
    }
}
