package com.kittens.action;

import com.kittens.GameState;
import com.kittens.Player;
import com.kittens.card.Card;
import com.kittens.action.player.interaction.PlayerQuestioner;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.ThreadLocalRandom;


@RequiredArgsConstructor
public class ExplodeOrDefuse implements Action
{

    private final PlayerQuestioner playerQuestioner;


    @Override
    public void doAction(GameState gameState)
    {
        Player player = gameState.getNowTurn();
        var explodingCatCard = player.getCards().stream()
                .filter(card -> card.getName().equals("exploding cat"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("у игрока должна быть карта котёнка в этот момент"));

        if (player.doesHeHaveCard("defuse"))
        {
            var defusedCard = player.removeCard("defuse");
            player.removeCard("exploding cat");
            gameState.addToCardReset(defusedCard);
            hideTheKitten(explodingCatCard, gameState);
        }
        else
        {
            player.removeCard("exploding cat");
            gameState.addToCardReset(player.getCards());
            player.getCards().clear();
            hideTheKitten(explodingCatCard, gameState);
            gameState.removePlayer(player.getId());
            gameState.setStepQuantity(1);
        }

    }


    private void hideTheKitten(Card kittenCard, GameState gameState)
    {
        var placeToHide = playerQuestioner.ask(gameState.getNowTurn().getId(), PlayerQuestioner.Question.WHERE_TO_HIDE);
        var deckSize = gameState.getCardDeck().size();

        if (placeToHide.equals(PlayerQuestioner.NO_RESPONSE))
        {
            gameState.getCardDeck().add(ThreadLocalRandom.current().nextInt(0, deckSize), kittenCard);
            return;
        }

        var hideAnswer = PlayerQuestioner.HideAnswer.valueOf(placeToHide.toUpperCase());
        switch (hideAnswer) {
            case FIRST ->
                gameState.getCardDeck().add(kittenCard);
            case SECOND ->
                gameState.getCardDeck().add(deckSize - 1, kittenCard);
            case THIRD ->
                gameState.getCardDeck().add(deckSize - 2, kittenCard);
            case FOURTH ->
                gameState.getCardDeck().add(deckSize - 3, kittenCard);
            case FIFTH ->
                gameState.getCardDeck().add(deckSize - 4, kittenCard);
            case LAST ->
                gameState.getCardDeck().add(0, kittenCard);
            case RANDOM ->
                gameState.getCardDeck().add(ThreadLocalRandom.current().nextInt(0, deckSize), kittenCard);
        }
    }
}
