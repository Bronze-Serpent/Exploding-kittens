package com.kittens.action;

import com.kittens.GameState;
import com.kittens.card.Card;
import com.kittens.action.player.interaction.PlayerQuestioner;
import lombok.RequiredArgsConstructor;

import static com.kittens.action.player.interaction.PlayerQuestioner.Question.WHICH_CARD_TO_TAKE;


@RequiredArgsConstructor
public class StealCardFromReset implements Action
{

    private final PlayerQuestioner playerQuestioner;


    @Override
    public GameState doAction(GameState gameState)
    {
        // TODO: 06.11.2023 наверное, через PlayerInformer отправлять какие карты вообще есть в сбросе (сет от сброса карт)

        var transmittedCardName = playerQuestioner.ask(gameState.getNowTurn().getId(), WHICH_CARD_TO_TAKE);

        var transmittedCard = gameState.getCardReset().stream()
                .filter(cardFromReset -> cardFromReset.getName().equals(transmittedCardName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Карта с именем: " + transmittedCardName + " должна быть в сбросе."));

        gameState.getCardReset().remove(transmittedCard);
        gameState.getNowTurn().addCard(transmittedCard);

        return gameState;
    }
}
