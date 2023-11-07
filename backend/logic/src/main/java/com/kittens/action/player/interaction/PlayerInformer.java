package com.kittens.action.player.interaction;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


public interface PlayerInformer
{

    void inform(Long playerId, Informing informing, String msg);


    @RequiredArgsConstructor
    enum Informing
    {
        SHOW_CARDS("show_cards");

        @Getter
        private final String writing;
    }
}
