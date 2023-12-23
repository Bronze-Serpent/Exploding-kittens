package com.kittens.logic.card;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;


@RequiredArgsConstructor
public enum CardName
{
    EXPLODING_KITTEN("exploding kitten"),
    DEFUSE("defuse"),
    NO("no"),
    ATTACK("attack"),
    GET_LOST("get lost"),
    FAVOR("favor"),
    SHUFFLE("shuffle"),
    HAIRY_CATATO("hairy catato"),
    TACOCAT("tacocat"),
    BEARDCAT("beardcat"),
    NYAN_CAT("nyan cat"),
    CATTERMELON("cattermelon"),
    SEE_THE_FUTURE("see the future");


    @Getter
    private final String writing;


    public static CardName fromString(String category)
    {
        return CardName.valueOf(String.join("_", category.split(" ")).toUpperCase());
    }


    public static boolean isItCardName(String line)
    {
        return Arrays.stream(CardName.values())
                .map(CardName::getWriting)
                .anyMatch(categoryWriting -> categoryWriting.equals(line));
    }
}
