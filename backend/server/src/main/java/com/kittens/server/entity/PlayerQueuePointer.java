package com.kittens.server.entity;

import com.kittens.server.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;


@EqualsAndHashCode(of = "", callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class PlayerQueuePointer extends BaseEntity<Integer>
{
    @OneToOne
    private PlayerEntity pointingPlayer;

    @OneToOne
    private PlayerEntity pointedAtPlayer;

    @ManyToOne()
    private GameStateEntity gameState;

    @Override
    public String toString()
    {
        return "[" + pointingPlayer.getId() + " -> " + pointedAtPlayer.getId() + "]";
    }
}
