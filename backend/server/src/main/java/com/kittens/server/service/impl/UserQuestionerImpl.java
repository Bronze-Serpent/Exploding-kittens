package com.kittens.server.service.impl;

import com.kittens.server.dto.PlayCardDto;
import com.kittens.server.service.NotificationService;
import com.kittens.server.service.UserQuestioner;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserQuestionerImpl implements UserQuestioner
{
    private static final Long TIME_TO_PLAY_SUDDEN_CARD = 10L;


    NotificationService notificationService;

    Map<Long, CompletableFuture<Optional<PlayCardDto>>> roomIdToResponses = new HashMap<>();


    @SneakyThrows
    @Override
    public Optional<PlayCardDto> askPlayersToPlaySuddenCards(Long roomId, Object message) {
        notificationService.sendMessageToRoom(roomId, message);

        CompletableFuture<Optional<PlayCardDto>> completableFuture = new CompletableFuture<>();
        roomIdToResponses.put(roomId, completableFuture);

        completableFuture.completeOnTimeout(Optional.empty(), TIME_TO_PLAY_SUDDEN_CARD, TimeUnit.SECONDS);

        return completableFuture.get();
    }

    @Override
    public void responseToPlaySuddenCards(Long roomId, PlayCardDto dto) {
        roomIdToResponses.remove(roomId).complete(Optional.of(dto));
    }


}
