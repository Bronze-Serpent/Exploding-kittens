package com.kittens.logic.service;

import com.kittens.logic.card.Card;
import com.kittens.logic.action.Action;
import com.kittens.logic.action.Inaction;
import com.kittens.logic.action.sudden.SuddenAction;
import com.kittens.logic.model.AbstractPlayer;
import com.kittens.logic.model.GameState;

import java.util.Collection;
import java.util.List;
import java.util.Map;


public class CardHandlerImpl implements CardHandler
{

    @Override
    public void playCard(GameState gameState, Long whoPlayedId, Card playerCard, Map<Long, List<Card>> playedSuddenCards)
    {
        gameState.getPlayerById(whoPlayedId).removeCard(playerCard.getName());
        for (Map.Entry<Long, List<Card>> entrySuddenCard : playedSuddenCards.entrySet())
        {
            AbstractPlayer whoPlayedCard = gameState.getPlayerById(entrySuddenCard.getKey());
            for (Card suddenCard : entrySuddenCard.getValue())
                whoPlayedCard.removeCard(suddenCard.getName());
        }
        
        Action action = playerCard.getPlayingAction();

        // применение карт игроков к сыгранной карте
        List<Card> allSuddenCards = playedSuddenCards.values()
                .stream()
                .flatMap(Collection::stream)
                .toList();
        Action oldAction = new Inaction();
        Action userAction = action;
        for (Card suddenCard : allSuddenCards)
        {
            SuddenAction suddenAction = suddenCard.getSuddenPlayingAction();
            var suddenResult = suddenAction.doSuddenAction(oldAction, userAction);

            oldAction = userAction;
            userAction = suddenResult;
        }

        // применение итогового действия
         userAction.doAction(gameState);

        // добавление сыгранных карт в сброс
        gameState.addToCardReset(playerCard);
        gameState.addToCardReset(allSuddenCards);
    }
}
