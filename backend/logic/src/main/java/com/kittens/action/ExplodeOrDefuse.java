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
    public GameState doAction(GameState gameState)
    {
        GameState newGameState = new GameState(gameState);
        Player player = gameState.getNowTurn();
        var explodingCatCard = player.getCards().stream()
                .filter(card -> card.getName().equals("exploding cat"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("у игрока должна быть карта котёнка в этот момент"));

        if (player.isThereCard("defuse"))
        {
            var defusedCard = player.removeCard("defuse");
            player.removeCard("exploding cat");
            newGameState.addToCardReset(defusedCard);
            hideTheKitten(explodingCatCard, gameState);
        }
        else
        {
            player.removeCard("exploding cat");
            newGameState.addToCardReset(player.getCards());
            player.getCards().clear();
            hideTheKitten(explodingCatCard, gameState);
            newGameState.removePlayer(player.getId());
        }

        return newGameState;
    }


    private void hideTheKitten(Card kittenCard, GameState gameState)
    {
        var placeToHide = playerQuestioner.ask(gameState.getNowTurn().getId(), PlayerQuestioner.Question.WHERE_TO_HIDE);

        if (placeToHide.equals(PlayerQuestioner.NO_RESPONSE))
            return;

        var hideAnswer = PlayerQuestioner.HideAnswer.valueOf(placeToHide);

        var deckSize = gameState.getCardDeck().size();
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
