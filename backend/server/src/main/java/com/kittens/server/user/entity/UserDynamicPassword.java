package com.kittens.server.user.entity;

import com.kittens.server.common.entity.AuditableEntity;
import com.vladmihalcea.hibernate.type.array.IntArrayType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "user_dynamic_password")
@SuperBuilder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDynamicPassword extends AuditableEntity {

    @Type(IntArrayType.class)
    @Column(name = "enter_durations", columnDefinition = "int[]")
    Integer[] enterDurations;

    @JoinColumn(name = "user_account_id")
    @ManyToOne
    User user;
}
