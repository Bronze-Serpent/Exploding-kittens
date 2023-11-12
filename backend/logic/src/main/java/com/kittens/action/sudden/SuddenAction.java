package com.kittens.action.sudden;

import com.kittens.action.Action;


/***
 * Действия из этого перечисления влияют на обычные действия. По сути эти действия взаимодействуют с двумя Action.
 * Например, обычное действие может быть отменено (CANCEL) или как-либо изменено перед его применением.
 */
public interface SuddenAction
{
    Action doSuddenAction(Action oldAction, Action newAction);

    String getName();
}
