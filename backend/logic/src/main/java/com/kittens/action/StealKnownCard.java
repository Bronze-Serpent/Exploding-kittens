package com.kittens.action;

import com.kittens.GameState;
import com.kittens.action.player.interaction.PlayerQuestioner;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class StealKnownCard implements Action
{

    private final PlayerQuestioner playerQuestioner;


    @Override
    public GameState doAction(GameState gameState)
    {
        var playerIdWhoseCard = Long.valueOf(playerQuestioner.ask(gameState.getNowTurn().getId(), PlayerQuestioner.Question.WHICH_PLAYER));
        var playerWhoseCard = gameState.getPlayerById(playerIdWhoseCard);

        var nameOfCardToTake = playerQuestioner.ask(gameState.getNowTurn().getId(), PlayerQuestioner.Question.WHICH_CARD_TO_TAKE);

        var transmittedCard = playerWhoseCard.removeCard(nameOfCardToTake);
        if (transmittedCard != null)
        {
            gameState.getNowTurn().addCard(transmittedCard);
        }

        return gameState;
    }
}
