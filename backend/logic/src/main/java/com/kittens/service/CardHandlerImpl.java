package com.kittens.service;

import com.kittens.GameState;
import com.kittens.card.Card;
import com.kittens.action.Action;
import com.kittens.action.Inaction;
import com.kittens.action.sudden.SuddenAction;

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
