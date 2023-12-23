package com.kittens.server.service.impl;

import com.kittens.server.service.NotificationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationServiceImpl implements NotificationService {
    SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void sendMessageToUser(Long roomId, Long userId, Object message) {
        simpMessagingTemplate.convertAndSend("/topic/message/" + roomId + "/" + userId, message);
    }

    @Override
    public void sendMessageToRoom(Long roomId, Object message) {
        simpMessagingTemplate.convertAndSend("/topic/message/" + roomId, message);
    }
}
