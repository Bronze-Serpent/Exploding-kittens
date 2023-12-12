package com.kittens.logic.action;

import com.kittens.logic.GameState;
import com.kittens.logic.Player;
import com.kittens.logic.action.player.interaction.PlayerInformer;
import com.kittens.logic.action.player.interaction.PlayerQuestioner;
import com.kittens.logic.card.CardName;
import lombok.RequiredArgsConstructor;

import static com.kittens.logic.action.GameStateUtils.doesAnyoneHaveACard;
import static com.kittens.logic.action.player.interaction.PlayerInformer.Informing.CARD_RECEIVED;
import static com.kittens.logic.action.player.interaction.PlayerInformer.Informing.CARD_STOLEN;
import static com.kittens.logic.action.player.interaction.PlayerQuestioner.Question.WHICH_CARD_TO_GIVE;
import static com.kittens.logic.action.player.interaction.PlayerQuestioner.Question.WHICH_PLAYER;


@RequiredArgsConstructor
public class StealOfPlayerChoice implements Action
{

    private final PlayerQuestioner playerQuestioner;
    private final PlayerInformer playerInformer;


    @Override
    public void doAction(GameState gameState)
    {
        // если нет игроков, у которых можно бы было украсть карту
        if (!doesAnyoneHaveACard(gameState))
            return;

        Player nowTurn = gameState.getNowTurn();

        var playerIdWhoseCard = Integer.parseInt((playerQuestioner.ask(nowTurn.getId(), WHICH_PLAYER)));
        var playerWhoseCard = gameState.getPlayerById(playerIdWhoseCard);
        
        if (playerWhoseCard.getCards().size() < 1)
            return;

        var cardName = playerQuestioner.ask(playerIdWhoseCard, WHICH_CARD_TO_GIVE);

        var transmittedCard = playerWhoseCard.removeCard(CardName.fromString(cardName));
        playerInformer.inform(playerWhoseCard.getId(), CARD_STOLEN, transmittedCard.getName().getWriting());
        nowTurn.addCard(transmittedCard);
        playerInformer.inform(nowTurn.getId(), CARD_RECEIVED, transmittedCard.getName().getWriting());

    }

    @Override
    public String getName() {
        return "steal_of_player_choice";
    }
}