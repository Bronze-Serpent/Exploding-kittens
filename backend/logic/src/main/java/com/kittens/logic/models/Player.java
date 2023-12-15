package com.kittens.logic.models;

import com.kittens.logic.card.Card;
import java.util.List;


public class Player extends AbstractPlayer
{
    public Player(int id, List<Card> cards) {
        super(id, cards);
    }
}
