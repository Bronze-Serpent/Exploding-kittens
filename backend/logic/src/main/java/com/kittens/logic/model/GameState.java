package com.kittens.logic.model;

import com.kittens.logic.card.Card;
import lombok.*;

import java.util.Collection;
import java.util.List;


@ToString
@EqualsAndHashCode
@Setter
@Getter
public abstract class GameState
{
    private LoopingList<AbstractPlayer> playersTurn;
    private List<Card> cardDeck;
    private List<Card> cardReset;
    private int stepQuantity;

    public GameState(LoopingList<AbstractPlayer> playersTurn,
                     List<Card> cardDeck,
                     List<Card> cardReset,
                     int stepQuantity)
    {
        this.playersTurn = playersTurn;
        this.cardDeck = cardDeck;
        this.cardReset = cardReset;
        this.stepQuantity = stepQuantity;
    }

    public void addToCardReset(Card card)
    {
        cardReset.add(card);
    }

    public void addToCardReset(Collection<Card> cards)
    {
        for (Card card : cards)
            addToCardReset(card);
    }

    public void removePlayer(AbstractPlayer player)
    {
        playersTurn.remove(player);
    }

    public AbstractPlayer getPlayerById(Long playerId)
    {
        return playersTurn.getElements().stream()
                .filter(player -> player.getId().equals(playerId))
                .findFirst()
                .orElse(null);
    }

    public void setNowTurn(AbstractPlayer nowTurn)
    {
        playersTurn.assignAWalker(nowTurn);
    }

    public AbstractPlayer getNowTurn()
    {
        return playersTurn.getCurrent();
    }
}
