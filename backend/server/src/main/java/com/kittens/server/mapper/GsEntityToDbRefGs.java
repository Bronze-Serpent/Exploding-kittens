package com.kittens.server.mapper;

import com.kittens.logic.model.AbstractPlayer;
import com.kittens.logic.model.LoopingList;
import com.kittens.logic.model.LoopingListImpl;
import com.kittens.server.entity.GameStateEntity;
import com.kittens.server.entity.PlayerQueuePointer;
import com.kittens.server.game.model.DbRefGameState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
@RequiredArgsConstructor
public class GsEntityToDbRefGs implements Mapper<GameStateEntity, DbRefGameState>
{
    private final CardNameToCard cardNameMapper;

    private final PlayerEntityToUserRefPlayer playerToUserRefPlayer;


    @Override
    public DbRefGameState map(GameStateEntity object)
    {
        return new DbRefGameState(
                playerPointersToLoopingList(object.getPlayerQueuePointers()),
                cardNameMapper.map(object.getCardDeck().getValue()),
                cardNameMapper.map(object.getCardReset().getValue()),
                playerToUserRefPlayer.map(object.getNowTurn()),
                object.getStepQuantity(),
                object.getId()
        );
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
