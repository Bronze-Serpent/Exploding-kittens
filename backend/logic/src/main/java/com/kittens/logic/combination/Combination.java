package com.kittens.logic.combination;

import com.kittens.logic.card.Card;
import com.kittens.logic.action.Action;

import java.util.List;


public interface Combination
{
    String getName();

    boolean isItCombination(List<Card> cards);

    Action getAction();
}
