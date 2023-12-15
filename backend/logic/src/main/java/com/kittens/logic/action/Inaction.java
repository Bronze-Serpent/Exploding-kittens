package com.kittens.logic.action;

import com.kittens.logic.models.GameState;


public class Inaction implements Action
{
    @Override
    public void doAction(GameState gameState)
    {
    }

    @Override
    public String getName() {
        return "inaction";
    }
}
