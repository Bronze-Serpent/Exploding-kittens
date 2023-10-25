package com.kittens.service;

import com.kittens.GameState;
import com.kittens.card.Card;

import java.util.HashSet;
import java.util.List;


public class CombinationHandlerImpl implements CombinationHandler
{

    public GameState playCombination(GameState oldGameState, List<Card> combination)
    {
        if (combination.size() == 2)
        {
            if (combination.get(0).equals(combination.get(1)))
            {
                System.out.println("Выбирает игрока и перекладываем случайную карту от этого игрока nowTurn");
            }
        }
        if (combination.size() == 3)
        {
            if (combination.get(0).equals(combination.get(1)) &&
                    combination.get(0).equals(combination.get(2)))
            {
                System.out.println("Выбирает игрока, получаем название карты и, если такая карта у игрока есть," +
                        " то перекладываем эту карту от игрока игроку nowTurn");
            }
        }
        // ешё стоит проверять, что в combination всего 5 элементов, но это лучше делать не тут,
        // а просто не позволять пользователю отправлять неподходящие комбинации
        if (new HashSet<>(combination).size() == 5)
        {
            System.out.println("Выбирает любою карту из сброса и добавляем её в nowTurn");
        }

        return oldGameState;
    }
}
