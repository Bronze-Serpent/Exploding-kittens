package com.kittens.server.mapper;

import com.kittens.logic.card.Card;
import com.kittens.logic.card.CardName;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
public class CardNameToCard implements Mapper<CardName[], List<Card>>
{
    private final Map<CardName, Card> cardNameToCardMap = new HashMap<>();

    public CardNameToCard(List<Card> cards)
    {
        for (Card card : cards)
            cardNameToCardMap.put(card.getName(), card);

    }

    @Override
    public List<Card> map(CardName[] object)
    {
        return Arrays.stream(object)
                .map(cardNameToCardMap::get)
                .collect(Collectors.toList());
    }
}
