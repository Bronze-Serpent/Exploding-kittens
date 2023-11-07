package com.kittens.action.sudden;

import com.kittens.action.Action;
import com.kittens.card.Card;


public class Cancel implements SuddenCardAction
{
    @Override
    public Action doSuddenAction(Card card, Action oldAction, Action newAction)
    {
        return oldAction;
    }
}
