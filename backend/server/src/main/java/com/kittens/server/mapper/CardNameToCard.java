package com.kittens.server.mapper;

import com.kittens.logic.card.Card;
import com.kittens.logic.card.CardName;
import com.kittens.server.entity.game.init.CardEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class CardNameToCard implements Mapper<CardEntity, Card>
{
    private final Map<CardName, Card> cardNameToCardMap = new HashMap<>();

    public CardNameToCard(List<Card> cards)
    {
        for (Card card : cards)
            cardNameToCardMap.put(card.getName(), card);

    }


    @Override
    public Card map(CardEntity object)
    {
        return cardNameToCardMap.get(object.getName());
    }
}
