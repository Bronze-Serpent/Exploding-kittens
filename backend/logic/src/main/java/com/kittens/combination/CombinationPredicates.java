package com.kittens.combination;

import com.kittens.card.Card;

import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;


public final class CombinationPredicates
{
    public static final Predicate<List<Card>> TWO_IDENTICAL = (cards) -> cards.size() == 2 &&
                                                                (cards.get(0).equals(cards.get(1)));

    public static final Predicate<List<Card>> THREE_IDENTICAL = (cards) -> cards.size() == 3 &&
                                                                cards.get(0).equals(cards.get(1)) &&
                                                                cards.get(0).equals(cards.get(2));

    public static final Predicate<List<Card>> FIVE_DIFFERENT = (cards) -> cards.size() == 5 &&
                                                                new HashSet<>(cards).size() == 5;
}
