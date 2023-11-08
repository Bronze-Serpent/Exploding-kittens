package com.kittens.service;

import com.kittens.GameState;
import com.kittens.card.Card;
import com.kittens.combination.Combination;
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
