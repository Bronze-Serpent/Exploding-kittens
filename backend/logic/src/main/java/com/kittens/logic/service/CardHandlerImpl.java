package com.kittens.logic.service;

import com.kittens.logic.card.Card;
import com.kittens.logic.action.Action;
import com.kittens.logic.action.Inaction;
import com.kittens.logic.action.sudden.SuddenAction;
import com.kittens.logic.models.GameState;

import java.util.List;


public class CardHandlerImpl implements CardHandler
{

    @Override
    public void playCard(GameState gameState, Card playerCard, List<Card> suddenCards)
    {
        Action action = playerCard.getPlayingAction();

        // применение карт игроков к сыгранной карте
        Action oldAction = new Inaction();
        Action userAction = action;
        for (Card suddenCard : suddenCards)
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
        gameState.addToCardReset(suddenCards);
    }
}
