package org.collabtask.helpers;

import java.time.ZonedDateTime;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class BaseEntity extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected String id;
    protected ZonedDateTime createdAt;
    protected ZonedDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = ZonedDateTimeHelper.now();
        updatedAt = ZonedDateTimeHelper.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = ZonedDateTimeHelper.now();
    }

}
