package com.kittens.service;

import com.kittens.GameState;
import com.kittens.card.Action;
import com.kittens.card.Card;
import com.kittens.card.SuddenAction;

import java.util.List;


public class CardHandlerImpl implements CardHandler
{

    @Override
    public GameState playCard(GameState oldGameState, Card playerCard, List<Card> suddenCards)
    {
        var movesPlayer = oldGameState.getNowTurn();

        Action cardAction = playerCard.getPlayingAction();

        // применение карт игроков к сыгранной карте
        Action oldAction = Action.INACTION;
        Action userAction = cardAction;
        for (Card suddenCard : suddenCards)
        {
            SuddenAction suddenAction = suddenCard.getSuddenPlayingAction();
            var suddenResult = suddenAction.doSuddenAction(suddenCard, oldAction, userAction);

            oldAction = userAction;
            userAction = suddenResult;
        }

        // применение итогового действия
        var newGameState = userAction.doAction(playerCard, oldGameState);

        // добавление сыгранной карты в сброс
        movesPlayer.removeCard(playerCard.getName());
        newGameState.addToCardReset(playerCard);

        return newGameState;
    }
}
