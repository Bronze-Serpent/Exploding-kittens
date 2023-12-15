package com.kittens.logic.action;

import com.kittens.logic.model.GameState;


public interface Action
{
    void doAction(GameState gameState);

    String getName();
}
