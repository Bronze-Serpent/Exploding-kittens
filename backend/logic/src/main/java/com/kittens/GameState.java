package com.kittens;

import com.kittens.card.Card;
import lombok.*;

import java.util.List;
import java.util.Optional;


@Data
@AllArgsConstructor
public class GameState
{
    private LoopingList<Player> playersList;
    private List<Card> cardDeck;
    private List<Card> cardReset;
    private Player nowTurn;
    private int stepQuantity;


    public GameState(GameState gameState)
    {
        this.playersList = gameState.getPlayersList();
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


    public void removePlayer(Long playerId)
    {
        var mayBeUser = playersList.getSourceList().stream()
                .filter(player -> player.getId().equals(playerId))
                .findFirst();

        mayBeUser.ifPresent(player -> playersList.remove(player));
    }


    public Player getPlayerById(Long playerId)
    {
        return playersList.getSourceList().stream()
                .filter(player -> player.getId().equals(playerId))
                .findFirst()
                .orElse(null);
    }
}
