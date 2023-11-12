package com.kittens.action;

import com.kittens.Utils;
import com.kittens.action.player.interaction.PlayerQuestioner;
import com.kittens.action.sudden.SuddenInaction;
import com.kittens.card.CardName;
import com.kittens.card.OrdinaryCard;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.kittens.action.player.interaction.PlayerQuestioner.Question.WHERE_TO_HIDE;
import static com.kittens.card.CardName.DEFUSE;
import static com.kittens.card.CardName.EXPLODING_KITTEN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;


@ExtendWith(MockitoExtension.class)
class ExplodeOrDefuseTest
{
    @Mock
    private PlayerQuestioner playerQuestioner;

    @InjectMocks
    private ExplodeOrDefuse explodeOrDefuse;


    @Test
    public void shouldThrowException()
    {
        var gameState = Utils.createGameState();
        Utils.set2PlayersWithCards(gameState);

        AssertionsForClassTypes.assertThat(gameState.getPlayerById(1L).doesHeHaveCard(EXPLODING_KITTEN)).isFalse();
        assertThrows(RuntimeException.class, () -> explodeOrDefuse.doAction(gameState));
    }

    @Test
    public void shouldDefuseKitten()
    {
        doReturn(PlayerQuestioner.HideAnswer.FIRST.getAnswer())
                .when(playerQuestioner).ask(1L, WHERE_TO_HIDE);

        var gameState = Utils.createGameState();
        Utils.set2PlayersWithCards(gameState);
        var oldGameState = Utils.copy(gameState);

        var inaction = new Inaction();
        var suddenInaction = new SuddenInaction();

        var defuse = new OrdinaryCard(DEFUSE, inaction, inaction, suddenInaction);
        var exploding_cat = new OrdinaryCard(EXPLODING_KITTEN, inaction, inaction, suddenInaction);

        gameState.getPlayerById(1L).addCard(defuse);
        gameState.getPlayerById(1L).addCard(exploding_cat);

        explodeOrDefuse.doAction(gameState);

        Assertions.assertThat(gameState.getStepQuantity()).isEqualTo(oldGameState.getStepQuantity());
        assertEquals(gameState.getNowTurn(), oldGameState.getNowTurn());
        oldGameState.getCardDeck().add(exploding_cat);
        assertThat(gameState.getCardDeck()).isEqualTo(oldGameState.getCardDeck());
        oldGameState.getCardReset().add(defuse);
        assertThat(gameState.getCardReset()).isEqualTo(oldGameState.getCardReset());
        assertThat(gameState.getPlayersTurn().getSourceList()).containsExactlyElementsOf(oldGameState.getPlayersTurn().getSourceList());
    }

    @Test
    public void shouldExplodePlayer()
    {
        doReturn(PlayerQuestioner.HideAnswer.LAST.getAnswer())
                .when(playerQuestioner).ask(1L, WHERE_TO_HIDE);

        var gameState = Utils.createGameState();
        Utils.set2PlayersWithCards(gameState);
        var oldGameState = Utils.copy(gameState);

        var inaction = new Inaction();
        var suddenInaction = new SuddenInaction();
        var exploding_cat = new OrdinaryCard(EXPLODING_KITTEN, inaction, inaction, suddenInaction);

        gameState.getPlayerById(1L).addCard(exploding_cat);
        gameState.setStepQuantity(3);

        Assertions.assertThat(gameState.getPlayerById(1L).doesHeHaveCard(DEFUSE)).isFalse();
        explodeOrDefuse.doAction(gameState);

        Assertions.assertThat(gameState.getStepQuantity()).isEqualTo(1);
        assertEquals(gameState.getNowTurn(), oldGameState.getNowTurn());

        oldGameState.getCardDeck().add(0, exploding_cat);
        assertThat(gameState.getCardDeck()).isEqualTo(oldGameState.getCardDeck());

        oldGameState.getCardReset().addAll(oldGameState.getPlayerById(1L).getCards());
        assertThat(gameState.getCardReset()).isEqualTo(oldGameState.getCardReset());

        assertThat(gameState.getPlayersTurn().getSourceList()).containsExactlyElementsOf(List.of(oldGameState.getPlayerById(2L)));
    }
}
