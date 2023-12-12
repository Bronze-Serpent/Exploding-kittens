package com.kittens.server.user.entity;

import com.kittens.server.common.entity.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;


@Entity
@Table(name = "user_account")
@SuperBuilder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends AuditableEntity {

    @Column(name = "login")
    String login;

    @Column(name = "password_hash")
    String passwordHash;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    List<UserDynamicPassword> dynamicPasswords;
}
