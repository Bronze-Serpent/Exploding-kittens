package com.kittens.server.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import org.hibernate.annotations.Type;


@Entity
public class CardDeck extends BaseEntity<Integer>
{
    @OneToOne
    private GameStateEntity gameState;

    @Type(value = JsonBinaryType.class)
    @Column()
    private String value;
}
