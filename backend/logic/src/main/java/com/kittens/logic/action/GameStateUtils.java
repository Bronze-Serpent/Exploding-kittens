package com.kittens.logic.action;

import com.kittens.logic.GameState;
import com.kittens.logic.Player;
import com.kittens.logic.card.Card;
import com.kittens.logic.card.CardName;
import lombok.experimental.UtilityClass;

import java.util.List;

import static com.kittens.logic.card.CardName.EXPLODING_KITTEN;


@UtilityClass
class GameStateUtils
{

    static boolean doesAnyoneHaveACard(GameState gameState)
    {
        List<Player> players = gameState.getPlayersTurn().getSourceList().stream()
                .filter(player -> player.getCards().size() > 0)
                .toList();

        return gameState.getPlayersTurn().getSourceList().stream()
                .filter(player -> player.getCards().size() > 0)
                .toList().size() > 0;
    }


    static boolean isResetEmpty(GameState gameState)
    {
        return gameState.getCardReset().size() < 1;
    }

    static boolean isCardDeckEmpty(GameState gameState)
    {
        return gameState.getCardDeck().size() < 1;
    }


    static Card getCardFromReset(GameState gameState, CardName cardName)
    {
        return gameState.getCardReset().stream()
                .filter(cardFromReset -> cardFromReset.getName().equals(cardName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Карта с именем: " + cardName + " должна быть в сбросе."));
    }

    static Card getExplodingKittenFrom(Player player)
    {
        return player.getCards().stream()
                .filter(card -> card.getName().equals(EXPLODING_KITTEN))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("у игрока должна быть карта котёнка в этот момент"));
    }
}
