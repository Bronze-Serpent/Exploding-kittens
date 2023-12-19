package com.kittens.server.mapper;

import com.kittens.server.entity.PlayerEntity;
import com.kittens.server.game.model.UserRefPlayer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class UserRefPlayerToPlayerEntity implements Mapper<UserRefPlayer, PlayerEntity>
{
    private final CardToCardName cardToCardName;


    @Override
    public PlayerEntity map(UserRefPlayer object)
    {

        PlayerEntity playerEntity = new PlayerEntity();

        copy(object, playerEntity);

        return playerEntity;
    }


    public void copy(UserRefPlayer object, PlayerEntity entity)
    {
        entity.setId(object.getId());
        entity.setCards(cardToCardName.map(object.getCards()));
    }
}
