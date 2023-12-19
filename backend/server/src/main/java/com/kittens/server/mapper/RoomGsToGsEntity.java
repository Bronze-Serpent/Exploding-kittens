package com.kittens.server.mapper;

import com.kittens.logic.model.AbstractPlayer;
import com.kittens.logic.model.LoopingList;
import com.kittens.server.entity.GameStateEntity;
import com.kittens.server.entity.PlayerQueuePointer;
import com.kittens.server.game.model.RoomGameState;
import com.kittens.server.game.model.UserRefPlayer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.Map;


@Component
@RequiredArgsConstructor
public class RoomGsToGsEntity implements Mapper<RoomGameState, GameStateEntity>
{
    private final UserRefPlayerToPlayerEntity userRefPlayerToPlayer;

    private final CardToCardName cardToCardName;


    @Override
    public GameStateEntity map(RoomGameState object)
    {
        GameStateEntity gameStateEntity = new GameStateEntity();
        copy(object, gameStateEntity);

        return gameStateEntity;
    }

    public void copy(RoomGameState object, GameStateEntity entity)
    {
        entity.setId(object.getId());
        entity.getCardDeck().setValue(cardToCardName.map(object.getCardDeck()));
        entity.getCardReset().setValue(cardToCardName.map(object.getCardReset()));
        entity.setStepQuantity(object.getStepQuantity());
        entity.setNowTurn(userRefPlayerToPlayer.map((UserRefPlayer) object.getNowTurn()));

        List<PlayerQueuePointer> pointers = entity.getPlayerQueuePointers();
        loopingListToPointers(pointers, object.getPlayersTurn());
    }


    private void loopingListToPointers(List<PlayerQueuePointer> pointers, LoopingList<AbstractPlayer> loopingList)
    {
        Map<AbstractPlayer, AbstractPlayer> pairs = loopingList.getPairs();

        Iterator<PlayerQueuePointer> iterator = pointers.iterator();
        while (iterator.hasNext())
        {
            PlayerQueuePointer pointer = iterator.next();

            UserRefPlayer playerWithPointingId = new UserRefPlayer(pointer.getPointingPlayer().getId(), null, null);
            UserRefPlayer playerWithPointedAtId = new UserRefPlayer(pointer.getPointedAtPlayer().getId(), null, null);

            if (pairs.containsKey(playerWithPointingId))
            {
                // перезаписываем в поинтере PlayerEntity по key
                pointer.setPointingPlayer(userRefPlayerToPlayer.map((UserRefPlayer) getKeyWithPlayerId(pairs, playerWithPointingId.getId())));
                if (!pairs.containsValue(playerWithPointedAtId))
                {
                    // перезаписываем в поинтере PlayerEntity по value
                    pointer.setPointedAtPlayer(userRefPlayerToPlayer.map((UserRefPlayer) pairs.get(playerWithPointingId)));
                }
            }
            else
            {
                // удаляем такой pointer, если его нет в loopingList
                iterator.remove();
            }
        }
    }


    private AbstractPlayer getKeyWithPlayerId(Map<AbstractPlayer, AbstractPlayer> map, Long id)
    {
        for (Map.Entry<AbstractPlayer, AbstractPlayer> entry : map.entrySet())
        {
            if (entry.getKey().getId() == id)
                return entry.getKey();
        }
        throw new RuntimeException("Не найдено ключа для такого id: " + id);
    }
}
