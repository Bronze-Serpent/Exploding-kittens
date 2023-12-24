package com.kittens.server.service.impl;

import com.kittens.server.entity.PlayerEntity;
import com.kittens.server.game.model.UserRefPlayer;
import com.kittens.server.mapper.UserRefPlayerToPlayerEntity;
import com.kittens.server.repository.PlayerRepository;
import com.kittens.server.repository.RoomRepository;
import com.kittens.server.service.PlayerService;
import com.kittens.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;


@RequiredArgsConstructor
@Transactional
@Service
public class PlayerServiceImpl implements PlayerService
{
    private final PlayerRepository playerRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    private final UserRefPlayerToPlayerEntity userRefPlayerToEntity;

    @Override
    public Long createEmptyPlayer(Long roomId, Long userId)
    {
        // TODO: 20.12.2023 почему super builder не работает
        PlayerEntity emptyPlayer = new PlayerEntity();
        emptyPlayer.setRoom(roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Невозможно создать Player для комнаты с таким id: " + roomId + "т.к. комната с таким id не найдена")));
        emptyPlayer.setUser(userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Невозможно создать Player для для такого user т.к. user с таким id: " + userId + " не найден")));

        return playerRepository.save(emptyPlayer).
                getId();
    }


    @Override
    public void updatePlayers(Set<UserRefPlayer> players)
    {
        for (UserRefPlayer player : players)
        {
            PlayerEntity playerEntity = playerRepository.findById(player.getId())
                    .orElseThrow(() -> new RuntimeException("Невозможно обновить player с id: " + player.getId() + " поскольку player с таким id не найден в БД"));
            userRefPlayerToEntity.copy(player, playerEntity);
        }
        playerRepository.flush();
    }

}
