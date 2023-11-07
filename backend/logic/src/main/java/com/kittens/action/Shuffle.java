package com.kittens.action;

import com.kittens.GameState;
import com.kittens.action.player.interaction.PlayerQuestioner;
import lombok.RequiredArgsConstructor;

import java.util.Collections;


@RequiredArgsConstructor
public class Shuffle implements Action
{

    private final PlayerQuestioner playerQuestioner;


    @Override
    public GameState doAction(GameState gameState)
    {
        var playerChoice = playerQuestioner.ask(gameState.getNowTurn().getId(), PlayerQuestioner.Question.HOW_TO_SHUFFLE);

        if (playerChoice.equals(PlayerQuestioner.NO_RESPONSE))
            return gameState;

        var shuffleAnswer = PlayerQuestioner.ShuffleAnswer.valueOf(playerChoice);

        var cardDeck = gameState.getCardDeck();
        switch (shuffleAnswer) {
            case RANDOM ->
                Collections.shuffle(cardDeck);
            case REVERSE ->
                Collections.reverse(cardDeck);
            case FIRST_CARD_TO_LAST ->
                Collections.swap(cardDeck, 0, cardDeck.size() - 1);
        }

        return gameState;
    }
}
