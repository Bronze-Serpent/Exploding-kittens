package com.kittens.logic.service;

import com.kittens.logic.GameState;
import com.kittens.logic.card.Card;

import java.util.List;


public interface CombinationHandler
{
    void playCombination(GameState gameState, List<Card> cards);
}
