package com.kittens.combination;

import com.kittens.card.Card;
import com.kittens.action.Action;

import java.util.List;


public interface Combination
{
    String getName();

    boolean isItCombination(List<Card> cards);

    Action getAction();
}
