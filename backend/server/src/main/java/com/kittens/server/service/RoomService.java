package com.kittens.server.service;


import java.util.List;

public interface RoomService
{
    Long createEmptyRoom();

    void addUserToRoom(Long createdRoomId, Long userId);

    List<Long> getAllUsersId(Long roomId);
}
