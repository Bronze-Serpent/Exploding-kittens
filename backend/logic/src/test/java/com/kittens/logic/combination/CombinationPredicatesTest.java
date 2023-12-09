package com.kittens.logic.combination;

import com.kittens.logic.action.Inaction;
import com.kittens.logic.action.sudden.SuddenInaction;
import com.kittens.logic.card.Card;
import com.kittens.logic.card.CardName;
import com.kittens.logic.card.OrdinaryCard;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.List;

import static com.kittens.logic.card.CardName.BEARDCAT;
import static com.kittens.logic.card.CardName.TACOCAT;
import static com.kittens.logic.combination.CombinationPredicate.TWO_IDENTICAL;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CombinationPredicatesTest
{
    @Test
    public void shouldDetermineTwo()
    {
        assertTrue(TWO_IDENTICAL.getPredicate().test(createCards(BEARDCAT, BEARDCAT)));
    }

    @Test
    public void shouldNotDetermineTwo()
    {
        assertFalse(TWO_IDENTICAL.getPredicate().test(createCards(BEARDCAT, TACOCAT)));
    }

    @Test
    public void shouldDetermineThree()
    {
        assertFalse(TWO_IDENTICAL.getPredicate().test(createCards(BEARDCAT, BEARDCAT, BEARDCAT)));
    }

    @Test
    public void shouldNotDetermineThree()
    {
        assertFalse(TWO_IDENTICAL.getPredicate().test(createCards(BEARDCAT, TACOCAT, BEARDCAT)));
    }

    @Test
    public void shouldDetermineFive()
    {
        assertFalse(TWO_IDENTICAL.getPredicate().test(createCards(BEARDCAT, BEARDCAT, BEARDCAT, BEARDCAT, BEARDCAT)));
    }

    @Test
    public void shouldNotDetermineFive()
    {
        assertFalse(TWO_IDENTICAL.getPredicate().test(createCards(BEARDCAT, TACOCAT, BEARDCAT, BEARDCAT, BEARDCAT)));
    }


    private List<Card> createCards(CardName... names)
    {
        var inaction = new Inaction();
        var suddenInaction = new SuddenInaction();

        List<Card> cards = new ArrayList<>();
        for (CardName name : names)
            cards.add(new OrdinaryCard(name, inaction, inaction, suddenInaction));

        return cards;
    }
}
