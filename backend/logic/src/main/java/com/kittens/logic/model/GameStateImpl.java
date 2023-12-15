package com.kittens.logic.model;

import com.kittens.logic.card.Card;

import java.util.List;


public class GameStateImpl extends GameState
{

    public GameStateImpl(LoopingList<AbstractPlayer> playersTurn,
                         List<Card> cardDeck,
                         List<Card> cardReset,
                         AbstractPlayer nowTurn,
                         int stepQuantity)
    {
        super(playersTurn, cardDeck, cardReset, nowTurn, stepQuantity);
    }
}
