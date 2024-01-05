package com.kittens.server.service.impl;

import com.kittens.logic.action.player.interaction.PlayerInformer;
import com.kittens.logic.model.AbstractPlayer;
import com.kittens.server.dto.PlayerInformingDto;
import com.kittens.server.dto.RoomReadDto;
import com.kittens.server.game.model.UserRefPlayer;
import com.kittens.server.service.NotificationService;
import com.kittens.server.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserInformer implements NotificationService, PlayerInformer
{
    private final SimpMessagingTemplate simpMessagingTemplate;

    // TODO: 05.01.2024 Lazy тут это конечно не здорово. Циклическая зависимость.
    //  А как иначе? В PlayerService этот метод впихнуть тоже не вариант. Отдельный сервис - хз, логически он связан то с комнатами.
    //  Использовать напрямую RoomRepository - ну хз
    @Lazy
    private final RoomService roomService;


    @Override
    public void sendMessageToUser(Long roomId, Long userId, Object message)
    {
        simpMessagingTemplate.convertAndSend("/topic/message/" + roomId + "/" + userId, message);
    }


    @Override
    public void sendMessageToRoom(Long roomId, Object message)
    {
        simpMessagingTemplate.convertAndSend("/topic/message/" + roomId, message);
    }


    @Override
    public void inform(AbstractPlayer player, PlayerInformer.Informing informing, String msg)
    {
        // TODO: 05.01.2024 а как это работает? В плане что придёт клиенту, если я отправлю тут какой-то DTO
        // (аналогично с gamestate вопрос)
        UserRefPlayer castedPlayer = ((UserRefPlayer) player);
        RoomReadDto roomDto = roomService.findRoomFor(castedPlayer.getUserId())
                .orElseThrow(() -> new RuntimeException("Не удалось найти room для игрока с playerId: " + player.getId()
                        + " принадлежащего user с userId: " + castedPlayer.getUserId()));

        simpMessagingTemplate.convertAndSend("/topic/message/" + roomDto.getId() + "/" + castedPlayer.getUserId(),
                new PlayerInformingDto(informing, msg));
    }
}
