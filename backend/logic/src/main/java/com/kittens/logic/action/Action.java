package com.kittens.logic.action;

import com.kittens.logic.GameState;


public interface Action
{

    void doAction(GameState gameState);

    String getName();
}
