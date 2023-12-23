package com.kittens.logic.combination;

import com.kittens.logic.action.Action;
import com.kittens.logic.action.Inaction;
import com.kittens.logic.action.sudden.SuddenAction;
import com.kittens.logic.action.sudden.SuddenInaction;
import com.kittens.logic.card.Card;
import com.kittens.logic.card.CardName;
import com.kittens.logic.card.OrdinaryCard;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.List;

import static com.kittens.logic.card.CardName.*;
import static com.kittens.logic.combination.CombinationPredicate.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CombinationPredicatesTest
{

    private final Action inaction = new Inaction();
    private final SuddenAction suddenInaction = new SuddenInaction();

    private final Card BEARDCAT_CARD = new OrdinaryCard(BEARDCAT, inaction, inaction, suddenInaction);
    private final Card TACOCAT_CARD = new OrdinaryCard(TACOCAT, inaction, inaction, suddenInaction);
    private final Card CATTERMELON_CARD = new OrdinaryCard(CATTERMELON, inaction, inaction, suddenInaction);
    private final Card HAIRY_CATATO_CARD = new OrdinaryCard(HAIRY_CATATO, inaction, inaction, suddenInaction);
    private final Card DEFUSE_CARD = new OrdinaryCard(DEFUSE, inaction, inaction, suddenInaction);

    @Test
    public void shouldDetermineTwo()
    {
        assertTrue(TWO_IDENTICAL.getPredicate().test(List.of(BEARDCAT_CARD, BEARDCAT_CARD)));
    }

    @Test
    public void shouldNotDetermineTwo()
    {
        assertFalse(TWO_IDENTICAL.getPredicate().test(List.of(BEARDCAT_CARD, TACOCAT_CARD)));
    }

    @Test
    public void shouldDetermineThree()
    {
        assertTrue(THREE_IDENTICAL.getPredicate().test(List.of(BEARDCAT_CARD, BEARDCAT_CARD, BEARDCAT_CARD)));
    }

    @Test
    public void shouldNotDetermineThree()
    {
        assertFalse(THREE_IDENTICAL.getPredicate().test(List.of(BEARDCAT_CARD, TACOCAT_CARD, BEARDCAT_CARD)));
    }

    @Test
    public void shouldDetermineFive()
    {
        assertTrue(FIVE_DIFFERENT.getPredicate().test(List.of(CATTERMELON_CARD, BEARDCAT_CARD,
                TACOCAT_CARD, HAIRY_CATATO_CARD, DEFUSE_CARD)));
    }

    @Test
    public void shouldNotDetermineFive()
    {
        assertFalse(FIVE_DIFFERENT.getPredicate().test(List.of(BEARDCAT_CARD, BEARDCAT_CARD,
                TACOCAT_CARD, HAIRY_CATATO_CARD, DEFUSE_CARD)));
    }


}
