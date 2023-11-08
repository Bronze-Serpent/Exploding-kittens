package com.kittens.combination;

import com.kittens.action.Inaction;
import com.kittens.action.sudden.SuddenInaction;
import com.kittens.card.Card;
import com.kittens.card.OrdinaryCard;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.List;

import static com.kittens.combination.CombinationPredicates.TWO_IDENTICAL;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CombinationPredicatesTest
{
    @Test
    public void shouldDetermineTwo()
    {
        assertTrue(TWO_IDENTICAL.test(createCards("test1", "test1")));
    }

    @Test
    public void shouldNotDetermineTwo()
    {
        assertFalse(TWO_IDENTICAL.test(createCards("test1", "test2")));
    }

    @Test
    public void shouldDetermineThree()
    {
        assertFalse(TWO_IDENTICAL.test(createCards("test1", "test1", "test1")));
    }

    @Test
    public void shouldNotDetermineThree()
    {
        assertFalse(TWO_IDENTICAL.test(createCards("test1", "test2", "test1")));
    }

    @Test
    public void shouldDetermineFive()
    {
        assertFalse(TWO_IDENTICAL.test(createCards("test1", "test1", "test1", "test1", "test1")));
    }

    @Test
    public void shouldNotDetermineFive()
    {
        assertFalse(TWO_IDENTICAL.test(createCards("test1", "test2", "test1", "test1", "test1")));
    }


    private List<Card> createCards(String... names)
    {
        var inaction = new Inaction();
        var suddenInaction = new SuddenInaction();

        List<Card> cards = new ArrayList<>();
        for (String name : names)
            cards.add(new OrdinaryCard(name, inaction, inaction, suddenInaction));

        return cards;
    }
}
