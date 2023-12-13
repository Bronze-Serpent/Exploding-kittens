package com.kittens.server.entity;

import com.kittens.server.common.entity.BaseEntity;
import com.kittens.server.user.entity.User;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;


@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "", callSuper = true)
@Entity
@Table(name = "player")
public class PlayerEntity extends BaseEntity<Long>
{
    @Type(StringArrayType.class)
    private String[] cards;

    // TODO: 13.12.2023 добавить связь в User мб
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
