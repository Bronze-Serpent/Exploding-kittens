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
class StealCardFromResetTest
{
    @Mock
    private PlayerQuestioner playerQuestioner;

    @InjectMocks
    private StealCardFromReset stealCardFromReset;


    @Test
    public void shouldStealKnownCard()
    {
        var gameState = Utils.createGameState();
        Utils.set2PlayersWithCards(gameState);
        var oldGameState = Utils.copy(gameState);

        var stealCard = new OrdinaryCard("testCard", new Inaction(), new Inaction(), new SuddenInaction());
        gameState.addToCardReset(stealCard);

        doReturn("testCard")
                .when(playerQuestioner).ask(1L, PlayerQuestioner.Question.WHICH_CARD_TO_TAKE);

        stealCardFromReset.doAction(gameState);

        assertThat(gameState.getPlayerById(1L).doesHeHaveCard(stealCard.getName())).isTrue();

        assertThat(gameState.getCardDeck()).isEqualTo(oldGameState.getCardDeck());
        assertThat(gameState.getNowTurn()).isEqualTo(oldGameState.getNowTurn());
        assertThat(gameState.getStepQuantity()).isEqualTo(oldGameState.getStepQuantity());
        assertThat(gameState.getCardReset()).isEqualTo(oldGameState.getCardReset());

        gameState.getPlayerById(1L).removeCard("testCard");
        assertThat(gameState.getPlayersTurn().getSourceList()).containsExactlyElementsOf(oldGameState.getPlayersTurn().getSourceList());
    }
}
