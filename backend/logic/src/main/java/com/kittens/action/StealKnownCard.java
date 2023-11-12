package com.kittens.action;

import com.kittens.GameState;
import com.kittens.action.player.interaction.PlayerQuestioner;
import com.kittens.card.CardName;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class StealKnownCard implements Action
{

    private final PlayerQuestioner playerQuestioner;


    @Override
    public void doAction(GameState gameState)
    {
        var playerIdWhoseCard = Long.valueOf(playerQuestioner.ask(gameState.getNowTurn().getId(), PlayerQuestioner.Question.WHICH_PLAYER));
        var playerWhoseCard = gameState.getPlayerById(playerIdWhoseCard);

        var stringTakenCardName = playerQuestioner.ask(gameState.getNowTurn().getId(), PlayerQuestioner.Question.WHICH_CARD_TO_TAKE);
        var cardName = CardName.fromString(stringTakenCardName);
        if (playerWhoseCard.doesHeHaveCard(cardName))
        {
            var transmittedCard = playerWhoseCard.removeCard(cardName);
            gameState.getNowTurn().addCard(transmittedCard);
        }
    }

    @Override
    public String getName() {
        return "steal known card";
    }
}
