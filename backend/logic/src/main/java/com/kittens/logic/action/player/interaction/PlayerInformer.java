package com.kittens.logic.action.player.interaction;

import com.kittens.logic.model.AbstractPlayer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


public interface PlayerInformer
{

    void inform(AbstractPlayer player, Informing informing, String msg);

    default void inform(AbstractPlayer player, Informing informing)
    {
        inform(player, informing, "");
    }


    @RequiredArgsConstructor
    enum Informing
    {
        SHOW_CARDS("show_cards"),
        CARD_RECEIVED("card_received"),
        CARD_STOLEN("card_stolen"),
        NUM_OF_PLAYER_CARDS("num_of_player_cards"),

        EXPLODED("exploded"),
        USED_DEFUSED_KITTEN("defused_kitten"),
        NO_SUCH_CARD("no_such_card");

        @Getter
        private final String writing;
    }
}
