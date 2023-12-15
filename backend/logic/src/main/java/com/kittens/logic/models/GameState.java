package com.kittens.logic.models;

import com.kittens.logic.card.Card;

import java.util.List;


/**
 * Реализации должны возвращать ссылку именно на содержащийся объекты, а не на их копии
 * т.к. посредством такого доступа к ним их его могут изменять
 */
public interface GameState
{
    void addToCardReset(Card card);

    void addToCardReset(List<Card> cards);

    void removePlayer(AbstractPlayer player);

    AbstractPlayer getPlayerById(int playerId);

    AbstractPlayer getNowTurn();

    void setNowTurn(AbstractPlayer nowTurn);

    int getStepQuantity();

    void setStepQuantity(int quantity);

    List<Card> getCardDeck();

    void setCardDeck(List<Card> cards);

    List<Card> getCardReset();

    void setCardReset(List<Card> cards);

    LoopingList<AbstractPlayer> getPlayersTurn();

    void setPlayersTurn(LoopingList<AbstractPlayer> loopingList);
}
