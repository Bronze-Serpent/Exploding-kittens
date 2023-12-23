package com.kittens.logic.service;

import com.kittens.logic.card.Card;
import com.kittens.logic.model.GameState;

import java.util.List;


public interface CardHandler
{
    void playCard(GameState gameState, Card playerCard, List<Card> suddenCards);
}
