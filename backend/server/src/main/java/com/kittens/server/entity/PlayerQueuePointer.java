package com.kittens.server.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;


@Getter
@Entity
public class PlayerQueuePointer extends BaseEntity<Integer>
{
    @OneToOne
    private PlayerEntity pointingPlayer;

    @OneToOne
    private PlayerEntity pointedAtPlayer;

    @ManyToOne
    private GameStateEntity gameState;
}
