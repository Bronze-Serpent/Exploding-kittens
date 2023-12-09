package com.kittens.logic.action.sudden;

import com.kittens.logic.action.Action;


public class SuddenInaction implements SuddenAction
{
    @Override
    public Action doSuddenAction(Action oldAction, Action newAction)
    {
        return newAction;
    }

    @Override
    public String getName() {
        return "sudden inaction";
    }
}
