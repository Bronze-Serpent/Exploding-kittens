package com.kittens.action.sudden;

import com.kittens.action.Action;


public class SuddenInaction implements SuddenAction
{
    @Override
    public Action doSuddenAction(Action oldAction, Action newAction)
    {
        return newAction;
    }
}
