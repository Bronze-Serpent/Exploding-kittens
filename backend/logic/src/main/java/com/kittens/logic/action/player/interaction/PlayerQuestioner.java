package com.kittens.logic.action.player.interaction;

import com.kittens.logic.model.AbstractPlayer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.kittens.logic.action.player.interaction.PlayerQuestioner.Question.HOW_TO_SHUFFLE;
import static com.kittens.logic.action.player.interaction.PlayerQuestioner.Question.WHERE_TO_HIDE;


public interface PlayerQuestioner
{

    String NO_RESPONSE = "no_response";

    Map<Question, List<? extends Answer>> answers = Map.of(
            HOW_TO_SHUFFLE, Arrays.stream(ShuffleAnswer.values()).toList(),
            WHERE_TO_HIDE, Arrays.stream(HideAnswer.values()).toList()
    );


    String ask(AbstractPlayer player, Question question);


    @RequiredArgsConstructor
    enum Question
    {
        HOW_TO_SHUFFLE("how_to_shuffle"),
        WHERE_TO_HIDE("where_to_hide"),
        WHICH_PLAYER("which_player"),
        WHICH_CARD_TO_GIVE("which_card_to_give"),
        WHICH_CARD_TO_TAKE("which_card_to_take"),
        WHICH_NUM_OF_CARD_TAKE("which_num_of_card_take");

        @Getter
        private final String writing;

    }


    interface Answer
    {
        String getAnswer();
    }

    @RequiredArgsConstructor
    enum ShuffleAnswer implements Answer
    {
        RANDOM ("random"),
        REVERSE ("reverse"),
        FIRST_CARD_TO_LAST("first_card_to_last");

        @Getter
        private final String answer;
    }

    @RequiredArgsConstructor
    enum HideAnswer implements Answer
    {
        FIRST("first"),
        SECOND("second"),
        THIRD("third"),
        FOURTH("fourth"),
        FIFTH("fifth"),
        LAST("last"),
        RANDOM("random");

        @Getter
        private final String answer;
    }
}
