package com.kittens.logic.model;

import com.kittens.logic.card.Card;
import com.kittens.logic.card.CardName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Getter
@EqualsAndHashCode(of = "id")
@ToString
@RequiredArgsConstructor
public abstract class AbstractPlayer
{
    private final Long id;
    private final List<Card> cards;

    public AbstractPlayer(AbstractPlayer player)
    {
        this.id = player.getId();
        this.cards = new ArrayList<>(player.getCards());
    }

    public void addCard(Card card)
    {
        cards.add(card);
    }


    public boolean hasACard(CardName name)
    {
        return cards.stream()
                .anyMatch(card -> card.getName().equals(name));
    }


    public Card removeCard(CardName nameToDelete)
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

        throw new RuntimeException("У игрока с id: " + this.id + " Карты с именем '" + nameToDelete + "' нет");
    }


    public Card removeCard(int indexToDelete)
    {
        return cards.remove(indexToDelete);
    }
}
