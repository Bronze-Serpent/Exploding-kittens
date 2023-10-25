package com.kittens.card;

import com.kittens.GameState;
import com.kittens.Player;


public enum Action
{

    @Deprecated
    PEEK {
        @Override
        public GameState doAction(Card card, GameState gameState)
        {
            return gameState;
        }
    },

    @Deprecated
    SHUFFLE {
        @Override
        public GameState doAction(Card card, GameState gameState)
        {
            return gameState;
        }
    },

    @Deprecated
    LICK {
        @Override
        public GameState doAction(Card card, GameState gameState)
        {
            return gameState;
        }
    },

    INACTION {
        @Override
        public GameState doAction(Card card, GameState gameState)
        {
            return gameState;
        }
    },

    ATTACK {
        @Override
        public GameState doAction(Card card, GameState gameState)
        {
            return new GameState(gameState.getPlayersList(),
                    gameState.getCardDeck(),
                    gameState.getCardReset(),
                    gameState.getPlayersList().next(),
                    gameState.getStepQuantity() + 1);
        }
    },

    EXPLODE_OR_DEFUSE {
        @Override
        public GameState doAction(Card card, GameState gameState)
        {
            GameState newGameState = new GameState(gameState);
            Player player = gameState.getNowTurn();
            if (player.isThereCard("defuse"))
            {
                var removedCard = player.removeCard("defuse");
                player.removeCard("exploding cat");
                newGameState.addToCardReset(removedCard);
                newGameState.addToCardReset(card);
            }
            else
            {
                newGameState.addToCardReset(player.getCards());
                player.getCards().clear();
                newGameState.removePlayer(player);
            }

            return newGameState;
        }
    },

    GET_LOST {
        @Override
        public GameState doAction(Card card, GameState gameState)
        {
            Player nowTurn = (gameState.getStepQuantity() == 1) ? gameState.getPlayersList().next() : gameState.getNowTurn();
            int stepQuantity = (gameState.getStepQuantity() == 1) ? 1 : gameState.getStepQuantity() - 1;

            return new GameState(gameState.getPlayersList(),
                    gameState.getCardDeck(),
                    gameState.getCardReset(),
                    nowTurn,
                    stepQuantity);
        }
    }
    ;

    public abstract GameState doAction(Card card, GameState gameState);

    // todo Здесь реализовать логику для таких действий. Тут взаимодействия должны быть через интерфейсы
    // куда будет выводиться информация из посмотреть.
    // Спрашивается какую карту отдать (возможно) спрашивать как именно перемешивать
}
