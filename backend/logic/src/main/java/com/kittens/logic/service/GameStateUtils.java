package com.kittens.logic.service;

import com.kittens.logic.models.AbstractPlayer;
import com.kittens.logic.models.GameState;
import com.kittens.logic.models.LoopingList;
import com.kittens.logic.card.Card;
import com.kittens.logic.card.CardName;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

import static com.kittens.logic.card.CardName.DEFUSE;
import static com.kittens.logic.card.CardName.EXPLODING_KITTEN;


@RequiredArgsConstructor
public class GameStateUtils
{

    private final CardHandler cardHandler;
    private final CombinationHandler combinationHandler;


    // TODO: 15.12.2023 сделать добавление карт котят в зависимости от количества игроков
    public void initGame(List<Card> cards,
                         List<AbstractPlayer> players,
                         int numOfCards,
                         AbstractPlayer firstPlayer,
                         GameState emptyGameState,
                         LoopingList<AbstractPlayer> loopingList)
    {
        if (players.stream()
                .filter(player -> player.getCards().size() > 0)
                .toList()
                .size() > 0)
            throw new RuntimeException("У переданных игроков не должно быть карт.");

        Map<CardName, List<Card>> cardNameToCard = listCardsToMap(cards);
        // раздача по 1 обезвредь
        distributeDefuseCard(players, cardNameToCard.get(DEFUSE));

        // раздача всем игрокам по numOfCards - 1 карт
        List<Card> gamingCards = extractAllGamingCards(cardNameToCard);
        Collections.shuffle(gamingCards);
        distributeGamingCards(players, gamingCards, numOfCards - 1);

        gamingCards.addAll(cardNameToCard.get(DEFUSE));
        gamingCards.addAll(cardNameToCard.get(EXPLODING_KITTEN));


        emptyGameState.setNowTurn(firstPlayer);
        emptyGameState.setStepQuantity(1);
        emptyGameState.setCardReset(new ArrayList<>());
        emptyGameState.setCardDeck(gamingCards);
        emptyGameState.setPlayersTurn(loopingList);
    }


    public void addNewCardToPlayer(GameState gameState)
    {
        var movesPlayer = gameState.getNowTurn();

        var receivedCard = gameState.getCardDeck().remove(gameState.getCardDeck().size() - 1);
        movesPlayer.addCard(receivedCard);
        var gettingAction = receivedCard.getGettingAction();
        gettingAction.doAction(gameState);
    }


    public void changeMove(GameState newGameState)
    {
        if (newGameState.getStepQuantity() == 1)
            newGameState.setNowTurn(newGameState.getPlayersTurn().next());
        else
            newGameState.setStepQuantity(newGameState.getStepQuantity() - 1);
    }


    public void playCard(GameState gameState, Card playerCard, List<Card> suddenCards)
    {
        cardHandler.playCard(gameState, playerCard, suddenCards);
        gameState.getNowTurn().removeCard(playerCard.getName());
    }


    public void playCombination(GameState gameState, List<Card> combination)
    {
        combinationHandler.playCombination(gameState, combination);

        var nowTurn = gameState.getNowTurn();
        for (Card combCard : combination)
            nowTurn.removeCard(combCard.getName());
    }

    private void distributeGamingCards(List<AbstractPlayer> players, List<Card> gamingCards, int numOfCards)
    {
        for (AbstractPlayer player : players)
            for (int i = 0; i < numOfCards; i++)
                player.addCard(gamingCards.remove(gamingCards.size() - 1));
    }


    private void distributeDefuseCard(List<AbstractPlayer> players, List<Card> defuseCards)
    {
        for (AbstractPlayer player : players)
            player.addCard(defuseCards.remove(defuseCards.size() - 1));
    }


    // все карты, кроме обезвредь и взрывных котят.
    private List<Card> extractAllGamingCards(Map<CardName, List<Card>> cards)
    {
        List<Card> allGamingCards = new ArrayList<>();
        for (Map.Entry<CardName, List<Card>> entry : cards.entrySet())
            if (!(entry.getKey() == EXPLODING_KITTEN ||
                    entry.getKey() == DEFUSE))
                allGamingCards.addAll(entry.getValue());

        return allGamingCards;
    }


    private Map<CardName, List<Card>> listCardsToMap(List<Card> cards)
    {
        Map<CardName, List<Card>> cardNameToCard = new HashMap<>();
        for (CardName name : CardName.values())
        {
            List<Card> cardsWithName = cards.stream()
                    .filter(card -> card.getName() == name)
                    .collect(Collectors.toList());

            cardNameToCard.put(name, cardsWithName);
        }

        return cardNameToCard;
    }
}
