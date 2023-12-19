package com.kittens.server.service.impl;

import com.kittens.logic.card.Card;
import com.kittens.logic.card.CardName;
import com.kittens.logic.service.GameStateUtils;
import com.kittens.server.dto.PlayCardDto;
import com.kittens.server.game.model.RoomGameState;
import com.kittens.server.mapper.CardNameToCard;
import com.kittens.server.service.GameService;
import com.kittens.server.service.GameStateService;
import com.kittens.server.service.NotificationService;
import com.kittens.server.service.UserQuestioner;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GameServiceImpl implements GameService {

    NotificationService notificationService;
    UserQuestioner userQuestioner;
    GameStateService gameStateService;
    GameStateUtils gameStateUtils;
    CardNameToCard cardNameToCardMapper;

    @Override
    public void playCard(Long roomId, PlayCardDto dto) {
        List<PlayCardDto> playSuddenCards = new ArrayList<>();
        Optional<PlayCardDto> playSuddenCardDto = userQuestioner.askPlayersToPlaySuddenCards(roomId, dto);
        while (playSuddenCardDto.isPresent()){
            playSuddenCards.add(playSuddenCardDto.get());
            playSuddenCardDto = userQuestioner.askPlayersToPlaySuddenCards(roomId, dto);
        }
        // TODO: 19.12.2023 нам бы тут ошибку кидать, что такой комнаты нет
        RoomGameState gameState = gameStateService.getGameStateByRoomId(roomId).get();

        Card card = cardNameToCardMapper.map(new CardName[]{dto.getCardName()}).get(0);

        List<Card> suddenCards = cardNameToCardMapper.map(playSuddenCards
                .stream()
                .map(PlayCardDto::getCardName)
                .toArray(CardName[]::new));

        gameStateUtils.playCard(gameState, card, suddenCards);

        gameStateService.updateGameState(gameState);

        //TODO: Вместо геймстейта отправляем геймстейт + карты игрока
        notificationService.sendMessageToRoom(roomId, gameState);
    }
}
