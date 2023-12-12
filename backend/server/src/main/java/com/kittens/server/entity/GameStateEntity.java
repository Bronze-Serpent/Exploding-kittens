package com.kittens.server.entity;

import com.kittens.server.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "game_state")
public class GameStateEntity extends BaseEntity<Long>
{
    private int stepQuantity;

    @OneToOne
    private PlayerEntity nowTurn;

    @OneToOne
    private CardReset cardReset;

    @OneToOne // будет работать CascadeType.ALL т.к. порядок сохранения при нём другой. А при Persist будет проблема
    // т.к. gamestate сохранится раньше и у него поле card_deck_id будет null
    private CardDeck cardDeck;

    @OneToMany(mappedBy = "gameState", cascade = CascadeType.PERSIST)
    private List<PlayerQueuePointer> playerQueuePointers = new ArrayList<>();
}
