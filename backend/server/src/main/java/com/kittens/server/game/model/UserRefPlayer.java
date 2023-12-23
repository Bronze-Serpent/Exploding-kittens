package com.kittens.server.game.model;

import com.kittens.logic.card.Card;
import com.kittens.logic.model.AbstractPlayer;
import lombok.Getter;

import java.util.List;


public class UserRefPlayer extends AbstractPlayer
{
    @Getter
    private final Long userId;

    public UserRefPlayer(long id, List<Card> cards, Long userId)
    {
        super(id, cards);
        this.userId = userId;
    }
}
