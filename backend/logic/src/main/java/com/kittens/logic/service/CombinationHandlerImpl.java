package com.kittens.logic.service;

import com.kittens.logic.card.Card;
import com.kittens.logic.combination.Combination;
import com.kittens.logic.model.AbstractPlayer;
import com.kittens.logic.model.GameState;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
public class CombinationHandlerImpl implements CombinationHandler
{

    private final List<Combination> combinations;


    public void playCombination(GameState gameState, Long whoPlayed, List<Card> cards)
    {
        AbstractPlayer player = gameState.getPlayerById(whoPlayed);
        for (Card combCard : cards)
            player.removeCard(combCard.getName());

        for (Combination combination : combinations)
            if (combination.isItCombination(cards))
            {
                combination.getAction()
                        .doAction(gameState);
                return;
            }

        throw new RuntimeException("Нет подходящий комбинации для кард: " + cards.stream()
                                                                                .map(Card::toString)
                                                                                .collect(Collectors.joining(",")));
    }
}
