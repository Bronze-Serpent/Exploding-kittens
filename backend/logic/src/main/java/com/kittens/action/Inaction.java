package com.kittens.action;

import com.kittens.GameState;


public class Inaction implements Action
{

    @Override
    public GameState doAction(GameState gameState)
    {
        return gameState;
    }
}
