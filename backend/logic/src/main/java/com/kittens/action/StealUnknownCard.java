package com.kittens.action;

import com.kittens.GameState;
import com.kittens.action.player.interaction.PlayerInformer;
import com.kittens.action.player.interaction.PlayerQuestioner;
import lombok.RequiredArgsConstructor;

import static com.kittens.action.GameStateUtils.doesAnyoneHaveACard;
import static com.kittens.action.player.interaction.PlayerInformer.Informing.*;
import static com.kittens.action.player.interaction.PlayerQuestioner.Question.WHICH_NUM_OF_CARD_TAKE;
import static com.kittens.action.player.interaction.PlayerQuestioner.Question.WHICH_PLAYER;


@RequiredArgsConstructor
public class StealUnknownCard implements Action
{

    private final PlayerQuestioner playerQuestioner;
    private final PlayerInformer playerInformer;


    @Override
    public void doAction(GameState gameState)
    {
        // если нет игроков, у которых можно бы было украсть карту
        if (!doesAnyoneHaveACard(gameState))
            return;

        Long nowTurnId = gameState.getNowTurn().getId();

        var playerIdWhoseCard = Long.valueOf(playerQuestioner.ask(nowTurnId, WHICH_PLAYER));
        var playerWhoseCard = gameState.getPlayerById(playerIdWhoseCard);

        int numOfPlayerCards = playerWhoseCard.getCards().size();
        if (numOfPlayerCards < 1)
            return;

        playerInformer.inform(nowTurnId, NUM_OF_PLAYER_CARDS, String.valueOf(numOfPlayerCards));
        var numOfTakenCard = Integer.parseInt(playerQuestioner.ask(nowTurnId, WHICH_NUM_OF_CARD_TAKE));

        var transmittedCard = playerWhoseCard.removeCard(numOfTakenCard);
        playerInformer.inform(playerWhoseCard.getId(), CARD_STOLEN, transmittedCard.getName().getWriting());
        gameState.getNowTurn().addCard(transmittedCard);
        playerInformer.inform(nowTurnId, CARD_RECEIVED, transmittedCard.getName().getWriting());

    }

    @Override
    public String getName() {
        return "steal unknown card";
    }
}
