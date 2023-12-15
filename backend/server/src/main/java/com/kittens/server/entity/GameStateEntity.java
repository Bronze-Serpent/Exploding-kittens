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

    @OneToOne(cascade = {CascadeType.MERGE})
    private PlayerEntity nowTurn;

    @OneToOne(cascade = {CascadeType.MERGE})
    private CardReset cardReset;

    // TODO: 16.12.2023 будет работать CascadeType.ALL т.к. порядок сохранения при нём другой. А при Persist будет проблема
    //  т.к. gamestate сохранится раньше и у него поле card_deck_id будет null
    @OneToOne(cascade = {CascadeType.MERGE})
    private CardDeck cardDeck;
    
    @Builder.Default
    @JoinColumn(name = "game_state_id")
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<PlayerQueuePointer> playerQueuePointers = new ArrayList<>();

}
