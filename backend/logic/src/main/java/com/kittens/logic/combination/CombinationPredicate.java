package com.kittens.logic.combination;

import com.kittens.logic.card.Card;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;


@RequiredArgsConstructor
@Getter
public enum CombinationPredicate
{
    TWO_IDENTICAL(
            (cards) -> cards.size() == 2 &&
            (cards.get(0).equals(cards.get(1))),
            "two identical"),

    THREE_IDENTICAL(
            (cards) -> cards.size() == 3 &&
            cards.get(0).equals(cards.get(1)) &&
            cards.get(0).equals(cards.get(2)),
            "three identical"),

    FIVE_DIFFERENT((cards) -> cards.size() == 5 &&
            new HashSet<>(cards).size() == 5,
            "five different");


    private final Predicate<List<Card>> predicate;

    private final String writing;
}
