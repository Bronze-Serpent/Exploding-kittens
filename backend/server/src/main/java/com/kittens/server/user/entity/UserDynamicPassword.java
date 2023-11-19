package com.kittens.server.user.entity;

import com.kittens.server.common.entity.AbstractEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "user_dynamic_password")
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDynamicPassword extends AbstractEntity {

    @Column(name = "enter_durations")
    @ElementCollection
    List<Integer> enterDurations;

    @JoinColumn(name = "user_id")
    @ManyToOne
    User user;
}
