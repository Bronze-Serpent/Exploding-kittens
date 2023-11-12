package com.kittens.action;

import com.kittens.GameState;
import com.kittens.action.player.interaction.PlayerQuestioner;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class StealUnknownCard implements Action
{

    private final PlayerQuestioner playerQuestioner;

    @Override
    public void doAction(GameState gameState)
    {
        var playerIdWhoseCard = Long.valueOf(playerQuestioner.ask(gameState.getNowTurn().getId(), PlayerQuestioner.Question.WHICH_PLAYER));
        var playerWhoseCard = gameState.getPlayerById(playerIdWhoseCard);

        if (playerWhoseCard.getCards().size() < 1)
            return;

        var numOfTakenCard = Integer.parseInt(playerQuestioner.ask(gameState.getNowTurn().getId(), PlayerQuestioner.Question.WHICH_NUM_OF_CARD_TAKE));
        var transmittedCard = playerWhoseCard.removeCard(numOfTakenCard);
        gameState.getNowTurn().addCard(transmittedCard);
    }

    @Override
    public String getName() {
        return "steal unknown card";
    }
}
