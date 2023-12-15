package com.kittens.logic.action;

import com.kittens.logic.action.player.interaction.PlayerQuestioner;
import com.kittens.logic.models.GameState;
import lombok.RequiredArgsConstructor;

import java.util.Collections;

import static com.kittens.logic.action.player.interaction.PlayerQuestioner.Question.HOW_TO_SHUFFLE;


@RequiredArgsConstructor
public class Shuffle implements Action
{
    private final PlayerQuestioner playerQuestioner;


    @Override
    public void doAction(GameState gameState)
    {
        if (GameStateUtils.isCardDeckEmpty(gameState))
            return;

        var playerChoice = playerQuestioner.ask(gameState.getNowTurn(), HOW_TO_SHUFFLE);

        if (playerChoice.equals(PlayerQuestioner.NO_RESPONSE))
            return;

        var shuffleAnswer = PlayerQuestioner.ShuffleAnswer.valueOf(playerChoice.toUpperCase());

        var cardDeck = gameState.getCardDeck();
        switch (shuffleAnswer) {
            case RANDOM ->
                Collections.shuffle(cardDeck);
            case REVERSE ->
                Collections.reverse(cardDeck);
            case FIRST_CARD_TO_LAST ->
                Collections.swap(cardDeck, 0, cardDeck.size() - 1);
        }
    }

    @Override
    public String getName() {
        return "shuffle";
    }
}
