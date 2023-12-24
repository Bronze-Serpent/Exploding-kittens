package com.kittens.logic.action;

import com.kittens.logic.Utils;
import com.kittens.logic.action.player.interaction.PlayerInformer;
import com.kittens.logic.action.player.interaction.PlayerQuestioner;
import com.kittens.logic.action.sudden.SuddenInaction;
import com.kittens.logic.card.OrdinaryCard;
import com.kittens.logic.model.Player;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.kittens.logic.action.player.interaction.PlayerQuestioner.Question.WHICH_CARD_TO_TAKE;
import static com.kittens.logic.action.player.interaction.PlayerQuestioner.Question.WHICH_PLAYER;
import static com.kittens.logic.card.CardName.TACOCAT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;


@ExtendWith(MockitoExtension.class)
class StealKnownCardTest
{
    @Mock
    private PlayerQuestioner playerQuestioner;

    @Mock
    private PlayerInformer playerInformer;

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
                .when(playerQuestioner).ask(new Player(1L, null), WHICH_PLAYER);
        doReturn(TACOCAT.getWriting())
                .when(playerQuestioner).ask(new Player(1L, null), WHICH_CARD_TO_TAKE);

        stealKnownCard.doAction(gameState);

        assertThat(gameState.getPlayerById(1L).hasACard(stealCard.getName())).isTrue();
        assertThat(gameState.getPlayerById(2L).hasACard(stealCard.getName())).isFalse();

        assertThat(gameState.getCardDeck()).isEqualTo(oldGameState.getCardDeck());
        assertThat(gameState.getCardReset()).isEqualTo(oldGameState.getCardReset());
        assertThat(gameState.getNowTurn()).isEqualTo(oldGameState.getNowTurn());
        assertThat(gameState.getStepQuantity()).isEqualTo(oldGameState.getStepQuantity());

        gameState.getPlayerById(1L).removeCard(TACOCAT);
        assertThat(gameState.getPlayersTurn().getElements()).containsExactlyElementsOf(oldGameState.getPlayersTurn().getElements());
    }

    @Test
    public void shouldDoNothing()
    {
        var gameState = Utils.createGameState();
        Utils.set2PlayersWithCards(gameState);
        var oldGameState = Utils.copy(gameState);

        doReturn("2")
                .when(playerQuestioner).ask(new Player(1L, null), WHICH_PLAYER);
        doReturn("tacocat")
                .when(playerQuestioner).ask(new Player(1L, null), WHICH_CARD_TO_TAKE);

        stealKnownCard.doAction(gameState);

        assertThat(gameState.getPlayerById(2L).hasACard(TACOCAT)).isFalse();

        assertThat(gameState.getCardDeck()).isEqualTo(oldGameState.getCardDeck());
        assertThat(gameState.getCardReset()).isEqualTo(oldGameState.getCardReset());
        assertThat(gameState.getNowTurn()).isEqualTo(oldGameState.getNowTurn());
        assertThat(gameState.getStepQuantity()).isEqualTo(oldGameState.getStepQuantity());
        assertThat(gameState.getPlayersTurn().getElements()).containsExactlyElementsOf(oldGameState.getPlayersTurn().getElements());
    }
}
