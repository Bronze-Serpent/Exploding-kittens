package com.kittens.server.integration.mapper;

import com.kittens.logic.action.Inaction;
import com.kittens.logic.action.SkippingMove;
import com.kittens.logic.action.sudden.SuddenInaction;
import com.kittens.logic.card.Card;
import com.kittens.logic.card.CardName;
import com.kittens.logic.card.OrdinaryCard;
import com.kittens.logic.model.AbstractPlayer;
import com.kittens.server.game.model.UserRefPlayer;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;


@UtilityClass
public class CreationUtils
{

    public List<Card> createCards()
    {
        Inaction inaction = new Inaction();
        SuddenInaction suddenInaction = new SuddenInaction();
        SkippingMove skippingMove = new SkippingMove();

        return new ArrayList<>(List.of(new OrdinaryCard(CardName.NYAN_CAT, inaction, inaction, suddenInaction),
                new OrdinaryCard(CardName.GET_LOST, inaction, skippingMove, suddenInaction),
                new OrdinaryCard(CardName.BEARDCAT, inaction, inaction, suddenInaction)));
    }

    public List<AbstractPlayer> createPlayers()
    {
        return List.of(new UserRefPlayer(1L, CreationUtils.createCards(), 1L),
                new UserRefPlayer(2L, CreationUtils.createCards(), 1L),
                new UserRefPlayer(3L, CreationUtils.createCards(), 2L)
                );
    }
}
