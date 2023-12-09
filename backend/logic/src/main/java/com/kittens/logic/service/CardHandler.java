package com.kittens.logic.service;

import com.kittens.logic.GameState;
import com.kittens.logic.card.Card;

import java.util.List;


public interface CardHandler
{
    void playCard(GameState gameState, Card playerCard, List<Card> suddenCards);
}
