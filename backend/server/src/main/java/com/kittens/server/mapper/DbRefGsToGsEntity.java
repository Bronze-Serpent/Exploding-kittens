package com.kittens.server.mapper;

import com.kittens.logic.model.AbstractPlayer;
import com.kittens.logic.model.LoopingList;
import com.kittens.server.entity.GameStateEntity;
import com.kittens.server.entity.PlayerQueuePointer;
import com.kittens.server.game.model.DbRefGameState;
import com.kittens.server.game.model.UserRefPlayer;
import com.kittens.server.repository.GameStateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

// TODO: 16.12.2023 вообще не здорово что этот маппер занимается ещё и тем, что по сути сливает изменения в БД, это нарушение srp
@Component
@RequiredArgsConstructor
public class DbRefGsToGsEntity implements Mapper<DbRefGameState, GameStateEntity>
{
    private final UserRefPlayerToPlayerEntity userRefPlayerToPlayer;

    private final CardToCardName cardToCardName;

    private final GameStateRepository gameStateRepository;


    @Override
    public GameStateEntity map(DbRefGameState object)
    {
        GameStateEntity gameStateEntity = gameStateRepository.findById(object.getId())
                .orElseThrow(() -> new RuntimeException("GameState на который ссылается данный объект нет в БД"));
        List<PlayerQueuePointer> pointers = gameStateEntity.getPlayerQueuePointers();
        loopingListToPointers(pointers, object.getPlayersTurn());

        gameStateEntity.getCardDeck().setValue(cardToCardName.map(object.getCardDeck()));
        gameStateEntity.getCardReset().setValue(cardToCardName.map(object.getCardReset()));
        gameStateEntity.setStepQuantity(object.getStepQuantity());

        return gameStateEntity;
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
