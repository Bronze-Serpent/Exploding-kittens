package com.kittens.logic.action;

import com.kittens.logic.GameState;
import com.kittens.logic.action.player.interaction.PlayerInformer;
import com.kittens.logic.action.player.interaction.PlayerQuestioner;
import lombok.RequiredArgsConstructor;

import static com.kittens.logic.action.GameStateUtils.doesAnyoneHaveACard;
import static com.kittens.logic.action.player.interaction.PlayerInformer.Informing.*;
import static com.kittens.logic.action.player.interaction.PlayerQuestioner.Question.WHICH_NUM_OF_CARD_TAKE;
import static com.kittens.logic.action.player.interaction.PlayerQuestioner.Question.WHICH_PLAYER;


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

        int nowTurnId = gameState.getNowTurn().getId();

        var playerIdWhoseCard = Integer.parseInt(playerQuestioner.ask(nowTurnId, WHICH_PLAYER));
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
        return "steal_unknown_card";
    }
}
