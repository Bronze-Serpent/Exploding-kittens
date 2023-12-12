package com.kittens.server.common.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@MappedSuperclass
@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED)
@SuperBuilder
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditableEntity extends BaseEntity<Long> {

    @Column(name = "created_date")
    @CreatedDate
    Instant createdDate;

    @Column(name = "modified_date")
    @LastModifiedDate
    Instant modifiedDate;
}
