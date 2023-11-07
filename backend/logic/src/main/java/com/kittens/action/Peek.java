package com.kittens.action;

import com.kittens.GameState;
import com.kittens.card.Card;
import com.kittens.action.player.interaction.PlayerInformer;
import lombok.RequiredArgsConstructor;

import java.util.stream.Collectors;


@RequiredArgsConstructor
public class Peek implements Action
{

    private final PlayerInformer playerInformer;


    @Override
    public GameState doAction(GameState gameState)
    {
        var first3Cards = gameState.getCardDeck().stream()
                .map(Card::getName)
                .limit(3)
                .collect(Collectors.joining(","));

        playerInformer.inform(gameState.getNowTurn().getId(), PlayerInformer.Informing.SHOW_CARDS, first3Cards);

        return gameState;
    }
}
