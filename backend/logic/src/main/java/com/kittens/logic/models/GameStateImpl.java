package com.kittens.logic.models;

import com.kittens.logic.card.Card;
import lombok.*;

import java.util.List;


@Data
@AllArgsConstructor
public class GameStateImpl implements GameState
{
    private LoopingList<AbstractPlayer> playersTurn;
    private List<Card> cardDeck;
    private List<Card> cardReset;
    private AbstractPlayer nowTurn;
    private int stepQuantity;


    public GameStateImpl(GameStateImpl gameState)
    {
        this.playersTurn = gameState.getPlayersTurn();
        this.cardDeck = gameState.getCardDeck();
        this.cardReset = gameState.getCardReset();
        this.nowTurn = gameState.getNowTurn();
        this.stepQuantity = gameState.getStepQuantity();
    }

    @Override
    public void addToCardReset(Card card)
    {
        cardReset.add(card);
    }

    @Override
    public void addToCardReset(List<Card> cards)
    {
       for (Card card : cards)
           addToCardReset(card);
    }

    @Override
    public void removePlayer(AbstractPlayer player)
    {
        playersTurn.remove(player);
    }

    @Override
    public AbstractPlayer getPlayerById(int playerId)
    {
        return playersTurn.getSequence().stream()
                .filter(player -> player.getId() == playerId)
                .findFirst()
                .orElse(null);
    }
}
