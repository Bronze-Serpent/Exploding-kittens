package com.kittens.action;

import com.kittens.GameState;


public interface Action
{

    GameState doAction(GameState gameState);
}
