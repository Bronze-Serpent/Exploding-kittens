package com.kittens.logic.action;


import com.kittens.logic.model.GameState;

public class TransferringMove implements Action
{

    @Override
    public void doAction(GameState gameState)
    {
        gameState.setNowTurn(gameState.getPlayersTurn().next());
        gameState.setStepQuantity(gameState.getStepQuantity() + 1);
    }

    @Override
    public String getName() {
        return "transferring_move";
    }
}
