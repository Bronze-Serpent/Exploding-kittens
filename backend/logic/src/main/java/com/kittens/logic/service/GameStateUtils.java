package com.kittens.logic.service;

import com.kittens.logic.GameState;
import com.kittens.logic.LoopingList;
import com.kittens.logic.Player;
import com.kittens.logic.card.Card;
import com.kittens.logic.card.CardName;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.kittens.logic.card.CardName.DEFUSE;
import static com.kittens.logic.card.CardName.EXPLODING_KITTEN;


@RequiredArgsConstructor
public class GameStateUtils
{

    private final CardHandler cardHandler;
    private final CombinationHandler combinationHandler;


    // TODO: 02.12.2023 часть правил игры всё же будет прописана здесь
    //  (Что у игроков по 1 карте обезвредь.Что карты взрывных котят искл. и добавляются после раздачи).
    //  Это норм? Остальное - сколько и каких карт, какие действия у карт и т.д. в БД
    public GameState initGame(Map<CardName, List<Card>> cards, List<Player> players, int numOfCards, Player firstPlayer)
    {
        if (players.stream()
                .filter(player -> player.getCards().size() > 0)
                .toList()
                .size() > 0)
            throw new RuntimeException("У переданных игроков не должно быть карт.");

        // раздача по 1 обезвредь
        distributeDefuseCard(players, cards.get(DEFUSE));

        // раздача всем игрокам по numOfCards - 1 карт
        List<Card> gamingCards = extractAllGamingCards(cards);
        Collections.shuffle(gamingCards);
        distributeGamingCards(players, gamingCards, numOfCards - 1);

        gamingCards.addAll(cards.get(DEFUSE));
        gamingCards.addAll(cards.get(EXPLODING_KITTEN));

        return new GameState(
                new LoopingList<>(players),
                gamingCards,
                new ArrayList<>(),
                firstPlayer,
                1);
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

    private void distributeGamingCards(List<Player> players, List<Card> gamingCards, int numOfCards)
    {
        for (Player player : players)
            for (int i = 0; i < numOfCards; i++)
                player.addCard(gamingCards.remove(gamingCards.size() - 1));
    }


    private void distributeDefuseCard(List<Player> players, List<Card> defuseCards)
    {
        for (Player player : players)
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
}
