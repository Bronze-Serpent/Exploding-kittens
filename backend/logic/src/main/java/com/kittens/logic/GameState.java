package com.kittens.logic;

import com.kittens.logic.card.Card;
import lombok.*;

import java.util.List;


@Data
@AllArgsConstructor
public class GameState
{
    private LoopingList<Player> playersTurn;
    private List<Card> cardDeck;
    private List<Card> cardReset;
    private Player nowTurn;
    private int stepQuantity;


    public GameState(GameState gameState)
    {
        this.playersTurn = gameState.getPlayersTurn();
        this.cardDeck = gameState.getCardDeck();
        this.cardReset = gameState.getCardReset();
        this.nowTurn = gameState.getNowTurn();
        this.stepQuantity = gameState.getStepQuantity();
    }


    public void addToCardReset(Card card)
    {
        cardReset.add(card);
    }

    public void addToCardReset(List<Card> cards)
    {
       for (Card card : cards)
           addToCardReset(card);
    }


    public void removePlayer(Player player)
    {
        playersTurn.remove(player);
    }


    public Player getPlayerById(int playerId)
    {
        return playersTurn.getConsistency().stream()
                .filter(player -> player.getId() == playerId)
                .findFirst()
                .orElse(null);
    }
}
