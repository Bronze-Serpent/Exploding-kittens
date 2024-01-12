package com.kittens.server.mapper;

import com.kittens.logic.card.Card;
import com.kittens.logic.model.GameState;
import com.kittens.server.dto.GameStateCardDto;
import com.kittens.server.dto.GameStateDto;
import com.kittens.server.dto.GameStatePlayerDto;


public class GameStateToDtoMapper implements Mapper<GameState, GameStateDto>
{
    @Override
    public GameStateDto map(GameState object)
    {
        return GameStateDto.builder()
                .playerTurnId(object.getNowTurn().getId())
                .stepQuantity(object.getStepQuantity())
                .players(object.getPlayersTurn().getElements().stream()
                        .map(player -> new GameStatePlayerDto(
                                player.getId(),
                                player.getCards().stream()
                                        .map(Card::getName)
                                        .map(GameStateCardDto::new)
                                        .toList()))
                        .toList())
                .build();
    }
}
