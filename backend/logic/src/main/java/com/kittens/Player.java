package com.kittens;

import com.kittens.card.Card;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Getter
@EqualsAndHashCode
@ToString
@RequiredArgsConstructor
public class Player
{
    private final Long id;
    private final String name;
    private final List<Card> cards = new ArrayList<>();

    public Player(Player player)
    {
        this.id = player.getId();
        this.name = player.getName();
        this.cards.addAll(player.getCards());
    }


    public void addCard(Card card)
    {
        cards.add(card);
    }


    public boolean doesHeHaveCard(String name)
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

        throw new RuntimeException("Карты с именем '" + nameToDelete + "' нет");
    }


    public Card removeCard(int indexToDelete)
    {
        return cards.remove(indexToDelete);
    }
}
