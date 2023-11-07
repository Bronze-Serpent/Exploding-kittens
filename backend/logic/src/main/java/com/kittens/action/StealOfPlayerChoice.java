package com.kittens.action;

import com.kittens.GameState;
import com.kittens.action.player.interaction.PlayerQuestioner;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class StealOfPlayerChoice implements Action
{

    private final PlayerQuestioner playerQuestioner;


    @Override
    public GameState doAction(GameState gameState)
    {
        var playerIdWhoseCard = Long.valueOf(playerQuestioner.ask(gameState.getNowTurn().getId(), PlayerQuestioner.Question.WHICH_PLAYER));
        var playerWhoseCard = gameState.getPlayerById(playerIdWhoseCard);
        
        if (playerWhoseCard.getCards().size() < 1)
            return gameState;

        var cardName = playerQuestioner.ask(playerIdWhoseCard, PlayerQuestioner.Question.WHICH_CARD_TO_GIVE);

        var transmittedCard = playerWhoseCard.removeCard(cardName);
        gameState.getNowTurn().addCard(transmittedCard);

        return gameState;
    }
}
