package com.kittens.logic.action;

import com.kittens.logic.model.AbstractPlayer;
import com.kittens.logic.model.GameState;
import com.kittens.logic.action.player.interaction.PlayerInformer;
import com.kittens.logic.action.player.interaction.PlayerQuestioner;
import com.kittens.logic.card.CardName;
import lombok.RequiredArgsConstructor;

import static com.kittens.logic.action.player.interaction.PlayerInformer.Informing.*;
import static com.kittens.logic.action.player.interaction.PlayerQuestioner.Question.WHICH_CARD_TO_TAKE;
import static com.kittens.logic.action.player.interaction.PlayerQuestioner.Question.WHICH_PLAYER;


@RequiredArgsConstructor
public class StealKnownCard implements Action
{
    private final PlayerQuestioner playerQuestioner;
    private final PlayerInformer playerInformer;


    @Override
    public void doAction(GameState gameState)
    {
        AbstractPlayer nowTurn = gameState.getNowTurn();

        var playerIdWhoseCard = Integer.parseInt(playerQuestioner.ask(nowTurn, WHICH_PLAYER));
        var playerWhoseCard = gameState.getPlayerById(playerIdWhoseCard);

        var stringTakenCardName = playerQuestioner.ask(nowTurn, WHICH_CARD_TO_TAKE);
        var cardName = CardName.fromString(stringTakenCardName);
        if (playerWhoseCard.hasACard(cardName))
        {
            var transmittedCard = playerWhoseCard.removeCard(cardName);
            playerInformer.inform(playerWhoseCard, CARD_STOLEN, transmittedCard.getName().getWriting());
            nowTurn.addCard(transmittedCard);
            playerInformer.inform(nowTurn, CARD_RECEIVED, transmittedCard.getName().getWriting());
        }
        else
            playerInformer.inform(nowTurn, NO_SUCH_CARD);
    }

    @Override
    public String getName() {
        return "steal_known_card";
    }
}
