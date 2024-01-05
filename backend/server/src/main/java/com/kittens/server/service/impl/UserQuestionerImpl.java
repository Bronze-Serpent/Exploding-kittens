package com.kittens.server.service.impl;

import com.kittens.logic.action.player.interaction.PlayerQuestioner;
import com.kittens.logic.model.AbstractPlayer;
import com.kittens.server.dto.PlayCardDto;
import com.kittens.server.dto.PlayerQuestioningDto;
import com.kittens.server.dto.RoomReadDto;
import com.kittens.server.dto.UserAnswerDto;
import com.kittens.server.game.model.UserRefPlayer;
import com.kittens.server.service.NotificationService;
import com.kittens.server.service.RoomService;
import com.kittens.server.service.UserQuestioner;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Lazy;
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
    private static final Long TIME_TO_ANSWER_QUESTION = 10L;

    private final NotificationService notificationService;
    @Lazy
    private final RoomService roomService;

    private final Map<Long, CompletableFuture<Optional<PlayCardDto>>> roomIdToSuddenCardResponses = new HashMap<>();
    private final Map<Long, CompletableFuture<String>> roomIdToQuestionAnswerResponses = new HashMap<>();


    // TODO: 05.01.2024 а в данной реализации нам вообще мапа нужна? У нас же по одному эти опросы происходят.
    //  Хотя тут в зависимости от того как у нас многопоточка будет.
    @SneakyThrows
    @Override
    public Optional<PlayCardDto> askPlayersToPlaySuddenCards(Long roomId, Object message)
    {
        notificationService.sendMessageToRoom(roomId, message);

        CompletableFuture<Optional<PlayCardDto>> completableFuture = new CompletableFuture<>();
        completableFuture.completeOnTimeout(Optional.empty(), TIME_TO_PLAY_SUDDEN_CARD, TimeUnit.SECONDS);

        roomIdToSuddenCardResponses.put(roomId, completableFuture);

        return completableFuture.get();
    }


    @Override
    public void responseToPlaySuddenCards(Long roomId, PlayCardDto dto)
    {
        roomIdToSuddenCardResponses.remove(roomId).complete(Optional.of(dto));
    }


    // TODO: 05.01.2024 для этой штуки бы серьёзную валидацию сделать
    //  т.к. строка-ответ где-то должна быть числом, где-то enum и т.д.
    @SneakyThrows
    @Override
    public String ask(AbstractPlayer player, PlayerQuestioner.Question question)
    {
        UserRefPlayer castedPlayer = (UserRefPlayer) player;
        RoomReadDto roomDto = roomService.findRoomFor(castedPlayer.getId())
                .orElseThrow(() -> new RuntimeException("Не удалось найти room для игрока с playerId: " + player.getId()
                        + " принадлежащего user с userId: " + castedPlayer.getUserId()));

        notificationService.sendMessageToUser(roomDto.getId(), castedPlayer.getUserId(), new PlayerQuestioningDto(question));

        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        completableFuture.completeOnTimeout(PlayerQuestioner.NO_RESPONSE, TIME_TO_ANSWER_QUESTION, TimeUnit.SECONDS);

        roomIdToQuestionAnswerResponses.put(roomDto.getId(), completableFuture);

        return completableFuture.get();
    }


    @Override
    public void responseToQuestion(Long roomId, UserAnswerDto answerDto)
    {
        roomIdToQuestionAnswerResponses.remove(roomId).complete(answerDto.getAnswer());
    }
}
