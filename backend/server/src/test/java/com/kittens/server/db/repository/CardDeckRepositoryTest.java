package com.kittens.server.db.repository;

import com.kittens.server.db.DatabaseTest;
import com.kittens.server.entity.CardDeck;
import com.kittens.server.repository.CardDeckRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RequiredArgsConstructor
public class CardDeckRepositoryTest extends DatabaseTest
{
    private final CardDeckRepository cardDeckRepository;


    @Test
    public void shouldUpdateCardDeck()
    {
        CardDeck oldCardDeck = cardDeckRepository.findById(1L).get();

        String[] cards = {"a", "b", "c"};
        CardDeck cardDeck = new CardDeck(oldCardDeck.getId(), cards);

        // TODO: 13.12.2023 ??? забыл, почему при flush сессии такое происходит с сущностями oldCardDeck value обновился, в gameState стал null
        cardDeckRepository.save(cardDeck); // save из pring data japa обновит сущность, если такая уже имеется, в отличие от просто JPA

        CardDeck newCardDeck = cardDeckRepository.findById(1L).get();
        assertThat(newCardDeck.getValue()).containsExactlyElementsOf(List.of(cards));
    }
}
