package com.kittens.logic.model;

import com.kittens.logic.card.Card;
import lombok.*;

import java.util.List;


@ToString
@EqualsAndHashCode
@Setter
@Getter
@AllArgsConstructor
public abstract class GameState
{
    private LoopingList<AbstractPlayer> playersTurn;
    private List<Card> cardDeck;
    private List<Card> cardReset;
    private AbstractPlayer nowTurn;
    private int stepQuantity;

    public void addToCardReset(Card card)
    {
        cardReset.add(card);
    }

    public void addToCardReset(List<Card> cards)
    {
        for (Card card : cards)
            addToCardReset(card);
    }

    public void removePlayer(AbstractPlayer player)
    {
        playersTurn.remove(player);
    }

    public AbstractPlayer getPlayerById(int playerId)
    {
        return playersTurn.getElements().stream()
                .filter(player -> player.getId() == playerId)
                .findFirst()
                .orElse(null);
    }
}
