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

    @OneToOne
    private CardReset cardReset;

    @OneToOne // будет работать CascadeType.ALL т.к. порядок сохранения при нём другой. А при Persist будет проблема
    // т.к. gamestate сохранится раньше и у него поле card_deck_id будет null
    private CardDeck cardDeck;

    // TODO: 13.12.2023 уточнить про orphanRemoval
    @Builder.Default
    @JoinColumn(name = "game_state_id")
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<PlayerQueuePointer> playerQueuePointers = new ArrayList<>();

}
