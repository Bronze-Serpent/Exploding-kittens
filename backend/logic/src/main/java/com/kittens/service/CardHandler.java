package com.kittens.service;

import com.kittens.GameState;
import com.kittens.card.Card;

import java.util.List;


public interface CardHandler
{
    void playCard(GameState gameState, Card playerCard, List<Card> suddenCards);
}
