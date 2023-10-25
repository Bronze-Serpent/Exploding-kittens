package com.kittens.card;

/***
 * Действия из этого перечисления влияют на обычные действия. По сути эти действия взаимодействуют с двумя Action.
 * Например, обычное действие может быть отменено (CANCEL) или как-либо изменено перед его применением.
 */
public enum SuddenAction
{
    CANCEL {
        @Override
        public Action doSuddenAction(Card card, Action oldAction, Action newAction)
        {
            return oldAction;
        }
    },

    INACTION {
        @Override
        public Action doSuddenAction(Card card, Action oldAction, Action newAction)
        {
            return newAction;
        }
    }
    ;

    public abstract Action doSuddenAction(Card card, Action oldAction, Action newAction);
}
