package com.kittens.logic.service;

import com.kittens.logic.card.Card;
import com.kittens.logic.model.GameState;

import java.util.List;
import java.util.Map;


public interface CardHandler
{
    void playCard(GameState gameState, Long whoPlayed, Card playerCard, Map<Long, List<Card>> suddenCards);
}
