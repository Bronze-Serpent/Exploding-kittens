package com.kittens.server.service;

public interface NotificationService {

    void sendMessageToUser(Long roomId, Long userId, Object message);

    void sendMessageToRoom(Long roomId, Object message);
}
