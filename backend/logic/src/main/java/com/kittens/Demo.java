package com.kittens;

import com.kittens.card.Action;
import com.kittens.card.Card;
import com.kittens.card.OrdinaryCard;
import com.kittens.card.SuddenAction;
import com.kittens.service.*;

import java.util.ArrayList;
import java.util.List;


public class Demo
{

    public static void main(String[] args)
    {
        gameProcess(new ArrayList<>(List.of(new Player("player 1"), new Player("player 2"))));
    }


    public static void gameProcess(List<Player> players)
    {
        var gameStateService = new GameStateService(new CardHandlerImpl(), new CombinationHandlerImpl());

        GameState gameState = init(players);

        while (gameState.getPlayersList().size() != 1)
        {
            Player movingPlayer = gameState.getNowTurn();

            // получение карты от игрока или её отсутствия
            var playerCard = movingPlayer.getCards().get(0);

            // Получение карт игроков. Что-то вроде 2с на то, чтобы скинуть карту.
            // Если карту скинули, заново 2с на то, чтобы скинуть опять.
            List<Card> suddenCards = List.of(
                    new OrdinaryCard("no", Action.INACTION, Action.INACTION, SuddenAction.CANCEL)
            );

            GameState newGameState = gameStateService.playCard(gameState, playerCard, suddenCards);

            // если не сыграли карту, меняющую курсор игры (слиняй или атакуй, например)
            if (movingPlayer.equals(newGameState.getNowTurn()))
            {
                gameStateService.addNewCardToPlayer(newGameState);

                gameStateService.changeMove(newGameState);
            }

            gameState = newGameState;
        }
        System.out.println();
    }


    private static GameState init(List<Player> players)
    {
        List<Card> cardDeck = new ArrayList<>(List.of(
                new OrdinaryCard("exploding cat", Action.INACTION, Action.EXPLODE_OR_DEFUSE, SuddenAction.INACTION),
                new OrdinaryCard("exploding cat", Action.INACTION, Action.EXPLODE_OR_DEFUSE, SuddenAction.INACTION),
                new OrdinaryCard("exploding cat", Action.INACTION, Action.EXPLODE_OR_DEFUSE, SuddenAction.INACTION),
                new OrdinaryCard("get lost", Action.GET_LOST)
        ));

        players.get(0).addCard(new OrdinaryCard("get lost", Action.GET_LOST));
        players.get(0).addCard(new OrdinaryCard("attack", Action.ATTACK));
        players.get(0).addCard(new OrdinaryCard("defuse"));
        players.get(0).addCard(new OrdinaryCard("no", Action.INACTION, Action.INACTION, SuddenAction.CANCEL));

        players.get(1).addCard(new OrdinaryCard("hairy catato"));
        players.get(1).addCard(new OrdinaryCard("tacocat"));
        players.get(1).addCard(new OrdinaryCard("beardcat"));
        players.get(1).addCard(new OrdinaryCard("nyah-cat in reverse"));
        players.get(1).addCard(new OrdinaryCard("cattermelon"));

        players.get(1).addCard(new OrdinaryCard("defuse"));

        var playerLoopingList = new LoopingList<>(players);
        return new GameState(
                playerLoopingList,
                cardDeck,
                new ArrayList<>(),
                playerLoopingList.next(),
                1
        );
    }
}
