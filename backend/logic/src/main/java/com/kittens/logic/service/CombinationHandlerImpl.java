package com.kittens.logic.service;

import com.kittens.logic.card.Card;
import com.kittens.logic.combination.Combination;
import com.kittens.logic.models.GameState;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
public class CombinationHandlerImpl implements CombinationHandler
{

    private final List<Combination> combinations;


    public void playCombination(GameState gameState, List<Card> cards)
    {
        for (Combination combination : combinations)
            if (combination.isItCombination(cards))
                combination.getAction()
                        .doAction(gameState);


        throw new RuntimeException("Нет подходящий комбинации для кард: " + cards.stream()
                                                                                .map(Card::toString)
                                                                                .collect(Collectors.joining(",")));
    }
}
