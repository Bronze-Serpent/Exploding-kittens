package com.kittens.action;

import com.kittens.GameState;
import com.kittens.Player;
import com.kittens.action.player.interaction.PlayerInformer;
import com.kittens.action.player.interaction.PlayerQuestioner;
import com.kittens.card.CardName;
import lombok.RequiredArgsConstructor;

import static com.kittens.action.GameStateUtils.isResetEmpty;
import static com.kittens.action.player.interaction.PlayerInformer.Informing.CARD_RECEIVED;
import static com.kittens.action.player.interaction.PlayerQuestioner.Question.WHICH_CARD_TO_TAKE;


@RequiredArgsConstructor
public class StealCardFromReset implements Action
{

    private final PlayerQuestioner playerQuestioner;
    private final PlayerInformer playerInformer;


    // todo у игроков постоянно должен быть доступ к сбросу, чтобы они могли посмотреть что в нём
    @Override
    public void doAction(GameState gameState)
    {
        if (isResetEmpty(gameState))
            return;

        Player nowTurn = gameState.getNowTurn();
        var transmittedStrCardName = playerQuestioner.ask(nowTurn.getId(), WHICH_CARD_TO_TAKE);
        var transmittedCardName = CardName.fromString(transmittedStrCardName);

        var transmittedCard = GameStateUtils.getCardFromReset(gameState, transmittedCardName);

        gameState.getCardReset().remove(transmittedCard);
        nowTurn.addCard(transmittedCard);

        playerInformer.inform(nowTurn.getId(), CARD_RECEIVED, transmittedCard.getName().getWriting());
    }

    @Override
    public String getName() {
        return "steal card from reset";
    }
}
