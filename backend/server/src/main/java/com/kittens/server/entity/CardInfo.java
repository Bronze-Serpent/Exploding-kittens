package com.kittens.server.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;


@Getter
@Entity
public class CardInfo extends BaseEntity<Integer>
{
    @ManyToOne
    private CardEntity card;

    private Integer quantity;
}

