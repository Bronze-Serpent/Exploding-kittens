package com.kittens.server.mapper;

import com.kittens.server.entity.PlayerEntity;
import com.kittens.server.game.model.UserRefPlayer;
import com.kittens.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class UserRefPlayerToPlayerEntity implements Mapper<UserRefPlayer, PlayerEntity>
{
    private final CardToCardName cardToCardName;

    private final UserRepository userRepository;


    @Override
    public PlayerEntity map(UserRefPlayer object)
    {

        PlayerEntity playerEntity = new PlayerEntity();

        playerEntity.setId(object.getId());
        playerEntity.setCards(cardToCardName.map(object.getCards()));
        playerEntity.setUser(userRepository
                .findById(object.getUserId())
                .orElseThrow(() -> (new RuntimeException("Player ссылается на id несуществующего User")))
        );

        return playerEntity;
    }
}
