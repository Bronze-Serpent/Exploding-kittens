package com.kittens.server.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import org.hibernate.annotations.Type;


@Entity
public class CardReset extends BaseEntity<Integer>
{
    @OneToOne
    private GameStateEntity gameState;

    @Type(value = JsonBinaryType.class)
    private String value;
}
