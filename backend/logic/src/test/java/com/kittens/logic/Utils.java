package com.kittens.logic;

import com.kittens.logic.action.Inaction;
import com.kittens.logic.action.SkippingMove;
import com.kittens.logic.action.TransferringMove;
import com.kittens.logic.action.sudden.Cancel;
import com.kittens.logic.action.sudden.SuddenInaction;
import com.kittens.logic.card.Card;
import com.kittens.logic.card.OrdinaryCard;
import com.kittens.logic.model.*;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.kittens.logic.card.CardName.*;


@UtilityClass
public class Utils
{
    public GameStateImpl createGameState()
    {
        LoopingList<AbstractPlayer> loopingList = new LoopingListImpl<>(List.of(new Player(-1L, new ArrayList<>())));
        return new GameStateImpl(
                loopingList,
                new LinkedList<>(),
                new ArrayList<>(),
                1);
    }

    public void set2PlayersWithCards(GameStateImpl gameState)
    {
        var suddenInaction = new SuddenInaction();
        var cancel = new Cancel();
        var inaction = new Inaction();
        var skippingMove = new SkippingMove();
        var transferringMove = new TransferringMove();

        var get_lost = new OrdinaryCard(GET_LOST, inaction, skippingMove, suddenInaction);
        var attack = new OrdinaryCard(ATTACK, inaction, transferringMove, suddenInaction);
        var defuse = new OrdinaryCard(DEFUSE, inaction, inaction, suddenInaction);
        var no = new OrdinaryCard(NO, inaction, inaction, cancel);
        var hairy_catato = new OrdinaryCard(HAIRY_CATATO, inaction, inaction, suddenInaction);
        var tacocat = new OrdinaryCard(TACOCAT, inaction, inaction, suddenInaction);
        var beardcat = new OrdinaryCard(BEARDCAT, inaction, inaction, suddenInaction);

        List<Card> cardDeck = new ArrayList<>();
        cardDeck.add(get_lost);
        cardDeck.add(attack);
        cardDeck.add(tacocat);
        cardDeck.add(beardcat);

        List<Card> cardReset = new ArrayList<>();
        cardReset.add(hairy_catato);
        cardReset.add(attack);
        cardReset.add(defuse);
        cardReset.add(no);

        var pl1 = new Player(1L, new ArrayList<>());
        pl1.addCard(beardcat);
        pl1.addCard(hairy_catato);
        pl1.addCard(get_lost);
        var pl2 = new Player(2L, new ArrayList<>());
        pl2.addCard(beardcat);
        pl2.addCard(hairy_catato);
        pl2.addCard(no);

        LoopingList<AbstractPlayer> loopingList = new LoopingListImpl<>(new ArrayList<>(List.of(pl1, pl2)));
        gameState.setPlayersTurn(loopingList);
        gameState.setNowTurn(pl1);
        gameState.setCardDeck(cardDeck);
        gameState.setCardReset(cardReset);
    }

    public GameStateImpl copy(GameStateImpl gameState)
    {
        List<AbstractPlayer> players = new ArrayList<>();
        for (AbstractPlayer pl :gameState.getPlayersTurn().getElements())
        {
            AbstractPlayer player = new Player(pl.getId(), new ArrayList<>(pl.getCards()));
            players.add(player);
        }

        GameStateImpl copied = new GameStateImpl(
                new LoopingListImpl<>(players),
                new ArrayList<>(gameState.getCardDeck()),
                new ArrayList<>(gameState.getCardReset()),
                gameState.getStepQuantity()
        );
        copied.setNowTurn(gameState.getNowTurn());

        return copied;
    }

}
