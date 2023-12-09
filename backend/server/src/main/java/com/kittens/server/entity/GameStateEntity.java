package com.kittens.server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


// TODO: 09.12.2023 проблема с equals & hashcode и toString.
//  Поверх чего их делать, если по id, как по полям с маппингом сущностей не лучшая идея

@Getter
@Entity
@NoArgsConstructor
@Table(name = "game_state")
public class GameStateEntity extends BaseEntity<Integer>
{
    private int stepQuantity;

    @OneToOne
    private PlayerEntity nowTurn;


    // TODO: 09.12.2023 ну или попробовать сделать card_deck и card_info тоже entity
    @OneToMany
    @JoinTable(name = "card_deck",
            joinColumns = @JoinColumn(name = "game_state_id"),
            inverseJoinColumns = @JoinColumn(name = "card_info_id"))
    private List<CardInfo> cardDeck = new ArrayList<>();


    @OneToMany
    @JoinTable(name = "card_reset",
            joinColumns = @JoinColumn(name = "game_state_id"),
            inverseJoinColumns = @JoinColumn(name = "card_info_id"))
    private List<CardInfo> cardReset = new ArrayList<>();


    @OneToMany(mappedBy = "gameState")
    private List<PlayerQueuePointer> playerQueuePointers = new ArrayList<>();
}
