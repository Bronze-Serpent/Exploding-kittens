package com.kittens.action;

import com.kittens.GameState;
import com.kittens.Player;
import com.kittens.action.player.interaction.PlayerInformer;
import com.kittens.card.Card;
import com.kittens.action.player.interaction.PlayerQuestioner;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.ThreadLocalRandom;

import static com.kittens.action.GameStateUtils.getExplodingKittenFrom;
import static com.kittens.action.player.interaction.PlayerInformer.Informing.DEFUSED_KITTEN;
import static com.kittens.action.player.interaction.PlayerInformer.Informing.EXPLODED;
import static com.kittens.action.player.interaction.PlayerQuestioner.*;
import static com.kittens.card.CardName.*;
import static com.kittens.card.CardName.DEFUSE;


@RequiredArgsConstructor
public class ExplodeOrDefuse implements Action
{

    private final PlayerQuestioner playerQuestioner;
    private final PlayerInformer playerInformer;


    @Override
    public void doAction(GameState gameState)
    {
        Player player = gameState.getNowTurn();
        var explodingCatCard = getExplodingKittenFrom(player);

        if (player.hasACard(DEFUSE))
        {
            var defusedCard = player.removeCard(DEFUSE);

            gameState.addToCardReset(defusedCard);
            playerInformer.inform(player.getId(), DEFUSED_KITTEN);

            player.removeCard(EXPLODING_KITTEN);
            hideTheKitten(explodingCatCard, gameState);
        }
        else
        {

            player.removeCard(EXPLODING_KITTEN);
            hideTheKitten(explodingCatCard, gameState);

            gameState.addToCardReset(player.getCards());
            player.getCards().clear();

            gameState.removePlayer(player.getId());
            playerInformer.inform(player.getId(), EXPLODED);

            gameState.setStepQuantity(1);
        }

    }

    @Override
    public String getName() {
        return "explode or defuse";
    }


    private void hideTheKitten(Card kittenCard, GameState gameState)
    {
        var placeToHide = playerQuestioner.ask(gameState.getNowTurn().getId(), Question.WHERE_TO_HIDE);
        var deckSize = gameState.getCardDeck().size();

        if (placeToHide.equals(NO_RESPONSE))
        {
            gameState.getCardDeck().add(ThreadLocalRandom.current().nextInt(0, deckSize), kittenCard);
            return;
        }

        var hideAnswer = HideAnswer.valueOf(placeToHide.toUpperCase());
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
