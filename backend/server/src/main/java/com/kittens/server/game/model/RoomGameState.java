package com.kittens.server.game.model;

import com.kittens.logic.card.Card;
import com.kittens.logic.model.AbstractPlayer;
import com.kittens.logic.model.GameState;
import com.kittens.logic.model.LoopingList;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;


@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
public class RoomGameState extends GameState

{
    private final Long id;

    public RoomGameState(LoopingList<AbstractPlayer> playersTurn,
                         List<Card> cardDeck, List<Card> cardReset,
                         int stepQuantity,
                         Long id)
    {
        super(playersTurn, cardDeck, cardReset, stepQuantity);
        this.id = id;
    }
}
