package com.kittens.action;

import com.kittens.Utils;
import com.kittens.action.player.interaction.PlayerQuestioner;
import com.kittens.action.sudden.SuddenInaction;
import com.kittens.card.CardName;
import com.kittens.card.OrdinaryCard;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.kittens.action.player.interaction.PlayerQuestioner.Question.WHICH_CARD_TO_TAKE;
import static com.kittens.action.player.interaction.PlayerQuestioner.Question.WHICH_PLAYER;
import static com.kittens.card.CardName.BEARDCAT;
import static com.kittens.card.CardName.TACOCAT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;


@ExtendWith(MockitoExtension.class)
class StealKnownCardTest
{
    @Mock
    private PlayerQuestioner playerQuestioner;

    @InjectMocks
    private StealKnownCard stealKnownCard;


    @Test
    public void shouldStealKnownCard()
    {
        var gameState = Utils.createGameState();
        Utils.set2PlayersWithCards(gameState);
        var oldGameState = Utils.copy(gameState);

        var stealCard = new OrdinaryCard(TACOCAT, new Inaction(), new Inaction(), new SuddenInaction());
        gameState.getPlayerById(2L).addCard(stealCard);

        doReturn("2")
                .when(playerQuestioner).ask(1L, WHICH_PLAYER);
        doReturn(TACOCAT.getWriting())
                .when(playerQuestioner).ask(1L, WHICH_CARD_TO_TAKE);

        stealKnownCard.doAction(gameState);

        assertThat(gameState.getPlayerById(1L).doesHeHaveCard(stealCard.getName())).isTrue();
        assertThat(gameState.getPlayerById(2L).doesHeHaveCard(stealCard.getName())).isFalse();

        assertThat(gameState.getCardDeck()).isEqualTo(oldGameState.getCardDeck());
        assertThat(gameState.getCardReset()).isEqualTo(oldGameState.getCardReset());
        assertThat(gameState.getNowTurn()).isEqualTo(oldGameState.getNowTurn());
        assertThat(gameState.getStepQuantity()).isEqualTo(oldGameState.getStepQuantity());

        gameState.getPlayerById(1L).removeCard(TACOCAT);
        assertThat(gameState.getPlayersTurn().getSourceList()).containsExactlyElementsOf(oldGameState.getPlayersTurn().getSourceList());
    }

    @Test
    public void shouldDoNothing()
    {
        var gameState = Utils.createGameState();
        Utils.set2PlayersWithCards(gameState);
        var oldGameState = Utils.copy(gameState);

        doReturn("2")
                .when(playerQuestioner).ask(1L, WHICH_PLAYER);
        doReturn("tacocat")
                .when(playerQuestioner).ask(1L, WHICH_CARD_TO_TAKE);

        stealKnownCard.doAction(gameState);

        assertThat(gameState.getPlayerById(2L).doesHeHaveCard(TACOCAT)).isFalse();

        assertThat(gameState.getCardDeck()).isEqualTo(oldGameState.getCardDeck());
        assertThat(gameState.getCardReset()).isEqualTo(oldGameState.getCardReset());
        assertThat(gameState.getNowTurn()).isEqualTo(oldGameState.getNowTurn());
        assertThat(gameState.getStepQuantity()).isEqualTo(oldGameState.getStepQuantity());
        assertThat(gameState.getPlayersTurn().getSourceList()).containsExactlyElementsOf(oldGameState.getPlayersTurn().getSourceList());
    }
}
