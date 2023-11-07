package com.kittens;

import com.kittens.card.Card;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Getter
@EqualsAndHashCode(exclude = "cards")
@RequiredArgsConstructor
public class Player
{
    private final Long id;
    private final String name;
    private final List<Card> cards = new ArrayList<>();


    public void addCard(Card card)
    {
        cards.add(card);
    }


    public boolean isThereCard(final String name)
    {
        return cards.stream()
                .anyMatch(card -> card.getName().equals(name));
    }


    public Card removeCard(String nameToDelete)
    {
        Iterator<Card> cardIterator = cards.iterator();

        while(cardIterator.hasNext())
        {
            Card nextCard = cardIterator.next();
            if (nextCard.getName().equals(nameToDelete))
            {
                cards.remove(nextCard);
                return nextCard;
            }
        }

        return null;
    }


    public Card removeCard(int indexToDelete)
    {
        return cards.remove(indexToDelete);
    }
}
