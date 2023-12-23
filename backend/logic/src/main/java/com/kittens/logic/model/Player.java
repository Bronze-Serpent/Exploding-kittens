package com.kittens.logic.model;

import com.kittens.logic.card.Card;
import java.util.List;


public class Player extends AbstractPlayer
{
    public Player(Long id, List<Card> cards) {
        super(id, cards);
    }
}
