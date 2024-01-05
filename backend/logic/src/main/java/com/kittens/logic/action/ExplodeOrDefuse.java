package com.kittens.logic.action;

import com.kittens.logic.model.AbstractPlayer;
import com.kittens.logic.model.GameState;
import com.kittens.logic.action.player.interaction.PlayerInformer;
import com.kittens.logic.card.Card;
import com.kittens.logic.action.player.interaction.PlayerQuestioner;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.ThreadLocalRandom;

import static com.kittens.logic.action.GameStateUtils.getExplodingKittenFrom;
import static com.kittens.logic.action.player.interaction.PlayerInformer.Informing.USED_DEFUSED_KITTEN;
import static com.kittens.logic.action.player.interaction.PlayerInformer.Informing.EXPLODED;
import static com.kittens.logic.action.player.interaction.PlayerQuestioner.*;
import static com.kittens.logic.card.CardName.*;
import static com.kittens.logic.card.CardName.DEFUSE;


@RequiredArgsConstructor
public class ExplodeOrDefuse implements Action
{

    private final PlayerQuestioner playerQuestioner;
    private final PlayerInformer playerInformer;


    @Override
    public void doAction(GameState gameState)
    {
        AbstractPlayer player = gameState.getNowTurn();
        var explodingCatCard = getExplodingKittenFrom(player);

        if (player.hasACard(DEFUSE))
        {
            var defusedCard = player.removeCard(DEFUSE);

            gameState.addToCardReset(defusedCard);
            playerInformer.inform(player, USED_DEFUSED_KITTEN);

            player.removeCard(EXPLODING_KITTEN);
            hideTheKitten(explodingCatCard, gameState);
        }
        else
        {
            player.removeCard(EXPLODING_KITTEN);
            hideTheKitten(explodingCatCard, gameState);

            gameState.addToCardReset(player.getCards());
            player.getCards().clear();

            gameState.removePlayer(player);
            playerInformer.inform(player, EXPLODED);

            gameState.setStepQuantity(1);
        }

    }

    @Override
    public String getName() {
        return "explode_or_defuse";
    }


    private void hideTheKitten(Card kittenCard, GameState gameState)
    {
        var placeToHide = playerQuestioner.ask(gameState.getNowTurn(), Question.WHERE_TO_HIDE);
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
