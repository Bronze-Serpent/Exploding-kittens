package com.kittens.server.mapper;

import com.kittens.logic.card.Card;
import com.kittens.logic.card.CardName;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class CardToCardName implements Mapper<List<Card>, String[]>
{
    @Override
    public String[] map(List<Card> object)
    {
        return object.stream()
                .map(Card::getName)
                .map(CardName::toString)
                .toArray(String[]::new);
    }
}
