package com.kittens.action;

import com.kittens.Utils;
import com.kittens.action.player.interaction.PlayerQuestioner;
import com.kittens.action.sudden.SuddenInaction;
import com.kittens.card.OrdinaryCard;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;


@ExtendWith(MockitoExtension.class)
class StealOfPlayerChoiceTest
{
    @Mock
    private PlayerQuestioner playerQuestioner;

    @InjectMocks
    private StealOfPlayerChoice stealOfPlayerChoice;

    @Test
    public void shouldStealOfPlayerChoice()
    {
        var gameState = Utils.createGameState();
        Utils.set2PlayersWithCards(gameState);
        var oldGameState = Utils.copy(gameState);

        var stealCard = new OrdinaryCard("testCard", new Inaction(), new Inaction(), new SuddenInaction());
        gameState.getPlayerById(2L).addCard(stealCard);

        doReturn("2")
                .when(playerQuestioner).ask(1L, PlayerQuestioner.Question.WHICH_PLAYER);
        doReturn("testCard")
                .when(playerQuestioner).ask(2L, PlayerQuestioner.Question.WHICH_CARD_TO_GIVE);

        stealOfPlayerChoice.doAction(gameState);

        assertThat(gameState.getPlayerById(1L).doesHeHaveCard(stealCard.getName())).isTrue();
        assertThat(gameState.getPlayerById(2L).doesHeHaveCard(stealCard.getName())).isFalse();

        assertThat(gameState.getCardDeck()).isEqualTo(oldGameState.getCardDeck());
        assertThat(gameState.getCardReset()).isEqualTo(oldGameState.getCardReset());
        assertThat(gameState.getNowTurn()).isEqualTo(oldGameState.getNowTurn());
        assertThat(gameState.getStepQuantity()).isEqualTo(oldGameState.getStepQuantity());

        gameState.getPlayerById(1L).removeCard("testCard");
        assertThat(gameState.getPlayersTurn().getSourceList()).containsExactlyElementsOf(oldGameState.getPlayersTurn().getSourceList());
    }
}