package com.kittens.server.entity;

import com.kittens.server.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "game_state")
public class GameStateEntity extends BaseEntity<Long>
{
    private int stepQuantity;

    @OneToOne
    private PlayerEntity nowTurn;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private CardReset cardReset;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private CardDeck cardDeck;
    
    @Builder.Default
    @JoinColumn(name = "game_state_id")
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<PlayerQueuePointer> playerQueuePointers = new ArrayList<>();

    @OneToOne
    private Room room;
}
