package com.kittens.service;

import com.kittens.GameState;
import com.kittens.card.Card;

import java.util.List;


public interface CombinationHandler
{
    void playCombination(GameState gameState, List<Card> cards);
}
