package com.kittens.service;

import com.kittens.GameState;
import com.kittens.card.Card;
import com.kittens.combination.Combination;
import lombok.RequiredArgsConstructor;

import java.util.List;


@RequiredArgsConstructor
public class CombinationHandlerImpl implements CombinationHandler
{

    private final List<Combination> combinations;


    public GameState playCombination(GameState oldGameState, List<Card> cards)
    {
        for (Combination combination : combinations)
            if (combination.isItCombination(cards))
                return combination.getAction()
                        .doAction(oldGameState);
            
        return oldGameState;
    }
}
