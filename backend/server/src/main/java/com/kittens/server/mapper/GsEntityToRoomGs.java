package com.kittens.server.mapper;

import com.kittens.logic.model.AbstractPlayer;
import com.kittens.logic.model.LoopingList;
import com.kittens.logic.model.LoopingListImpl;
import com.kittens.server.entity.GameStateEntity;
import com.kittens.server.entity.PlayerQueuePointer;
import com.kittens.server.game.model.RoomGameState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
@RequiredArgsConstructor
public class GsEntityToRoomGs implements Mapper<GameStateEntity, RoomGameState>
{
    private final CardNameToCard cardNameMapper;

    private final PlayerEntityToUserRefPlayer playerToUserRefPlayer;


    @Override
    public RoomGameState map(GameStateEntity object)
    {
        RoomGameState roomGameState = new RoomGameState(
                playerPointersToLoopingList(object.getPlayerQueuePointers()),
                cardNameMapper.map(object.getCardDeck().getValue()),
                cardNameMapper.map(object.getCardReset().getValue()),
                object.getStepQuantity(),
                object.getId()
        );
        roomGameState.setNowTurn(playerToUserRefPlayer.map(object.getNowTurn()));

        return roomGameState;
    }


    private LoopingList<AbstractPlayer> playerPointersToLoopingList(List<PlayerQueuePointer> plPointers)
    {
        Map<AbstractPlayer, AbstractPlayer> sourceMap = new HashMap<>();

        for (PlayerQueuePointer pointer : plPointers)
        {
            sourceMap.put(
                    playerToUserRefPlayer.map(pointer.getPointingPlayer()),
                    playerToUserRefPlayer.map(pointer.getPointedAtPlayer())
            );
        }

        return new LoopingListImpl<>(sourceMap);
    }

}
