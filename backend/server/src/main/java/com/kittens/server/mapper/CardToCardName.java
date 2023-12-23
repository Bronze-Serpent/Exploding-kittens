package com.kittens.server.mapper;

import com.kittens.logic.card.Card;
import com.kittens.logic.card.CardName;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class CardToCardName implements Mapper<List<Card>, CardName[]>
{
    @Override
    public CardName[] map(List<Card> object)
    {
        return object.stream()
                .map(Card::getName)
                .toArray(CardName[]::new);
    }

    public CardName map(Card object)
    {
        return object.getName();
    }
}
