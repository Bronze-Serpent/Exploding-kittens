package com.kittens.logic.action;

import com.kittens.logic.Utils;
import com.kittens.logic.action.player.interaction.PlayerInformer;
import com.kittens.logic.action.player.interaction.PlayerQuestioner;
import com.kittens.logic.action.sudden.SuddenInaction;
import com.kittens.logic.card.OrdinaryCard;
import com.kittens.logic.models.Player;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.kittens.logic.card.CardName.TACOCAT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;


@ExtendWith(MockitoExtension.class)
class StealCardFromResetTest
{
    @Mock
    private PlayerQuestioner playerQuestioner;

    @Mock
    private PlayerInformer playerInformer;

    @InjectMocks
    private StealCardFromReset stealCardFromReset;


    @Test
    public void shouldStealKnownCard()
    {
        var gameState = Utils.createGameState();
        Utils.set2PlayersWithCards(gameState);
        var oldGameState = Utils.copy(gameState);

        var stealCard = new OrdinaryCard(TACOCAT, new Inaction(), new Inaction(), new SuddenInaction());
        gameState.addToCardReset(stealCard);

        doReturn(TACOCAT.getWriting())
                .when(playerQuestioner).ask(new Player(1, null), PlayerQuestioner.Question.WHICH_CARD_TO_TAKE);

        stealCardFromReset.doAction(gameState);

        assertThat(gameState.getPlayerById(1).hasACard(stealCard.getName())).isTrue();

        assertThat(gameState.getCardDeck()).isEqualTo(oldGameState.getCardDeck());
        assertThat(gameState.getNowTurn()).isEqualTo(oldGameState.getNowTurn());
        assertThat(gameState.getStepQuantity()).isEqualTo(oldGameState.getStepQuantity());
        assertThat(gameState.getCardReset()).isEqualTo(oldGameState.getCardReset());

        gameState.getPlayerById(1).removeCard(TACOCAT);
        assertThat(gameState.getPlayersTurn().getSequence()).containsExactlyElementsOf(oldGameState.getPlayersTurn().getSequence());
    }
}
