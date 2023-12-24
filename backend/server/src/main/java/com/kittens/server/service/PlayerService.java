package com.kittens.server.service;

import com.kittens.server.game.model.UserRefPlayer;

import java.util.Optional;
import java.util.Set;


public interface PlayerService
{
    Long createEmptyPlayer(Long roomId, Long userId);

    void updatePlayers(Set<UserRefPlayer> players);

    Optional<UserRefPlayer> findById(Long id);
}
