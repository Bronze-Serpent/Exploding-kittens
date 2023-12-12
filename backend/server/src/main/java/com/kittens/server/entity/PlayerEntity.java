package com.kittens.server.entity;

import com.vladmihalcea.hibernate.type.array.IntArrayType;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;


@Entity
@NoArgsConstructor
@Table(name = "player")
public class PlayerEntity extends BaseEntity<Integer>
{
    @Type(type = "jsonb")
    private Integer[] cards;

    // TODO: 12.12.2023 добавить связь с user
}
