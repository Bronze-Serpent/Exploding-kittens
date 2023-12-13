package com.kittens.server.entity;

import com.kittens.server.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@EqualsAndHashCode(of = "", callSuper = true)
@NoArgsConstructor
@Getter
@Setter
@Entity
public class PlayerQueuePointer extends BaseEntity<Long>
{
    @OneToOne
    private PlayerEntity pointingPlayer;

    @OneToOne
    private PlayerEntity pointedAtPlayer;

    public PlayerQueuePointer(Long id, PlayerEntity pointingPlayer, PlayerEntity pointedAtPlayer)
    {
        super(id);
        this.pointingPlayer = pointingPlayer;
        this.pointedAtPlayer = pointedAtPlayer;
    }

    @Override
    public String toString()
    {
        return "[" + pointingPlayer.getId() + " -> " + pointedAtPlayer.getId() + "]";
    }
}
