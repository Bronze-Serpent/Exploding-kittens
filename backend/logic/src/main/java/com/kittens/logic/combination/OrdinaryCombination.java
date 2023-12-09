package com.kittens.logic.combination;

import com.kittens.logic.action.Action;
import com.kittens.logic.card.Card;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.Predicate;


@RequiredArgsConstructor
public class OrdinaryCombination implements Combination
{

    @Getter
    private final String name;

    private final Predicate<List<Card>> combinationPredicate;

    @Getter
    private final Action action;


    @Override
    public boolean isItCombination(List<Card> cards)
    {
        return combinationPredicate.test(cards);
    }

}
