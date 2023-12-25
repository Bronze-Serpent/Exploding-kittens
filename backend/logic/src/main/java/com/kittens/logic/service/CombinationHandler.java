package com.kittens.logic.service;

import com.kittens.logic.card.Card;
import com.kittens.logic.model.GameState;

import java.util.List;


public interface CombinationHandler
{
    void playCombination(GameState gameState, Long whoPlayed, List<Card> cards);
}
