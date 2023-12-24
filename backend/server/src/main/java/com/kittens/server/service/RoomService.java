package com.kittens.server.service;


import java.util.List;

public interface RoomService
{
    void addUserToRoom(Long createdRoomId, Long userId);

    List<Long> getAllUsersId(Long roomId);

    Long createRoom(Long userId);
}
