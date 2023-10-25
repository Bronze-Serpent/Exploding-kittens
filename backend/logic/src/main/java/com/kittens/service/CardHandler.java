package com.kittens.service;

import com.kittens.GameState;
import com.kittens.card.Card;

import java.util.List;


public interface CardHandler
{
    GameState playCard(GameState oldGameState, Card playerCard, List<Card> suddenCards);
}
