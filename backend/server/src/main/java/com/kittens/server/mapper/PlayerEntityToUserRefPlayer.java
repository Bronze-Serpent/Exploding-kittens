package com.kittens.server.mapper;

import com.kittens.server.entity.PlayerEntity;
import com.kittens.server.game.model.UserRefPlayer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class PlayerEntityToUserRefPlayer implements Mapper<PlayerEntity, UserRefPlayer>
{
    private final CardNameToCard cardNameToCard;

    @Override
    public UserRefPlayer map(PlayerEntity object)
    {
        return new UserRefPlayer(
                object.getId(),
                cardNameToCard.map(object.getCards()),
                object.getUser().getId()
        );
    }
}
