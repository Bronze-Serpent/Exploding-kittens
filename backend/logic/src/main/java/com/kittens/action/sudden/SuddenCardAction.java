package com.kittens.action.sudden;

import com.kittens.action.Action;
import com.kittens.card.Card;


/***
 * Действия из этого перечисления влияют на обычные действия. По сути эти действия взаимодействуют с двумя Action.
 * Например, обычное действие может быть отменено (CANCEL) или как-либо изменено перед его применением.
 */
public interface SuddenCardAction
{
    Action doSuddenAction(Card card, Action oldAction, Action newAction);
}
