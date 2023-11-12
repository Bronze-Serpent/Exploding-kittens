package com.kittens.action.sudden;

import com.kittens.action.Action;


public class Cancel implements SuddenAction
{
    @Override
    public Action doSuddenAction(Action oldAction, Action newAction)
    {
        return oldAction;
    }

    @Override
    public String getName()
    {
        return "cancel";
    }
}
