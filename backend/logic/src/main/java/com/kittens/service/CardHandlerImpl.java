package com.kittens.service;

import com.kittens.GameState;
import com.kittens.card.Card;
import com.kittens.action.Action;
import com.kittens.action.Inaction;
import com.kittens.action.sudden.SuddenCardAction;

import java.util.List;


public class CardHandlerImpl implements CardHandler
{

    @Override
    public GameState playCard(GameState oldGameState, Card playerCard, List<Card> suddenCards)
    {
        var movesPlayer = oldGameState.getNowTurn();

        Action action = playerCard.getPlayingAction();

        // применение карт игроков к сыгранной карте
        Action oldAction = new Inaction();
        Action userAction = action;
        for (Card suddenCard : suddenCards)
        {
            SuddenCardAction suddenCardAction = suddenCard.getSuddenPlayingAction();
            var suddenResult = suddenCardAction.doSuddenAction(suddenCard, oldAction, userAction);

            oldAction = userAction;
            userAction = suddenResult;
        }

        // применение итогового действия
        var newGameState = userAction.doAction(oldGameState);

        // добавление сыгранной карты в сброс
        movesPlayer.removeCard(playerCard.getName());
        newGameState.addToCardReset(playerCard);

        return newGameState;
    }
}
