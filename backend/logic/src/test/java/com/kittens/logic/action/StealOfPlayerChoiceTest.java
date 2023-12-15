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

import static com.kittens.logic.action.player.interaction.PlayerQuestioner.Question.WHICH_CARD_TO_GIVE;
import static com.kittens.logic.action.player.interaction.PlayerQuestioner.Question.WHICH_PLAYER;
import static com.kittens.logic.card.CardName.CATTERMELON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;


@ExtendWith(MockitoExtension.class)
class StealOfPlayerChoiceTest
{
    @Mock
    private PlayerQuestioner playerQuestioner;

    @Mock
    private PlayerInformer playerInformer;

    @InjectMocks
    private StealOfPlayerChoice stealOfPlayerChoice;

    @Test
    public void shouldStealOfPlayerChoice()
    {
        var gameState = Utils.createGameState();
        Utils.set2PlayersWithCards(gameState);
        var oldGameState = Utils.copy(gameState);

        var stealCard = new OrdinaryCard(CATTERMELON, new Inaction(), new Inaction(), new SuddenInaction());
        gameState.getPlayerById(2).addCard(stealCard);

        doReturn("2")
                .when(playerQuestioner).ask(new Player(1, null), WHICH_PLAYER);
        doReturn(CATTERMELON.getWriting())
                .when(playerQuestioner).ask(new Player(2, null), WHICH_CARD_TO_GIVE);

        stealOfPlayerChoice.doAction(gameState);

        assertThat(gameState.getPlayerById(1).hasACard(stealCard.getName())).isTrue();
        assertThat(gameState.getPlayerById(2).hasACard(stealCard.getName())).isFalse();

        assertThat(gameState.getCardDeck()).isEqualTo(oldGameState.getCardDeck());
        assertThat(gameState.getCardReset()).isEqualTo(oldGameState.getCardReset());
        assertThat(gameState.getNowTurn()).isEqualTo(oldGameState.getNowTurn());
        assertThat(gameState.getStepQuantity()).isEqualTo(oldGameState.getStepQuantity());

        gameState.getPlayerById(1).removeCard(CATTERMELON);
        assertThat(gameState.getPlayersTurn().getSequence()).containsExactlyElementsOf(oldGameState.getPlayersTurn().getSequence());
    }
}