package com.kittens.logic.service;

import com.kittens.logic.model.AbstractPlayer;
import com.kittens.logic.model.GameState;
import com.kittens.logic.model.LoopingList;
import com.kittens.logic.card.Card;
import com.kittens.logic.card.CardName;
import com.kittens.logic.model.Player;
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


    public void initGame(List<Card> cards,
                         List<AbstractPlayer> players,
                         int numOfCardsPlayersHave,
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

        // раздача всем игрокам по numOfCardsPlayersHave - 1 карт
        List<Card> gamingCards = extractAllGamingCards(cardNameToCard);
        Collections.shuffle(gamingCards);
        distributeGamingCards(players, gamingCards, numOfCardsPlayersHave - 1);

        // добавляем все оставшиеся карты обезвредь и карты взрывных котят в колоду
        gamingCards.addAll(cardNameToCard.get(DEFUSE));
        addExplodingKittens(gamingCards, cardNameToCard.get(EXPLODING_KITTEN), loopingList.size());

        Collections.shuffle(gamingCards);

        emptyGameState.setPlayersTurn(loopingList);
        emptyGameState.setNowTurn(firstPlayer);
        emptyGameState.setStepQuantity(1);
        emptyGameState.setCardReset(new ArrayList<>());
        emptyGameState.setCardDeck(gamingCards);
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


    public void playCard(GameState gameState, Long whoPlayed, Card playerCard, Map<Long, List<Card>> suddenCards)
    {
        cardHandler.playCard(gameState, whoPlayed, playerCard, suddenCards);
    }


    public void playCombination(GameState gameState, List<Card> combination)
    {
        combinationHandler.playCombination(gameState, combination);

        var nowTurn = gameState.getNowTurn();
        for (Card combCard : combination)
            nowTurn.removeCard(combCard.getName());
    }


    private void addExplodingKittens(List<Card> gamingCards, List<Card> explodingKittens, int playerQuantity)
    {
        // число игроков - 1 карт
        if (explodingKittens.size() < playerQuantity - 1)
            throw new RuntimeException("Недостаточное число карт взрывных котят. Карт: " + explodingKittens.size()
                    + " .Игроков: " + playerQuantity);

        else
            gamingCards.addAll(explodingKittens.subList(0, playerQuantity - 1));
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
