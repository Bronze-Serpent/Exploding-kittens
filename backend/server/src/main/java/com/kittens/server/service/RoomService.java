package com.kittens.server.service;


import com.kittens.server.dto.RoomReadDto;

import java.util.List;
import java.util.Optional;

public interface RoomService
{
    void addUserToRoom(Long createdRoomId, Long userId);

    List<Long> getAllUsersId(Long roomId);

    Long createRoom(Long userId);

    Optional<RoomReadDto> findRoomFor(Long playerId);
}
