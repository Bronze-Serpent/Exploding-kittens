package com.kittens.server.game.model;

import com.kittens.logic.card.Card;
import com.kittens.logic.model.AbstractPlayer;
import com.kittens.logic.model.GameState;
import com.kittens.logic.model.LoopingList;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;


@EqualsAndHashCode(of = "id", callSuper = false)
public class DbRefGameState extends GameState

{
    @Getter
    private final Long id;

    public DbRefGameState(LoopingList<AbstractPlayer> playersTurn,
                          List<Card> cardDeck, List<Card> cardReset,
                          AbstractPlayer nowTurn,
                          int stepQuantity,
                          Long id)
    {
        super(playersTurn, cardDeck, cardReset, nowTurn, stepQuantity);
        this.id = id;
    }
}